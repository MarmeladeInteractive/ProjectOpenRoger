package com.jv01.generations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;

public class Arcades {
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

    public Arcades(String gameName, int id, int level){
        this.gameName = gameName;

        soundManager = new SoundManager(gameName);

        this.id = id;
        this.level = level;


        getArcadeValues();


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

    public void getArcadeValues(){
        Document doc = save.getDocumentXml(gameName,"functional/arcades");
        Element element = save.getElementById(doc, "arcade", String.valueOf(id));

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
        if(mainGameWindow.player.inputsManager.interactKeyPressed){
            if(level==0){
                mainGameWindow.runArcade(id); 
                mainGameWindow.player.inputsManager.interactKeyPressed = false;  
            }else{
                
            }
        }
    }
    
}
