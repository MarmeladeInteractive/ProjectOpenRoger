package com.jv01.generations;

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
                this.spam = "'e' pour "+ buySpam + " " + price + "€"; 
            }else{
                this.spam = "'e' pour "+ useSpam + " " + price + "€";
            }   
        }else if(level < 3){
            this.spam = "'e' pour "+ useSpam + " " + usePrice + "€"+ "<br>"+
                        "'u' pour "+ updateSpam  + " " + price + "€";
        }else{
            this.spam = "'e' pour "+ useSpam + " " + usePrice + "€";
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

        if(mainGameWindow.player.keyBord.interactKeyPressed){
            if(level==0){
                switch (id) {
                    case 0:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "printerLevel", String.valueOf(1));
                            mainGameWindow.player.keyBord.interactKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 1:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "ovenLevel", String.valueOf(1));
                            mainGameWindow.player.keyBord.interactKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 2:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "transportationLevel", String.valueOf(1));
                            mainGameWindow.player.keyBord.interactKeyPressed = false;
                            refresh = true;
                        }
                        break;

                    case 3:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeChunkBuildingType(mainGameWindow.player.gameName,mainGameWindow.player.chunk,0);
                                                      
                            mainGameWindow.player.keyBord.interactKeyPressed = false;
                            mainGameWindow.player.positionX = -100;
                        }
                        break;
                    case 4:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            
                            save.changeChunkBuildingType(mainGameWindow.player.gameName,mainGameWindow.player.chunk,0);
                                                      
                            mainGameWindow.player.keyBord.interactKeyPressed = false;
                            mainGameWindow.player.positionX = -100;
                        }
                        break;
                    case 5:
                        if(mainGameWindow.player.inventory.wastes >= 0){

                            soundManager.playSFX("waste01");
                            
                            mainGameWindow.player.money += mainGameWindow.player.inventory.wastes;

                            mainGameWindow.player.inventory.wastes = 0;

                            mainGameWindow.player.save();
                            mainGameWindow.player.inventory.saveWastes();

                            refreshDisplay = true;
                                                      
                            mainGameWindow.player.keyBord.interactKeyPressed = false;
                        }
                        break;
                    case 6:
                        mainGameWindow.runArcade(0); 
                        mainGameWindow.player.keyBord.interactKeyPressed = false;         
                        break;
                
                    default:
                        break;
                }
            }else{
                switch (id) {
                    case 0:
                        
                        break;
                    case 1:
                        if((mainGameWindow.player.inventory.maxChocolatines != mainGameWindow.player.inventory.chocolatines)&&(mainGameWindow.player.inventory.maxCroissants != mainGameWindow.player.inventory.croissants))if(mainGameWindow.player.isEnoughMoney(usePrice,true)){
                            if((mainGameWindow.player.inventory.maxChocolatines - mainGameWindow.player.inventory.chocolatines)>=(level*2)){
                                mainGameWindow.player.inventory.chocolatines += level*2;
                            }else{
                                mainGameWindow.player.inventory.chocolatines = mainGameWindow.player.inventory.maxChocolatines;
                            }
                            if((mainGameWindow.player.inventory.maxCroissants - mainGameWindow.player.inventory.croissants)>=(level*2)){
                                mainGameWindow.player.inventory.croissants += level*2;
                            }else{
                                mainGameWindow.player.inventory.croissants = mainGameWindow.player.inventory.maxCroissants;
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
        }else if(mainGameWindow.player.keyBord.upgradeKeyPressed){
            if(level<3){
                switch (id) {
                    case 0:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            level++;
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "printerLevel", String.valueOf(level));
                            mainGameWindow.player.keyBord.upgradeKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 1:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            level++;
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "ovenLevel", String.valueOf(level));
                            mainGameWindow.player.keyBord.upgradeKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 2:
                        if(mainGameWindow.player.isEnoughMoney(price,true)){
                            level++;
                            
                            save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "transportationLevel", String.valueOf(level));
                            mainGameWindow.player.keyBord.upgradeKeyPressed = false;
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
