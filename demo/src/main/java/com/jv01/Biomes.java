package com.jv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Biomes {
    public Save save = new Save();
    public String gameName;
    public String biomeKey;

    public String imageUrl;
    public int biomeType;
    public String description;
    public String name;

    
    public Biomes(String gameName, String biomeKey){
        this.gameName = gameName;
        this.biomeKey = biomeKey;

        generateBiome();
    }

    private void generateBiome(){
        char key1 = biomeKey.charAt(0);
        
        if (key1 >= '0' && key1 <= '1') {
            biomeType = 0;
        }else if (key1 >= '2' && key1 <= '3') {
            biomeType = 1;
        }else if (key1 >= '4' && key1 <= '5') {
            biomeType = 2;
        }else if (key1 >= '6' && key1 <= '7') {
            biomeType = 3;
        }else if (key1 >= '8' && key1 <= '9') {
            biomeType = 4;
        }else if (key1 >= 'a' && key1 <= 'b') {
            biomeType = 5;
        }else if (key1 >= 'c' && key1 <= 'd') {
            biomeType = 6;
        }else if (key1 >= 'e' && key1 <= 'f') {
            biomeType = 7;
        }

        Document doc = save.getDocumentXml(gameName,"functional/biomes");
        Element element = save.getElementById(doc, "biome", String.valueOf(biomeType));

        this.description = save.getChildFromElement(element, "description");
        this.name = save.getChildFromElement(element, "name");

        this.imageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[0];
        this.imageUrl = save.dropSpaceFromString(this.imageUrl);
    }
}
