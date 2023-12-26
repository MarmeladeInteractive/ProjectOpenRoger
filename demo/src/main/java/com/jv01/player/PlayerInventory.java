package com.jv01.player;

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

    public int wastes = 0;
    public int apples = 0;
    public int chocolatines = 0;
    public int croissants = 0;

    public int maxWastes = 10;
    public int maxApples = 10;
    public int maxChocolatines = 10;
    public int maxCroissants = 10;

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

        this.wastes = Integer.parseInt(save.getChildFromMapElements(allElements,"wastes"));
        this.apples = Integer.parseInt(save.getChildFromMapElements(allElements,"apples"));
        this.chocolatines = Integer.parseInt(save.getChildFromMapElements(allElements,"chocolatines"));
        this.croissants = Integer.parseInt(save.getChildFromMapElements(allElements,"croissants"));

        this.maxWastes = Integer.parseInt(save.getChildFromMapElements(allElements,"maxWastes"));
        this.maxApples = Integer.parseInt(save.getChildFromMapElements(allElements,"maxApples"));
        this.maxChocolatines = Integer.parseInt(save.getChildFromMapElements(allElements,"maxChocolatines"));
        this.maxCroissants = Integer.parseInt(save.getChildFromMapElements(allElements,"maxCroissants"));
    }

    public void saveInventoryValue( String childName, String newValue){
        save.changeElementChildValue(gameName, "inventory", "inventory", "inventory", childName, newValue);
    }

    public void saveWastes(){
        saveInventoryValue("wastes",String.valueOf(wastes));
    }

    public void saveApples(){
        saveInventoryValue("apples",String.valueOf(apples));
    }

    public void saveChocolatines(){
        saveInventoryValue("chocolatines",String.valueOf(chocolatines));
    }

    public void saveCroissants(){
        saveInventoryValue("croissants",String.valueOf(croissants));
    }

    public void saveAll(){
        saveWastes();
        saveApples();
        saveChocolatines();
        saveCroissants();
    }

    public void fillInventory(){
        wastes = maxWastes;
        apples = maxApples;
        chocolatines = maxChocolatines;
        croissants = maxCroissants;
    }

    public void emptyInventory(){
        soundManager.playSFX("waste01");
        wastes = 0;
        apples = 0;
        chocolatines = 0;
        croissants = 0;
    }
}
