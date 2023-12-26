package com.jv01.screens.Windows;

import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;
import com.jv01.generations.Items;
import com.jv01.generations.Objects;
import com.jv01.miniGames.games.roulette.Roulette;
import com.jv01.player.PlayerInventory;
import com.jv01.screens.ShowNewWindow;

import java.awt.*;

public class Inventory {
    public boolean isInGame = true;
    public SoundManager soundManager;
    public Save save = new Save();

    private JPanel newWindowPanel;
    private int boxSize;

    public ShowNewWindow showNewWindow;

    public String gameName;

    public JPanel inventoryPanel;

    public int[] cellSize = {0,0};

    public PlayerInventory playerInventory;


    private JLabel wastesLabel;
    private Objects wastesObj;
    private boolean isWaste = false;
    private int[] wastePosition;

    private JLabel applesLabel;
    private Objects applesObj;
    private boolean isApple = false;
    private int[] applePosition;

    private JLabel chocolatinesLabel;
    private Objects chocolatinesObj;
    private boolean isChocolatine = false;
    private int[] chocolatinePosition;

    private JLabel croissantsLabel;
    private Objects croissantsObj;
    private boolean isCroissant = false;
    private int[] croissantPosition;

    private boolean[][] cell = {
        {false,false,false,false},
        {false,false,false,false},
        {false,false,false,false},
        {false,false,false,false}
    };

    public Inventory(ShowNewWindow showNewWindow){
        this.showNewWindow = showNewWindow;
        this.newWindowPanel = showNewWindow.newWindowPanel; 
        this.newWindowPanel.setLayout(null);

        this.gameName = showNewWindow.mainGameWindow.gameName;

        this.boxSize = showNewWindow.boxSize;

        this.playerInventory = showNewWindow.mainGameWindow.player.inventory;

        this.cellSize[0] = boxSize/4;
        this.cellSize[1] = boxSize/4;

        this.playerInventory.getInventoryValues();

        showInventory();
    }

    public Inventory(JPanel newWindowPanel,int boxSize){
        this.isInGame = false;

        this.newWindowPanel = newWindowPanel; 
        this.newWindowPanel.setLayout(null);
        this.boxSize = boxSize;

        this.cellSize[0] = boxSize/4;
        this.cellSize[1] = boxSize/4;

        this.gameName = "rh";

        playerInventory = new PlayerInventory(gameName);

        showInventory();
    }


    public void showInventory(){
        createInventoryPanel();
        addItems();
    }

    public void createInventoryPanel(){
        inventoryPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("demo/img/inventory/Inventory.png");
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        inventoryPanel.setLayout(null);
        inventoryPanel.setBounds(0, 0, boxSize, boxSize); 

        inventoryPanel.setLocation(0,0);

        newWindowPanel.add(inventoryPanel);
    }

    private void addItems(){
        if(playerInventory.wastes > 0){addWasteItem();}
        if(playerInventory.apples > 0){addAppleItem();}
        if(playerInventory.chocolatines > 0){addChocolatineItem();}
        if(playerInventory.croissants > 0){addCroissantItem();}
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
        wastesLabel = addLabel(wastesLabel,position, String.valueOf(playerInventory.wastes) + '/' + String.valueOf(playerInventory.maxWastes));
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
        applesLabel = addLabel(applesLabel,position, String.valueOf(playerInventory.apples) + '/' + String.valueOf(playerInventory.maxApples));
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
        chocolatinesLabel = addLabel(chocolatinesLabel,position, String.valueOf(playerInventory.chocolatines) + '/' + String.valueOf(playerInventory.maxChocolatines));
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
        croissantsLabel = addLabel(croissantsLabel,position, String.valueOf(playerInventory.croissants) + '/' + String.valueOf(playerInventory.maxCroissants));
    }

    private int[] getPosition(){
        for(int r = 0; r <= 3; r++){
            for(int c = 0; c <= 3; c++){
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




     public static void main(String[] args){
        JFrame frame;
        frame = new JFrame("Main Game Window");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        
        frame.getContentPane().setBackground(new java.awt.Color(90, 90, 90));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 800, 800);

        frame.add(panel);

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);

        new Inventory(panel,800);
    }
}
