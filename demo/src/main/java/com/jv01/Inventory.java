package com.jv01;

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

import java.awt.*;

public class Inventory {
    public Save save = new Save();

    public String gameName;

    public JPanel inventoryPanel;
    public int inventoryHeight = 800;
    public int inventoryWidth = 380;
    public int[] cellSize = {(inventoryWidth/3),(inventoryWidth/3)};

    public int wastes = 0;
    public int apples = 0;

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

    private boolean[][] cell = {
        {false,false,false},
        {false,false,false},
        {false,false,false},
        {false,false,false}
    };

    public Inventory(String gameName){
        this.gameName = gameName;

        getInventoryValues();  
    }

    public void createInventoryPanel(JFrame frame){
        inventoryPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("demo\\img\\inventory\\back.jpg");
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
        Items item = new Items(type);
        Objects obj = new Objects(cellSize[0]/2 + position[0]*(cellSize[0]), cellSize[1]/2 + position[1]*(cellSize[1]), new int[]{cellSize[0],cellSize[1]}, item.imagesUrls[item.type][0], 0, inventoryPanel);
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

        this.maxWastes = Integer.parseInt(save.getChildFromMapElements(allElements,"maxWastes"));
        this.maxApples = Integer.parseInt(save.getChildFromMapElements(allElements,"maxApples"));
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
    }
}
