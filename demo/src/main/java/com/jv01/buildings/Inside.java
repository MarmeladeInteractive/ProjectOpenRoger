package com.jv01.buildings;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;
import com.jv01.generations.Chunks;
import com.jv01.screens.GameWindowsSize;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
    public String className;

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

        createInside();
    }

    private void getInsideValue(){
        Document doc = save.getDocumentXml(gameName,"functional/insides");
        Element element = save.getElementById(doc, "inside", String.valueOf(type));

        this.name = save.getChildFromElement(element, "name");
        this.imageUrl = save.getChildFromElement(element, "bacPic");
        this.doorSoundId = save.getChildFromElement(element, "doorSoundId");
        this.className = save.getChildFromElement(element, "className");
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

    @SuppressWarnings("unchecked")
    private void createInside(){
        try {
            if(!isInsideBuilding)soundManager.playSFX(doorSoundId);
            Class<?> clazz = Class.forName(className);
        
            Constructor<?> constructor = clazz.getConstructor(String.class, int.class, JPanel.class);
        
            Object instance = constructor.newInstance(gameName, boxSize, backgroundPanel);
            
            Field field = clazz.getField("restrictedAreas");
            Object value = field.get(instance);
            addRestrictedAreas((List<int[][]>)value);

            field = clazz.getField("trigerEvents");
            value = field.get(instance);
            addTrigerEventsAreas((List<Object[]>)value);

        } catch (Exception e) {
            e.printStackTrace(); 
        }
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
