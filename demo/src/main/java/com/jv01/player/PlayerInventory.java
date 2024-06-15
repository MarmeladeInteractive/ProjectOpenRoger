package com.jv01.player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;

import java.awt.*;

public class PlayerInventory {
    public SoundManager soundManager;
    public Save save = new Save();

    public String gameName;

    public JPanel inventoryPanel;
    public int inventoryHeight = 800;
    public int inventoryWidth = 380;
    public int[] cellSize = {(inventoryWidth/3),(inventoryWidth/3)};

    public int maxWastes = 10;
    public int maxApples = 10;
    public int maxChocolatines = 10;
    public int maxCroissants = 10;

    public Map<String, Integer> itemsCount = new HashMap<>();
    public Map<String, Integer> itemsMax = new HashMap<>();
    public Map<String, String> itemsPics = new HashMap<>();
    public String backPic;

    public PlayerInventory(String gameName){
        this.gameName = gameName;

        soundManager = new SoundManager(gameName);

        getInventoryValues();  
    }

    public void createInventoryPanel(JFrame frame){
        inventoryPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("demo/img/inventory/back.jpg");
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        inventoryPanel.setLayout(null);
        inventoryPanel.setBounds(0, 0, inventoryWidth, inventoryHeight); 

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screenSize = toolkit.getScreenSize();

        int screenHeight = screenSize.height;

        inventoryPanel.setLocation(10,(screenHeight - inventoryHeight)/2);

        frame.add(inventoryPanel);
    }

    public void getInventoryValues(){
        Document doc = save.getDocumentXml(gameName,"inventory");
        Element element = save.getElementById(doc, "inventory", "inventory");

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        this.maxWastes = Integer.parseInt(save.getChildFromMapElements(allElements,"maxWastes"));
        this.maxApples = Integer.parseInt(save.getChildFromMapElements(allElements,"maxApples"));
        this.maxChocolatines = Integer.parseInt(save.getChildFromMapElements(allElements,"maxChocolatines"));
        this.maxCroissants = Integer.parseInt(save.getChildFromMapElements(allElements,"maxCroissants"));
        
        itemsMax.put("wastes",this.maxWastes);
        itemsMax.put("apples",this.maxApples);
        itemsMax.put("chocolatines",this.maxChocolatines);
        itemsMax.put("croissants",this.maxCroissants);

        itemsCount.put("wastes",Integer.parseInt(save.getChildFromMapElements(allElements,"wastes")));
        itemsCount.put("apples",Integer.parseInt(save.getChildFromMapElements(allElements,"apples")));
        itemsCount.put("chocolatines",Integer.parseInt(save.getChildFromMapElements(allElements,"chocolatines")));
        itemsCount.put("croissants",Integer.parseInt(save.getChildFromMapElements(allElements,"croissants")));

        element = save.getElementById(doc, "inventory", "items");
        allElements = save.getAllChildsFromElement(element);

        String path = save.stringToStringArray(save.getChildFromElement(element, "wastes"))[0];
        itemsPics.put("wastes",save.dropSpaceFromString(path));

        path = save.stringToStringArray(save.getChildFromElement(element, "apples"))[0];
        itemsPics.put("apples",save.dropSpaceFromString(path));

        path = save.stringToStringArray(save.getChildFromElement(element, "chocolatines"))[0];
        itemsPics.put("chocolatines",save.dropSpaceFromString(path));

        path = save.stringToStringArray(save.getChildFromElement(element, "croissants"))[0];
        itemsPics.put("croissants",save.dropSpaceFromString(path));

        element = save.getElementById(doc, "inventory", "back");
        allElements = save.getAllChildsFromElement(element);

        backPic = save.stringToStringArray(save.getChildFromElement(element, "back"))[0];
        backPic = save.dropSpaceFromString(backPic);
    }

    public void saveInventoryValue( String childName, String newValue){
        save.changeElementChildValue(gameName, "inventory", "inventory", "inventory", childName, newValue);
    }

    public void saveWastes(){
        saveInventoryValue("wastes",String.valueOf(itemsCount.get("wastes")));
    }

    public void saveApples(){
        saveInventoryValue("apples",String.valueOf(itemsCount.get("apples")));
    }

    public void saveChocolatines(){
        saveInventoryValue("chocolatines",String.valueOf(itemsCount.get("chocolatines")));
    }

    public void saveCroissants(){
        saveInventoryValue("croissants",String.valueOf(itemsCount.get("croissants")));
    }

    public void saveAll(){
        saveWastes();
        saveApples();
        saveChocolatines();
        saveCroissants();
    }

    public void changeValue(String key, int value){
        try{
            itemsCount.replace(key, value);
        }catch(Exception e){
            System.err.println("inventory key dont exist!");
        }
    }

    public int getValue(String key){
        try{
            return itemsCount.get(key);
        }catch(Exception e){
            System.err.println("inventory key dont exist!");
        }
        return 0;
    }

    public void fillInventory(){
        itemsCount.replace("wastes", maxWastes);
        itemsCount.replace("apples", maxApples);
        itemsCount.replace("chocolatines", maxChocolatines);
        itemsCount.replace("croissants", maxCroissants);

    }

    public void emptyInventory(){
        soundManager.playSFX("waste01");

        itemsCount.replace("wastes", 0);
        itemsCount.replace("apples", 0);
        itemsCount.replace("chocolatines", 0);
        itemsCount.replace("croissants", 0);
    }
}
