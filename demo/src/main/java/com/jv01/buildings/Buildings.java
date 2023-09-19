package com.jv01.buildings;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;

public class Buildings {
    public Save save = new Save();
    public String gameName;

    public String name;
    public int id;
    public int type;
    public String imageUrl;
    public String defaultImageUrl;
    public String description;
    public long[] chunk;
    public int[] cell;
    public int[] dimension;
    public String buildingKey;

    public int offsetX = 0;
    public int offsetY = 0;

    public String seed;
    public String mapKey;

    public Buildings(String gameName, int id, long[] chunk, int[] cell, String buildingKey){
        this.gameName = gameName;

        this.id = id;

        this.buildingKey = buildingKey;
        this.chunk = chunk;
        this.cell = cell;

        this.type = getBuildingVariety();

        getBuildingValues();
    }

    public Buildings(){

    }

    private void getBuildingValues(){
        Document doc = save.getDocumentXml(gameName,"functional/buildings");
        Element element = save.getElementById(doc, "building", String.valueOf(id));

        this.name = save.getChildFromElement(element, "name");
        this.description = save.getChildFromElement(element, "description");
        this.dimension = save.stringToIntArray(save.getChildFromElement(element, "size"));

        this.imageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[type];
        this.imageUrl = save.dropSpaceFromString(this.imageUrl);

        this.defaultImageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[0];
        this.defaultImageUrl = save.dropSpaceFromString(this.imageUrl);
    }

    public int getBuildingVariety(){
        int variety = 0;
        char key01 = buildingKey.charAt(1);
        
        if(key01 >= '0' && key01 <= '3'){
            variety = 0;
        }else if(key01 >= '4' && key01 <= '7'){
            variety = 1;
        }else if(key01 >= '8' && key01 <= 'b'){
            variety = 2;
        }else{
            variety = 3;
        }
        return variety;
    }

    public void getOffset(){
        int key02 = Integer.parseInt(String.valueOf(buildingKey.charAt(2)), 16);
        int key03 = Integer.parseInt(String.valueOf(buildingKey.charAt(3)), 16);   

        offsetX = (int)mapRange(key02,0,16,-20,20);
        offsetY = (int)mapRange(key03,0,16,-20,20);
    }

    public double mapRange(double value, double minInput, double maxInput, double minOutput, double maxOutput) {
        return minOutput + ((value - minInput) / (maxInput - minInput)) * (maxOutput - minOutput);
    }


}
