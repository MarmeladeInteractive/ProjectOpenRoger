package com.jv01.generations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;

public class Tools {
    public Save save = new Save();
    public SoundManager soundManager;
    public String gameName;

    public boolean refresh = false;
    public boolean refreshDisplay = false;

    public int id;
    public int level;

    public String name;
    public String imageUrl;
    public int[] size;
    public int price;
    public int usePrice;

    public String updateSpam;
    public String useSpam;
    public String buySpam;

    public String spam;

    public DefaultListModel<List<String>> listModelInteractive = new DefaultListModel<>();
    private List<String> row;

    public Document doc;
    public Element element;

    public Tools(String gameName, int id, int level){
        this.gameName = gameName;

        soundManager = new SoundManager(gameName);

        this.id = id;
        this.level = level;


        getToolValues();


        if(level==0){
            if(buySpam != ""){
                spam = buySpam + " " + price + "€"; 
                row = new ArrayList<>(Arrays.asList(buySpam + " " + price + "€", "tool", "interact"));
                listModelInteractive.addElement(row);
            }else{
                spam = "'e' pour "+ useSpam + " " + price + "€";
                row = new ArrayList<>(Arrays.asList(useSpam + " " + price + "€", "tool", "interact"));
                listModelInteractive.addElement(row);
            }   
        }else if(level < 3){
            spam = "'e' pour "+ useSpam + " " + usePrice + "€"+ "<br>"+
                    "'u' pour "+ updateSpam  + " " + price + "€";
            row = new ArrayList<>(Arrays.asList(useSpam + " " + usePrice + "€", "tool", "interact"));
            listModelInteractive.addElement(row);
            row = new ArrayList<>(Arrays.asList(updateSpam + " " + price + "€", "tool", "upgrade"));
            listModelInteractive.addElement(row);
        }else{
            spam = "'e' pour "+ useSpam + " " + usePrice + "€";
            row = new ArrayList<>(Arrays.asList(useSpam + " " + usePrice + "€", "tool", "interact"));
            listModelInteractive.addElement(row);
        }

    }

    public void getToolValues(){
        Document doc = save.getDocumentXml(gameName,"functional/tools");
        Element element = save.getElementById(doc, "tool", String.valueOf(id));

        this.name = save.getChildFromElement(element, "name");

        this.imageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[level];
        this.imageUrl = save.dropSpaceFromString(this.imageUrl);

        this.size = save.stringToIntArray(save.getChildFromElement(element, "size"));

        String pricesString = save.getChildFromElement(element, "prices");
        this.price = save.stringToIntArray(pricesString)[level];

        String usePriceString = save.getChildFromElement(element, "usePrices");
        this.usePrice = save.stringToIntArray(usePriceString)[level];

        this.updateSpam = save.getChildFromElement(element, "upgradeable");
        this.useSpam = save.getChildFromElement(element, "useSpam");
        this.buySpam = save.getChildFromElement(element, "buySpam");

    }

    public void interact(MainGameWindow mainGameWindow){
        doc = save.getDocumentXml(mainGameWindow.player.gameName, "partyHouse");

        String action = "null";

        try {
            if(mainGameWindow.interactiveListPanel.isSelectedValue && mainGameWindow.interactiveListPanel.selectedValue.get(1) == "tool"){
                switch (mainGameWindow.interactiveListPanel.selectedValue.get(2)) {
                    case "interact":
                        action = "interact";
                        break;
                    case "upgrade":
                        action = "upgrade";
                        break;

                    default:
                        break;
                }
            }
        } catch (Exception e) {
        }

        if(mainGameWindow.player.inputsManager.interactKeyPressed || action == "interact"){
            if(level==0){
                switch (id) {
                    case 0:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "printerLevel", String.valueOf(1));
                            mainGameWindow.player.inputsManager.interactKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 1:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "ovenLevel", String.valueOf(1));
                            mainGameWindow.player.inputsManager.interactKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 2:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "transportationLevel", String.valueOf(1));
                            mainGameWindow.player.inputsManager.interactKeyPressed = false;
                            refresh = true;
                        }
                        break;

                    case 3:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeChunkBuildingType(mainGameWindow.player.gameName,mainGameWindow.player.chunk,0);
                                                      
                            mainGameWindow.player.inputsManager.interactKeyPressed = false;
                            mainGameWindow.player.positionX = -100;
                        }
                        break;
                    case 4:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeChunkBuildingType(mainGameWindow.player.gameName,mainGameWindow.player.chunk,0);
                                                      
                            mainGameWindow.player.inputsManager.interactKeyPressed = false;
                            mainGameWindow.player.positionX = -100;
                        }
                        break;
                    case 5:
                        if(mainGameWindow.player.inventory.itemsCount.get("wastes") >= 0){

                            soundManager.playSFX("waste01");
                            
                            mainGameWindow.player.money += mainGameWindow.player.inventory.itemsCount.get("wastes");

                            mainGameWindow.player.inventory.itemsCount.replace("wastes", 0);

                            mainGameWindow.player.save();
                            mainGameWindow.player.inventory.saveWastes();

                            refreshDisplay = true;
                                                      
                            mainGameWindow.player.inputsManager.interactKeyPressed = false;
                        }
                        break;
                    case 6:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                                
                            save.changeChunkBuildingType(mainGameWindow.player.gameName,mainGameWindow.player.chunk,11);

                            mainGameWindow.playerOwnerships.addOwnership("personalFarm", mainGameWindow.player.chunk, '{'+String.valueOf(mainGameWindow.player.chunk[0])+','+String.valueOf(mainGameWindow.player.chunk[1])+'}');

                            mainGameWindow.player.inputsManager.interactKeyPressed = false;
                            mainGameWindow.player.positionX = -100;
                        }
                        break;        
                
                    default:
                        break;
                }
            }else{
                switch (id) {
                    case 0:
                        
                        break;
                    case 1:
                        if((mainGameWindow.player.inventory.maxChocolatines != mainGameWindow.player.inventory.itemsCount.get("chocolatines"))&&(mainGameWindow.player.inventory.maxCroissants != mainGameWindow.player.inventory.itemsCount.get("croissants")))if(mainGameWindow.player.isEnoughMoney(usePrice,true)){
                            if((mainGameWindow.player.inventory.maxChocolatines - mainGameWindow.player.inventory.itemsCount.get("chocolatines"))>=(level*2)){
                                int val = mainGameWindow.player.inventory.itemsCount.get("chocolatines") + level*2;
                                mainGameWindow.player.inventory.itemsCount.replace("chocolatines",val);
                            }else{
                                mainGameWindow.player.inventory.itemsCount.replace("chocolatines",mainGameWindow.player.inventory.maxChocolatines);
                            }
                            if((mainGameWindow.player.inventory.maxCroissants - mainGameWindow.player.inventory.itemsCount.get("croissants"))>=(level*2)){
                                int val = mainGameWindow.player.inventory.itemsCount.get("croissants") + level*2;
                                mainGameWindow.player.inventory.itemsCount.replace("croissants",val);
                            }else{
                                mainGameWindow.player.inventory.itemsCount.replace("croissants",mainGameWindow.player.inventory.maxCroissants);
                            }
                            mainGameWindow.player.inventory.saveChocolatines();
                            mainGameWindow.player.inventory.saveCroissants();
                            refresh = true;
                        }
                        break;
                    case 2:

                        break;
                
                    default:
                        break;
                }
            }
        }else if(mainGameWindow.player.inputsManager.upgradeKeyPressed || action == "upgrade"){
            if(level<3){
                switch (id) {
                    case 0:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            level++;
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "printerLevel", String.valueOf(level));
                            mainGameWindow.player.inputsManager.upgradeKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 1:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            level++;
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "ovenLevel", String.valueOf(level));
                            mainGameWindow.player.inputsManager.upgradeKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 2:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            level++;
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "transportationLevel", String.valueOf(level));
                            mainGameWindow.player.inputsManager.upgradeKeyPressed = false;
                            refresh = true;
                        }
                        break;
                
                    default:
                        break;
                }
            }
        }
    }
    
}
