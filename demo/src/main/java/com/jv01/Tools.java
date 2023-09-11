package com.jv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Tools {
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

    

    public String[] toolsNames = {
        "Imprimante",
        "Four",
        "Véhicule",

        "Maison abandonee",
        "Maison vide",

        "Poubelle",

        "Stand de pomme",
        "Stand de chocolatines",
        "Stand de croissants",
    };

    public String[][] toolsPics = {
        {
            "demo\\img\\achat.png",
            "demo\\img\\tools\\imprimante01.png",
            "demo\\img\\tools\\imprimante01.png",
            "demo\\img\\tools\\imprimante01.png",
        },
        {
            "demo\\img\\achat.png",
            "demo\\img\\tools\\four01.png",
            "demo\\img\\tools\\four01.png",
            "demo\\img\\tools\\four01.png",
        },
        {
            "demo\\img\\achat.png",
            "demo\\img\\tools\\velo01.png",
            "demo\\img\\tools\\moto01.png",
            "demo\\img\\tools\\voiture01.png",
        },

        {
            "demo\\img\\achat.png",
            "demo\\img\\achat.png",
            "demo\\img\\achat.png",
            "demo\\img\\achat.png",
        },
        {
            "demo\\img\\achat.png",
            "demo\\img\\achat.png",
            "demo\\img\\achat.png",
            "demo\\img\\achat.png",
        },
        {
            "demo\\img\\tools\\poubelle01.png",
            "demo\\img\\tools\\poubelle01.png",
            "demo\\img\\tools\\poubelle01.png",
            "demo\\img\\tools\\poubelle01.png",
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
        },
        {
            "demo\\img\\null.png",
            "demo\\img\\null.png",
            "demo\\img\\null.png",
            "demo\\img\\null.png",
        }
    };

    public int[][] toolsPrices = {
        {
            100,
            500,
            800,
            1000
        },
        {
            150,
            600,
            850,
            1100
        },
        {
            400,
            1000,
            3000,
            10000
        },

        {
            1000,
            1000,
            1000,
            1000
        },
        {
            2000,
            2000,
            2000,
            2000
        },
        {
            0,
            0,
            0,
            0
        },
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

     public int[][] toolsUsePrices = {
        {
            10,
            15,
            30,
            50
        },
        {
            20,
            30,
            45,
            50
        },
        {
            0,
            1,
            2,
            3
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

    public int[][] toolsSizes = {
        {150,150},
        {150,150},
        {200,200},

        {150,150},
        {150,150},
        {80,80},
        {50,50},
        {50,50},
        {50,50},
    };

    public String[] toolsbuySpams = {
        "acheter une imprimante",
        "acheter un four",
        "acheter un véhicule",

        "renover la maison",
        "acheter la maison",
        "",
        "acheter une pomme",
        "acheter une chocolatine",
        "acheter un croissant"
    };

    public String[] toolsUpdateSpams = {
        "amélirer l'imprimante",
        "amélirer le four",
        "amélirer le véhicule",

        "",
        "",
        "",
        "",
        "",
        ""
    };

    public String[] toolsUseSpams = {
        "utiliser l'imprimante",
        "utiliser le four",
        "utiliser le véhicule",

        "",
        "",
        "utiliser la poubelle",
        "vendre des pommes",
        "vendre des chocolatines",
        "vendre des croissants"
    };

    public Tools(int type, int level){
        this.type = type;
        this.level = level;

        this.name = toolsNames[type];
        this.imageUrl = toolsPics[type][level];
        this.size = toolsSizes[type];
        this.price = toolsPrices[type][level];
        this.usePrice = toolsUsePrices[type][level];

        this.updateSpam = toolsUpdateSpams[type];
        this.useSpam = toolsUseSpams[type];
        this.buySpam = toolsbuySpams[type];

        if((type != 6)&&(type != 7)&&(type != 8)){
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
        }else{
            this.spam = "'e' pour "+ useSpam + " " + String.valueOf(price/2) + "€"+ "<br>"+
                        "'u' pour "+ buySpam  + " " + String.valueOf(price) + "€";
        }
    }

    public void interact(Player player){
        doc = save.getDocumentXml(player.gameName, "partyHouse");

        if(player.keyBord.interactKeyPressed){
            if(level==0){
                switch (type) {
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
                    case 6:
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
            }else{
                
            }
        }else if(player.keyBord.upgradeKeyPressed){
            if(level<3){
                switch (type) {
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

                    case 6:
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
    
}
