package com.jv01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class Items {
    public String name;
    public int type;
    public int price;
    public String description;
    public int[] size;
    public String imageUrl;
    public String spam;

    public int offsetX;
    public int offsetY;

    public List<Object[]> trigerEvents = new ArrayList<>();
    public int trigerSize = 50;

    public JLabel objectLabel;
    public JPanel backgroundPanel;

    public boolean refreshDisplay = false;
    public boolean isExist = false;

    private String[] names = {
        "Waste",
        "Apple"
    };
    private int[][] sizes = {
        {50,50},
        {50,50},
    };
    private String[] descriptions = {
        "",
        ""
    };
    private int[] prices = {
        0,
        0,
    };
    public String[][] imagesUrls = {
        {
            "demo\\img\\items\\waste01.png",
            "demo\\img\\items\\waste02.png",
            "demo\\img\\items\\waste03.png",
            "demo\\img\\items\\waste04.png",
        },
        {
            "demo\\img\\items\\apple01.png",
            "demo\\img\\items\\apple02.png",
            "demo\\img\\items\\apple03.png",
            "demo\\img\\items\\apple04.png",
        },
    };

    private String[] interactsSpams = {
        "'e' pour ramasser les déchets",
        "'e' pour ramasser la pomme",
    };

    private Random random = new Random();

    public Items(int type){
        this.type = type;
        this.name = this.names[type];
        this.price = this.prices[type];
        this.description = this.descriptions[type];
        this.size = this.sizes[type];

        this.imageUrl = this.imagesUrls[type][random.nextInt(4)];

        this.spam = interactsSpams[type];

        getOffset();
    }

    public Object[] addItem(int x, int y, JPanel backgroundPanel){
        Objects obj = new Objects(x+offsetX, y+ offsetY, size, imageUrl, 0, backgroundPanel);  
        
        Object[] item01 = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "item",
            this
        };

        objectLabel = obj.objectLabel;
        this.backgroundPanel = backgroundPanel;
        this.isExist = true;

        return item01;
    }

    public void removeItem(){
        backgroundPanel.remove(objectLabel);
    }

    public void interact(Player player){
        if(player.keyBord.interactKeyPressed){
 
            switch (type) {
                case 0:
                    if(player.inventory.wastes < player.inventory.maxWastes){
                        removeItem();
                        this.isExist = false;
                        //player.money += 1;
                        player.wasteCollected ++;
                        player.inventory.wastes ++;

                        player.save();
                        player.inventory.saveWastes();

                        refreshDisplay = true;
                    }else{
                        player.alertMessage = "Plus d'espace dans l'inventaire";
                        player.displayAlert = true;
                    }
                    break;
                case 1:
                    if(player.inventory.apples < player.inventory.maxApples){
                        removeItem();
                        this.isExist = false;
                        //player.money += 1;
                        //player.wasteCollected ++;
                        player.inventory.apples ++;

                        player.save();
                        player.inventory.saveApples();

                        refreshDisplay = true;
                    }else{
                        player.alertMessage = "Plus d'espace dans l'inventaire";
                        player.displayAlert = true;
                    }
                    break;
            
                default:
                    break;
            }

        }
    }    


    public void getOffset(){
        this.offsetX = random.nextInt(20 + 20) - 20;
        this.offsetY = random.nextInt(20 + 20) - 20;
    }
}
