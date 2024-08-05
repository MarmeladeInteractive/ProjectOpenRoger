package com.jv01.buildings;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;
import com.jv01.generations.Chunks;
import com.jv01.screens.GameWindowsSize;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Inside {
    private Save save = new Save();
    private Chunks chunk;

    public GameWindowsSize GWS = new GameWindowsSize(false);

    public String gameName;
    public String name;
    public int type;
    public String imageUrl;
    public boolean isInsideBuilding;

    private SoundManager soundManager;

    private String doorSoundId;

    public int boxSize;

    public JPanel backgroundPanel;

    public List<int[][]> restrictedAreas = new ArrayList<>();
    public List<Object[]> trigerEvents = new ArrayList<>();

    public Inside(Chunks chunk){
        this.chunk = chunk;

        this.gameName = chunk.gameName;

        soundManager = new SoundManager(gameName);

        this.boxSize =  GWS.boxSize;

        this.isInsideBuilding = chunk.isInsideBuilding;
        this.backgroundPanel = chunk.backgroundPanel;

        this.type = chunk.bType[0]; 

        getInsideValue();

        addRestrictedBorders();

        switch (type) {
            case 0:
                createPartieHouseInside();
                break;
            case 1:
                createEmptyHouseInside();
                break;

            case 4:
                createBakeryInside();
                break;

            case 6:
                createAbandonedHouseInside();
                break;

            case 8:
                createCorpoHouseInside();
                break;

            case 9:
                createBarInside();
                break;

            case 10:
                createFarmInside();
                break;
        
            default:
                break;
        }
    }

    private void getInsideValue(){
        Document doc = save.getDocumentXml(gameName,"functional/insides");
        Element element = save.getElementById(doc, "inside", String.valueOf(type));

        this.name = save.getChildFromElement(element, "name");
        this.imageUrl = save.getChildFromElement(element, "bacPic");
        this.doorSoundId = save.getChildFromElement(element, "doorSoundId");
    }

    private void addRestrictedBorders(){
        this.restrictedAreas.add(new int[][]{ 
            {0,0},
            {120*GWS.boxScale,boxSize} 
        });
        this.restrictedAreas.add(new int[][]{ 
            {0,0},
            {boxSize,190*GWS.boxScale} 
        });
        this.restrictedAreas.add(new int[][]{ 
            {boxSize-120*GWS.boxScale,0},
            {boxSize,boxSize} 
        });
        this.restrictedAreas.add(new int[][]{ 
            {0,boxSize-100*GWS.boxScale},
            {(boxSize/2) - 50*GWS.boxScale,boxSize} 
        });
        this.restrictedAreas.add(new int[][]{ 
            {(boxSize/2) + 80*GWS.boxScale,boxSize-100*GWS.boxScale},
            {boxSize-50*GWS.boxScale,boxSize}  
        });
    }

    private void createPartieHouseInside(){
        if(!isInsideBuilding)soundManager.playSFX(doorSoundId);
        PartieHouse partieHouse = new PartieHouse(gameName,boxSize, backgroundPanel);
        addRestrictedAreas(partieHouse.restrictedAreas);
        addTrigerEventsAreas(partieHouse.trigerEvents);
    }

    private void createAbandonedHouseInside(){
        if(!isInsideBuilding)soundManager.playSFX(doorSoundId);
        AbandonedHouse abandonedHouse = new AbandonedHouse(gameName,boxSize, backgroundPanel);
        addRestrictedAreas(abandonedHouse.restrictedAreas);
        addTrigerEventsAreas(abandonedHouse.trigerEvents);
    }

    private void createBakeryInside(){
        if(!isInsideBuilding)soundManager.playSFX(doorSoundId);
        Bakery bakery = new Bakery(gameName, backgroundPanel);
        addRestrictedAreas(bakery.restrictedAreas);
        addTrigerEventsAreas(bakery.trigerEvents);
    }

    private void createEmptyHouseInside(){
        if(!isInsideBuilding)soundManager.playSFX(doorSoundId);
        EmptyHouse emptyHouse = new EmptyHouse(gameName,boxSize, backgroundPanel);
        addRestrictedAreas(emptyHouse.restrictedAreas);
        addTrigerEventsAreas(emptyHouse.trigerEvents);
    }

    private void createCorpoHouseInside(){
        if(!isInsideBuilding)soundManager.playSFX(doorSoundId);
        CorporationHouse corpoHouse = new CorporationHouse(gameName,boxSize, backgroundPanel);
        addRestrictedAreas(corpoHouse.restrictedAreas);
        addTrigerEventsAreas(corpoHouse.trigerEvents);
    }

    private void createBarInside(){
        if(!isInsideBuilding)soundManager.playSFX(doorSoundId);
        BarHouse barHouse = new BarHouse(gameName, boxSize, backgroundPanel);
        addRestrictedAreas(barHouse.restrictedAreas);
        addTrigerEventsAreas(barHouse.trigerEvents);
    }

    private void createFarmInside(){
        if(!isInsideBuilding)soundManager.playSFX(doorSoundId);
        Farm farm = new Farm(gameName, boxSize, backgroundPanel);
        addRestrictedAreas(farm.restrictedAreas);
        addTrigerEventsAreas(farm.trigerEvents);
    }


    private void addRestrictedAreas(List<int[][]> buildingRestrictedAreas){
        for (int[][] rest : buildingRestrictedAreas) {
            restrictedAreas.add(rest);
        }
    }
    private void addTrigerEventsAreas(List<Object[]> buildingTrigerEventsAreas){
        for (Object[] trigEvent : buildingTrigerEventsAreas) {
            trigerEvents.add(trigEvent);
        }
    }
}
