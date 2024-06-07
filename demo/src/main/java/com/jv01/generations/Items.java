package com.jv01.generations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;
import com.jv01.player.Player;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;

public class Items {
    private Save save = new Save();
    private SoundManager soundManager;
    public String gameName;

    public String name;
    public int id;
    public int type;
    public String interactsSoundId;

    public int buyPrice;
    public int sellPrice;

    public int hungerValue;
    public int thirstValue;

    public String description;
    public int[] size;
    public String imageUrl;
    public String defaultImageUrl;
    public String spam;

    public DefaultListModel<List<String>> listModelInteractive = new DefaultListModel<>();
    List<String> interactIconsList = new ArrayList<>();

    public int offsetX;
    public int offsetY;

    public int x;
    public int y;

    public List<Object[]> trigerEvents = new ArrayList<>();
    public int trigerSize = 50;

    public JLabel objectLabel;
    public JPanel backgroundPanel;

    public boolean refreshDisplay = false;
    public boolean isExist = false;

    private Random random = new Random();

    public Items(String gameName, int id){
        this.gameName = gameName;

        soundManager = new SoundManager(gameName);
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

        this.hungerValue = Integer.parseInt(save.getChildFromElement(element, "hungerValue"));
        this.thirstValue = Integer.parseInt(save.getChildFromElement(element, "thirstValue"));

        this.description = save.getChildFromElement(element, "description");
        this.size = save.stringToIntArray(save.getChildFromElement(element, "size"));

        this.imageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[random.nextInt(4)];
        this.imageUrl = save.dropSpaceFromString(this.imageUrl);

        this.defaultImageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[0];
        this.defaultImageUrl = save.dropSpaceFromString(this.defaultImageUrl);

        this.spam = save.getChildFromElement(element, "interactsSpam");
        List<String> row = new ArrayList<>(Arrays.asList(spam, "item"));
        this.listModelInteractive.addElement(row);
        this.spam = save.getChildFromElement(element, "consumeSpam");
        row = new ArrayList<>(Arrays.asList(spam, "item"));
        this.listModelInteractive.addElement(row);

        this.interactsSoundId = save.getChildFromElement(element, "interactsSoundId");

        this.interactIconsList.add("interactPickUp");
        this.interactIconsList.add("interactConsume");
    }

    public Object[] addItem(int newX, int newY, JPanel backgroundPanel){
        x = newX + offsetX;
        y = newY + offsetY;

        Objects obj = new Objects(x, y, size, imageUrl, 0, backgroundPanel);  

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

    public void interact(MainGameWindow mainGameWindow){
        if(!mainGameWindow.selectionWheel.isOpen)mainGameWindow.selectionWheel.openSelectionWheel(x, y,"item",interactIconsList);
        //if(mainGameWindow.player.inputsManager.interactKeyPressed || (mainGameWindow.interactiveListPanel.isSelectedValue && mainGameWindow.interactiveListPanel.selectedValue.get(1) == "item")){
        if(mainGameWindow.player.inputsManager.interactKeyPressed || (mainGameWindow.selectionWheel.isIconSelected && mainGameWindow.selectionWheel.interactType == "item")){

            switch (id) {
                case 0:
                    if(mainGameWindow.player.inventory.wastes < mainGameWindow.player.inventory.maxWastes){
                        removeItem();

                        soundManager.playSFX(interactsSoundId);

                        this.isExist = false;
                        //player.money += 1;
                        mainGameWindow.player.wasteCollected ++;
                        mainGameWindow.player.inventory.wastes ++;

                        mainGameWindow.player.save();
                        mainGameWindow.player.inventory.saveWastes();

                        refreshDisplay = true;
                    }else{
                        mainGameWindow.player.alertMessage = "Plus d'espace dans l'inventaire";
                        mainGameWindow.player.displayAlert = true;
                    }
                    break;
                case 1:
                    if(mainGameWindow.player.inventory.apples < mainGameWindow.player.inventory.maxApples){
                        removeItem();

                        soundManager.playSFX(interactsSoundId);
                        
                        this.isExist = false;
                        //player.money += 1;
                        //player.wasteCollected ++;
                        mainGameWindow.player.inventory.apples ++;

                        mainGameWindow.player.save();
                        mainGameWindow.player.inventory.saveApples();

                        refreshDisplay = true;
                    }else{
                        mainGameWindow.player.alertMessage = "Plus d'espace dans l'inventaire";
                        mainGameWindow.player.displayAlert = true;
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
