package com.jv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Dealer {
    public Save save = new Save();
    public boolean refresh = false;
    public boolean refreshDisplay = false;

    public int type;
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

    

    public String[] DealersNames = {
        "Stand de pomme",
        "Stand de chocolatines",
        "Stand de croissants",
    };

    public String[][] DealersPics = {
        {
            "demo\\img\\null.png",
            "demo\\img\\null.png",
            "demo\\img\\null.png",
            "demo\\img\\null.png",
        },
        {
            "demo\\img\\null.png",
            "demo\\img\\null.png",
            "demo\\img\\null.png",
            "demo\\img\\null.png",
        },
        {
            "demo\\img\\null.png",
            "demo\\img\\null.png",
            "demo\\img\\null.png",
            "demo\\img\\null.png",
        }
    };

    public int[][] DealersPrices = {
        {
            8,
            8,
            8,
            8
        },
        {
            3,
            3,
            3,
            3
        },
        {
            2,
            2,
            2,
            2
        }
    };

     public int[][] DealersUsePrices = {
        {
            0,
            0,
            0,
            0
        },
        {
            0,
            0,
            0,
            0
        },
        {
            0,
            0,
            0,
            0
        }
    };

    public int[][] DealersSizes = {
        {50,50},
        {50,50},
        {50,50},
    };

    public String[] DealersbuySpams = {
        "acheter une pomme",
        "acheter une chocolatine",
        "acheter un croissant"
    };

    public String[] DealersUpdateSpams = {
        "",
        "",
        ""
    };

    public String[] DealersUseSpams = {
        "vendre des pommes",
        "vendre des chocolatines",
        "vendre des croissants"
    };

    public Dealer(int type){
        this.type = type;

        this.name = DealersNames[type];
        this.imageUrl = DealersPics[type][level];
        this.size = DealersSizes[type];
        this.price = DealersPrices[type][level];
        this.usePrice = DealersUsePrices[type][level];

        this.updateSpam = DealersUpdateSpams[type];
        this.useSpam = DealersUseSpams[type];
        this.buySpam = DealersbuySpams[type];


        this.spam = "'e' pour "+ useSpam + " " + String.valueOf(price/2) + "€"+ "<br>"+
                    "'u' pour "+ buySpam  + " " + String.valueOf(price) + "€";
        
    }

    public void interact(Player player){
        doc = save.getDocumentXml(player.gameName, "partyHouse");

        if(player.keyBord.interactKeyPressed){

                switch (type) {
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

                switch (type) {
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
