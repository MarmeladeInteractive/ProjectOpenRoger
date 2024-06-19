package com.jv01.generations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.player.Player;

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

        if(player.inputsManager.interactKeyPressed){

                switch (id) {
                    case 0:
                        if(player.inventory.getValue("apples") >= 0){
                            
                            player.money += player.inventory.getValue("apples") * sellPrice;

                            player.inventory.changeValue("apples", 0);;

                            player.save();
                            player.inventory.saveApples();

                            refreshDisplay = true;
                                                      
                            player.inputsManager.interactKeyPressed = false;
                        }
                        break;

                    case 1:
                        if(player.inventory.getValue("chocolatines") >= 0){
                            
                            player.money += player.inventory.getValue("chocolatines") * sellPrice;

                            player.inventory.changeValue("chocolatines", 0);

                            player.save();
                            player.inventory.saveChocolatines();

                            refreshDisplay = true;
                                                      
                            player.inputsManager.interactKeyPressed = false;
                        }
                        break;

                    case 2:
                        if(player.inventory.getValue("croissants") >= 0){
                            
                            player.money += player.inventory.getValue("croissants") * sellPrice;

                            player.inventory.changeValue("chocolatines", 0);

                            player.save();
                            player.inventory.saveCroissants();

                            refreshDisplay = true;
                                                      
                            player.inputsManager.interactKeyPressed = false;
                        }
                        break;
                        
                
                    default:
                        break;
                }

        }else if(player.inputsManager.upgradeKeyPressed){

                if(player.isEnoughMoney(buyPrice,false))
                switch (id) {
                    case 0:
                        if(player.inventory.getValue("apples") < player.inventory.maxApples){
                            player.inventory.changeValue("apples", player.inventory.getValue("apples") + 1);
                            player.money -= buyPrice;
                            player.save();
                            player.inventory.saveApples();

                            refreshDisplay = true;
                                                                       
                            player.inputsManager.upgradeKeyPressed = false;
                        }
                        break;

                    case 1:
                        if(player.inventory.getValue("chocolatines") < player.inventory.maxChocolatines){
                            player.inventory.changeValue("chocolatines", player.inventory.getValue("chocolatines") + 1);
                            player.money -= buyPrice;
                            player.save();
                            player.inventory.saveChocolatines();

                            refreshDisplay = true;
                                                                       
                            player.inputsManager.upgradeKeyPressed = false;
                        }
                        break;

                    case 2:
                        if(player.inventory.getValue("croissants") < player.inventory.maxCroissants){
                            player.inventory.changeValue("croissants", player.inventory.getValue("croissants") + 1);
                            player.money -= buyPrice;
                            player.save();
                            player.inventory.saveCroissants();

                            refreshDisplay = true;
                                                                       
                            player.inputsManager.upgradeKeyPressed = false;
                        }
                        break;
                
                    default:
                        break;
                }
            }
    }

    public int getId()
    {
        return id;
    }
    
}
