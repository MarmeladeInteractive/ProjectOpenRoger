package com.jv01;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.JLabel;

public class Items {
    private Save save = new Save();
    public String gameName;

    public String name;
    public int id;
    public int type;

    public int buyPrice;
    public int sellPrice;

    public String description;
    public int[] size;
    public String imageUrl;
    public String defaultImageUrl;
    public String spam;

    public int offsetX;
    public int offsetY;

    public List<Object[]> trigerEvents = new ArrayList<>();
    public int trigerSize = 50;

    public JLabel objectLabel;
    public JPanel backgroundPanel;

    public boolean refreshDisplay = false;
    public boolean isExist = false;

    private Random random = new Random();

    public Items(String gameName, int id){
        this.gameName = gameName;
        this.id = id;

        getItemsValues();

        getOffset();
    }

    private void getItemsValues(){
        Document doc = save.getDocumentXml(gameName,"functional/items");
        Element element = save.getElementById(doc, "item", String.valueOf(id));

        this.name = save.getChildFromElement(element, "name");

        this.buyPrice = Integer.parseInt(save.getChildFromElement(element, "purchasePrice"));
        this.sellPrice = Integer.parseInt(save.getChildFromElement(element, "sellingPrice"));

        this.description = save.getChildFromElement(element, "description");
        this.size = save.stringToIntArray(save.getChildFromElement(element, "size"));

        this.imageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[random.nextInt(4)];
        this.imageUrl = save.dropSpaceFromString(this.imageUrl);

        this.defaultImageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[0];
        this.defaultImageUrl = save.dropSpaceFromString(this.defaultImageUrl);

        this.spam = save.getChildFromElement(element, "interactsSpam");
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
 
            switch (id) {
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
