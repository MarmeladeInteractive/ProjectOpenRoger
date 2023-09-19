package com.jv01.player;

import java.util.List;
import java.util.Map;

//import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
//import javax.swing.border.Border;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;
import com.jv01.generations.Items;
import com.jv01.generations.Objects;

import java.awt.*;

public class Inventory {
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
    private JLabel wastesLabel;
    private Objects wastesObj;
    private boolean isWaste = false;
    private int[] wastePosition;

    public int maxApples = 10;
    private JLabel applesLabel;
    private Objects applesObj;
    private boolean isApple = false;
    private int[] applePosition;

    public int maxChocolatines = 10;
    private JLabel chocolatinesLabel;
    private Objects chocolatinesObj;
    private boolean isChocolatine = false;
    private int[] chocolatinePosition;

    public int maxCroissants = 10;
    private JLabel croissantsLabel;
    private Objects croissantsObj;
    private boolean isCroissant = false;
    private int[] croissantPosition;

    private boolean[][] cell = {
        {false,false,false},
        {false,false,false},
        {false,false,false},
        {false,false,false}
    };

    public Inventory(String gameName){
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

        //int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        inventoryPanel.setLocation(10,(screenHeight - inventoryHeight)/2);

        frame.add(inventoryPanel);

        addItems();
    }

    private void addItems(){
        if(wastes > 0){addWasteItem();}
        if(apples > 0){addAppleItem();}
        if(chocolatines > 0){addChocolatineItem();}
        if(croissants > 0){addCroissantItem();}
    }

    private void addWasteItem(){
        int[] position;
        if(isWaste){
            position = wastePosition;
        }else{
            position = getPosition();
            wastePosition = position;
            isWaste = true;
        }
        wastesObj = addObject(position,0);
        wastesLabel = addLabel(wastesLabel,position, String.valueOf(wastes) + '/' + String.valueOf(maxWastes));
    }

    private void addAppleItem(){
        int[] position;
        if(isApple){
            position = applePosition;
        }else{
            position = getPosition();
            applePosition = position;
            isApple = true;
        }
        applesObj = addObject(position,1);
        applesLabel = addLabel(applesLabel,position, String.valueOf(apples) + '/' + String.valueOf(maxApples));
    }

    private void addChocolatineItem(){
        int[] position;
        if(isChocolatine){
            position = chocolatinePosition;
        }else{
            position = getPosition();
            chocolatinePosition = position;
            isChocolatine = true;
        }
        chocolatinesObj = addObject(position,2);
        chocolatinesLabel = addLabel(chocolatinesLabel,position, String.valueOf(chocolatines) + '/' + String.valueOf(maxChocolatines));
    }

    private void addCroissantItem(){
        int[] position;
        if(isCroissant){
            position = croissantPosition;
        }else{
            position = getPosition();
            croissantPosition = position;
            isCroissant = true;
        }
        croissantsObj = addObject(position,3);
        croissantsLabel = addLabel(croissantsLabel,position, String.valueOf(croissants) + '/' + String.valueOf(maxCroissants));
    }

    private int[] getPosition(){
        for(int r = 0; r <= 3; r++){
            for(int c = 0; c <= 2; c++){
                if(!cell[r][c]){
                    cell[r][c] = true;
                    return new int[] {c,r};
                }
            }
        }
        return new int[] {-1,-1};
    }

    private Objects addObject(int[] position, int type){
        Items item = new Items(gameName,type);
        Objects obj = new Objects(cellSize[0]/2 + position[0]*(cellSize[0]), cellSize[1]/2 + position[1]*(cellSize[1]), new int[]{cellSize[0],cellSize[1]}, item.defaultImageUrl, 0, inventoryPanel);
        return obj;
    }

    private JLabel addLabel(JLabel label, int[] position, String text){
        label = new JLabel();
        label.setOpaque(true);
        label.setForeground(new Color(0, 0, 0));
        label.setBackground(new Color(150, 150, 150, 0));

        label.setBounds((cellSize[0]-100)/2 + position[0]*(cellSize[0]), cellSize[1] + position[1]*(cellSize[1]), 100, 30);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        label.setFont(labelFont);
        
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        inventoryPanel.add(label);
        label.setVisible(true);

        label.setText(
            "<html>"+
                text+
            "</html>"
        );
        return label;
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
        updateLables();
    }

    public void saveApples(){
        saveInventoryValue("apples",String.valueOf(apples));
        updateLables();
    }

    public void saveChocolatines(){
        saveInventoryValue("chocolatines",String.valueOf(chocolatines));
        updateLables();
    }

    public void saveCroissants(){
        saveInventoryValue("croissants",String.valueOf(croissants));
        updateLables();
    }

    public void saveAll(){
        saveWastes();
        saveApples();
        saveChocolatines();
        saveCroissants();
    }

    public void updateWastesLabel(){
        wastesLabel.setText(
            "<html>"+
                wastes + '/' + maxWastes+
            "</html>"
        );
    }

    public void updateApplesLabel(){
        applesLabel.setText(
            "<html>"+
                apples + '/' + maxApples+
            "</html>"
        );
    }

    public void updateChocolatinesLabel(){
        chocolatinesLabel.setText(
            "<html>"+
                chocolatines + '/' + maxChocolatines+
            "</html>"
        );
    }

    public void updateCroissantsLabel(){
        croissantsLabel.setText(
            "<html>"+
                croissants + '/' + maxCroissants+
            "</html>"
        );
    }
    
    public void updateLables(){
        if(wastes > 0){
            if(!isWaste){
                addWasteItem();
                isWaste = true;
            }
            updateWastesLabel();
        }else{
            if(isWaste){
                inventoryPanel.remove(wastesLabel);
                inventoryPanel.remove(wastesObj.objectLabel);
                cell[wastePosition[0]][wastePosition[1]] = false;
                isWaste = false;
            }
        }

        if(apples > 0){
            if(!isApple){
                addAppleItem();
                isApple = true;
            }
            updateApplesLabel();
        }else{
            if(isApple){
                inventoryPanel.remove(applesLabel);
                inventoryPanel.remove(applesObj.objectLabel);
                cell[applePosition[0]][applePosition[1]] = false;
                isApple = false;
            }
        }

        if(chocolatines > 0){
            if(!isChocolatine){
                addChocolatineItem();
                isChocolatine = true;
            }
            updateChocolatinesLabel();
        }else{
            if(isChocolatine){
                inventoryPanel.remove(chocolatinesLabel);
                inventoryPanel.remove(chocolatinesObj.objectLabel);
                cell[chocolatinePosition[0]][chocolatinePosition[1]] = false;
                isChocolatine = false;
            }
        }

        if(croissants > 0){
            if(!isCroissant){
                addCroissantItem();
                isCroissant = true;
            }
            updateCroissantsLabel();
        }else{
            if(isCroissant){
                inventoryPanel.remove(croissantsLabel);
                inventoryPanel.remove(croissantsObj.objectLabel);
                cell[croissantPosition[0]][croissantPosition[1]] = false;
                isCroissant = false;
            }
        }
    }

    public void fillInventory(){
        wastes = maxWastes;
        apples = maxApples;
        chocolatines = maxChocolatines;
        croissants = maxCroissants;
        updateLables();
    }

    public void emptyInventory(){
        soundManager.playSFX(4);
        wastes = 0;
        apples = 0;
        chocolatines = 0;
        croissants = 0;
        updateLables();
    }
}
