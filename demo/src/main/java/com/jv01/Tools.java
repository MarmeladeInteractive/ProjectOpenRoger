package com.jv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Tools {
    public Save save = new Save();
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

    public void interact(Player player){
        doc = save.getDocumentXml(player.gameName, "partyHouse");

        if(player.keyBord.interactKeyPressed){
            if(level==0){
                switch (id) {
                    case 0:
                        if(player.isEnoughMoney(price,true)){
                            
                            save.changeElementChildValue(player.gameName, "partyHouse", "partyHouse", "partyHouse", "printerLevel", String.valueOf(1));
                            player.keyBord.interactKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 1:
                        if(player.isEnoughMoney(price,true)){
                            
                            save.changeElementChildValue(player.gameName, "partyHouse", "partyHouse", "partyHouse", "ovenLevel", String.valueOf(1));
                            player.keyBord.interactKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 2:
                        if(player.isEnoughMoney(price,true)){
                            
                            save.changeElementChildValue(player.gameName, "partyHouse", "partyHouse", "partyHouse", "transportationLevel", String.valueOf(1));
                            player.keyBord.interactKeyPressed = false;
                            refresh = true;
                        }
                        break;

                    case 3:
                        if(player.isEnoughMoney(price,true)){
                            
                            save.changeChunkBuildingType(player.gameName,player.chunk,0);
                                                      
                            player.keyBord.interactKeyPressed = false;
                            player.positionX = -100;
                        }
                        break;
                    case 4:
                        if(player.isEnoughMoney(price,true)){
                            
                            save.changeChunkBuildingType(player.gameName,player.chunk,0);
                                                      
                            player.keyBord.interactKeyPressed = false;
                            player.positionX = -100;
                        }
                        break;
                    case 5:
                        if(player.inventory.wastes >= 0){
                            
                            player.money += player.inventory.wastes;

                            player.inventory.wastes = 0;

                            player.save();
                            player.inventory.saveWastes();

                            refreshDisplay = true;
                                                      
                            player.keyBord.interactKeyPressed = false;
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
                        if((player.inventory.maxChocolatines != player.inventory.chocolatines)&&(player.inventory.maxCroissants != player.inventory.croissants))if(player.isEnoughMoney(usePrice,true)){
                            if((player.inventory.maxChocolatines - player.inventory.chocolatines)>=(level*2)){
                                player.inventory.chocolatines += level*2;
                            }else{
                                player.inventory.chocolatines = player.inventory.maxChocolatines;
                            }
                            if((player.inventory.maxCroissants - player.inventory.croissants)>=(level*2)){
                                player.inventory.croissants += level*2;
                            }else{
                                player.inventory.croissants = player.inventory.maxCroissants;
                            }
                            player.inventory.saveChocolatines();
                            player.inventory.saveCroissants();
                            refresh = true;
                        }
                        break;
                    case 2:

                        break;
                
                    default:
                        break;
                }
            }
        }else if(player.keyBord.upgradeKeyPressed){
            if(level<3){
                switch (id) {
                    case 0:
                        if(player.isEnoughMoney(price,true)){
                            level++;
                            
                            save.changeElementChildValue(player.gameName, "partyHouse", "partyHouse", "partyHouse", "printerLevel", String.valueOf(level));
                            player.keyBord.upgradeKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 1:
                        if(player.isEnoughMoney(price,true)){
                            level++;
                            
                            save.changeElementChildValue(player.gameName, "partyHouse", "partyHouse", "partyHouse", "ovenLevel", String.valueOf(level));
                            player.keyBord.upgradeKeyPressed = false;
                            refresh = true;
                        }
                        break;
                    case 2:
                        if(player.isEnoughMoney(price,true)){
                            level++;
                            
                            save.changeElementChildValue(player.gameName, "partyHouse", "partyHouse", "partyHouse", "transportationLevel", String.valueOf(level));
                            player.keyBord.upgradeKeyPressed = false;
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
