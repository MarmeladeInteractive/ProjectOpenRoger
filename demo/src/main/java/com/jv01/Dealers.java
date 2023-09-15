package com.jv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Dealers {
    public Save save = new Save();
    public String gameName;

    public boolean refresh = false;
    public boolean refreshDisplay = false;

    public int id;
    public int level;

    public String name;
    public String imageUrl;
    public int[] size;

    public String description;

    public String sellSpam;
    public String buySpam;

    public int itemSoldId;

    public int sellPrice;
    public int buyPrice;

    public String spam;

    public Document doc;
    public Element element;


    public Dealers(String gameName, int id){
        this.gameName = gameName;

        this.id = id;

        getDealerValues();

        this.spam = "'e' pour "+ sellSpam + " " + String.valueOf(sellPrice) + "€"+ "<br>"+
                    "'u' pour "+ buySpam  + " " + String.valueOf(buyPrice) + "€";
        
    }

    private void getDealerValues(){


        Document doc = save.getDocumentXml(gameName,"functional/dealers");
        Element element = save.getElementById(doc, "dealer", String.valueOf(id));

        this.name = save.getChildFromElement(element, "name");
        this.description = save.getChildFromElement(element, "description");
        
        this.imageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[0];
        this.imageUrl = save.dropSpaceFromString(this.imageUrl);

        this.size = save.stringToIntArray(save.getChildFromElement(element, "size"));


        this.sellSpam = save.getChildFromElement(element, "sellSpam");
        this.buySpam = save.getChildFromElement(element, "buySpam");

        this.itemSoldId = Integer.parseInt(save.getChildFromElement(element, "itemSoldId"));


        Items item = new Items(gameName, this.itemSoldId);

        this.sellPrice = item.sellPrice;
        this.buyPrice = item.buyPrice;

    }

    public void interact(Player player){
        doc = save.getDocumentXml(player.gameName, "partyHouse");

        if(player.keyBord.interactKeyPressed){

                switch (id) {
                    case 0:
                        if(player.inventory.apples >= 0){
                            
                            player.money += player.inventory.apples*4;

                            player.inventory.apples = 0;

                            player.save();
                            player.inventory.saveApples();

                            refreshDisplay = true;
                                                      
                            player.keyBord.interactKeyPressed = false;
                        }
                        break;
                
                    default:
                        break;
                }

        }else if(player.keyBord.upgradeKeyPressed){

                switch (id) {
                    case 0:
                        if(player.inventory.apples < player.inventory.maxApples){
                            player.inventory.apples ++;
                            player.money -= 8;
                            player.save();
                            player.inventory.saveApples();

                            refreshDisplay = true;
                                                                       
                            player.keyBord.upgradeKeyPressed = false;
                        }
                        break;
                
                    default:
                        break;
                }
            }
    }
    
}
