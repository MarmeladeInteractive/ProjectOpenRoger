package com.jv01.buildings;

import java.lang.annotation.Retention;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jv01.fonctionals.Atlas;
import com.jv01.fonctionals.Save;
import com.jv01.generations.Chunks;
import com.jv01.generations.MainGameWindow;
import com.jv01.generations.specialStructures.SpecialStructures;

public class Buildings {
    public Save save = new Save();
    public Atlas atlas;
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

    public SpecialStructures specialStructures;

    public int specialStructuresNumber = 0;
    public String[] specialStructuresPosibleTypes;

    public int offsetX = 0;
    public int offsetY = 0;

    public String seed;
    public String mapKey;

    public Buildings(String gameName, int id, long[] chunk, int[] cell, String buildingKey){
        this.gameName = gameName;
        this.atlas = new Atlas(gameName);

        this.specialStructures = new SpecialStructures(gameName);

        this.id = id;

        this.buildingKey = buildingKey;
        this.chunk = chunk;
        this.cell = cell;

        this.type = getBuildingVariety();

        getBuildingValues();

        if(id == 8)getCorporationHouseValues();
    }

    public Buildings(){

    }

    private void getBuildingValues(){
        Document doc = save.getDocumentXml(gameName,"functional/buildings");
        Element element = save.getElementById(doc, "building", String.valueOf(id));

        this.name = save.getChildFromElement(element, "name");
        this.description = save.getChildFromElement(element, "description");
        this.dimension = save.stringToIntArray(save.getChildFromElement(element, "size"));

        this.specialStructuresNumber = Integer.parseInt(save.getChildFromElement(element, "specialStructuresNumber"));
        this.specialStructuresPosibleTypes = save.stringToStringArray(save.getChildFromElement(element, "specialStructuresPosibleTypes"));

        this.imageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[type];
        this.imageUrl = save.dropSpaceFromString(this.imageUrl);

        this.defaultImageUrl = save.stringToStringArray(save.getChildFromElement(element, "imagesUrls"))[0];
        this.defaultImageUrl = save.dropSpaceFromString(this.imageUrl);
    }

    private void getCorporationHouseValues(){
        Document doc = save.getDocumentXml(gameName,"corporations");
        NodeList corporationNodes = doc.getElementsByTagName("corporation");
        int numberOfCorporations = corporationNodes.getLength();

        int unassignedId = -1;
        boolean corporationHouseExist = false;

        for(int i = 1; i <= numberOfCorporations; i++){
            Element corp = save.getElementById(doc, "corporation",String.valueOf(i));
            int[] chunkInt = save.stringToIntArray(save.getChildFromElement(corp, "corporationHouseChunk"));
            long[] chunk = {(long)chunkInt[0],(long)chunkInt[1]};

            if(Arrays.equals(chunk, this.chunk)){
                this.name = save.getChildFromElement(corp, "corporationName");
                this.description = save.getChildFromElement(corp, "catchPhrase");
                corporationHouseExist = true;
                break;
            }else if(Arrays.equals(chunk, new long[]{0, 0})){
                unassignedId = i;
            }
        }

        if(!corporationHouseExist){
            if(unassignedId>0){
                save.changeElementChildValue(gameName,"corporations","corporation",String.valueOf(unassignedId),"corporationHouseChunk",'{'+String.valueOf(this.chunk[0])+','+String.valueOf(this.chunk[1])+'}');
                getCorporationHouseValues();
            }else{
                this.id = 7;
                getBuildingValues();
            }
        }
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
