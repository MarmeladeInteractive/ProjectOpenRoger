package com.jv01.generations.specialStructures;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.awt.Color;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.generations.Chunks;
import com.jv01.generations.MainGameWindow;

public class SpecialStructures {
    public Save save = new Save();
    public SpecialStructuresTypes specialStructuresType;

    String gameName;

    public Document doc;
    public Element element;

    public String type;
    public String elementId;

    public List<int[][]> restrictedAreas = new ArrayList<>();
    public List<Object[]> trigerEvents = new ArrayList<>();

    public int x = 0;
    public int y = 0;
    
    public boolean refreshDisplay = false;

    public JPanel panel;

    public SpecialStructures(String gameName){
        this.gameName = gameName;

        this.specialStructuresType = new SpecialStructuresTypes(gameName);
    }

    private void createSpecialStructureElement(String type, long[] chunk, String elementId){
        Element specialStructure = doc.createElement("specialStructure");

        specialStructure.setAttribute("id", elementId);
        
        save.createXmlElement(specialStructure,doc,"chunk", '{'+String.valueOf(chunk[0])+','+String.valueOf(chunk[1])+'}');
        save.createXmlElement(specialStructure,doc,"type", type);
        save.createXmlElement(specialStructure,doc,"elementId", elementId);

        doc.getDocumentElement().appendChild(specialStructure);
    }

    public void addSpecialStructure(String type, long[] chunk, String elementId){
        this.doc = save.getDocumentXml(gameName,"functional/specialStructures/specialStructures");
        createSpecialStructureElement(type, chunk, elementId);
        save.saveXmlFile(doc, gameName, "functional/specialStructures/specialStructures");
    }

    public void getElementsValues(String elementId){
        Document doc = save.getDocumentXml(gameName,"functional/specialStructures/specialStructures");
        Element element = save.getElementById(doc, "specialStructure", String.valueOf(elementId));
       
        this.type = save.getChildFromElement(element, "type");

        this.elementId = elementId;

        specialStructuresType = specialStructuresType.getElementsTypeValues(type);
    }

    public String[][] createSpecialStructures(String key, int specialStructuresNumber, String[]specialStructuresPosibleTypes, long[] chunk) {
        String[][] specialStructuresIds = {{"null", "null", "null"}, {"null", "null", "null"}, {"null", "null", "null"}};

        long seed = key.hashCode();
        Random random = new Random(seed);

        Set<String> usedPositions = new HashSet<>();

        for (int n = 0; n < specialStructuresNumber; n++) {
            int row, col;
            String positionKey;
        
            do {
                row = random.nextInt(specialStructuresIds.length);
                col = random.nextInt(specialStructuresIds[row].length);
                positionKey = row + "," + col;
            } while (usedPositions.contains(positionKey));
            
            usedPositions.add(positionKey);

            String type = specialStructuresPosibleTypes[random.nextInt(specialStructuresPosibleTypes.length)];
            
            int[] cell = {row, col};
            specialStructuresIds[row][col] = createSpecialStructure(chunk, cell, type);
        }

        return specialStructuresIds;
    }

    public String createSpecialStructure(long[] chunk, int[] cell, String type){
        try {
            specialStructuresType = specialStructuresType.getElementsTypeValues(type);

            Class<?> clazz = Class.forName(specialStructuresType.classesName);
        
            Constructor<?> constructor = clazz.getConstructor(String.class);
        
            Object instance = constructor.newInstance(gameName);
        
            Method method = clazz.getMethod("createDefaultStructure", long[].class, int[].class);
        
            String specialStructuresId = (String) method.invoke(instance, chunk, cell);

            addSpecialStructure(type, chunk, specialStructuresId);
            
            return specialStructuresId;
        } catch (Exception e) {
            //e.printStackTrace(); 
            String newId = type + "_" + save.generateRandomString(10);

            addSpecialStructure(type, chunk, newId);

            return newId;
        }
    }

    public void addSpecialStructureToPanel(int x, int y, String elementId, JPanel backgroundPanel, Chunks chunk){
        this.x = x;
        this.y = y;

        getElementsValues(elementId);

        SpecialStructures newSpecialStructure = new SpecialStructures(gameName);
        newSpecialStructure.getElementsValues(this.elementId);

        JPanel backgroundPanelWithImage = newSpecialStructure.specialStructuresType.getSpecialStructuresTypeJPanel();

        if(newSpecialStructure.specialStructuresType.isRestictedArea){
            restrictedAreas.add(new int[][] { 
                {
                    x - (newSpecialStructure.specialStructuresType.size[0]/2), 
                    y - (newSpecialStructure.specialStructuresType.size[1]/2)
                }, 
                {
                    x + (newSpecialStructure.specialStructuresType.size[0]/2), 
                    y + (newSpecialStructure.specialStructuresType.size[1]/2)
                } 
            });
        }

        if(newSpecialStructure.specialStructuresType.interactionTypes.length > 0){
            Object[] struObjects = {
                new int[]{
                    x,
                    y,
                },
                "specialStructure",
                newSpecialStructure
            };

            trigerEvents.add(struObjects);
        }

        newSpecialStructure.panel = new JPanel(null);
        newSpecialStructure.panel.setBounds(x - (newSpecialStructure.specialStructuresType.size[0] / 2), y - (newSpecialStructure.specialStructuresType.size[1] / 2), newSpecialStructure.specialStructuresType.size[0], newSpecialStructure.specialStructuresType.size[1]);
        newSpecialStructure.panel.setOpaque(false);

        if(!newSpecialStructure.specialStructuresType.classesName.isEmpty()){
            try {
                Class<?> clazz = Class.forName(newSpecialStructure.specialStructuresType.classesName);
            
                Constructor<?> constructor = clazz.getConstructor(String.class);
            
                Object instance = constructor.newInstance(gameName);
            
                Method method = clazz.getMethod("getSpecialStructureToPanel", SpecialStructuresTypes.class, int.class, int.class, String.class);
            
                JPanel specialStructuresPanel = (JPanel) method.invoke(instance, newSpecialStructure.specialStructuresType, x, y, elementId);

                newSpecialStructure.panel.add(specialStructuresPanel);
                newSpecialStructure.panel.add(backgroundPanelWithImage);

                backgroundPanel.add(newSpecialStructure.panel);

                backgroundPanel.revalidate();
                backgroundPanel.repaint();

            } catch (Exception e) {
                //e.printStackTrace(); 
                newSpecialStructure.panel.add(backgroundPanelWithImage);

                backgroundPanel.add(newSpecialStructure.panel);

                backgroundPanel.revalidate();
                backgroundPanel.repaint();
            }
        }else {
            newSpecialStructure.panel.add(backgroundPanelWithImage);

            backgroundPanel.add(newSpecialStructure.panel);

            backgroundPanel.revalidate();
            backgroundPanel.repaint();
        }
    }

    public void interact(MainGameWindow mainGameWindow, int[] position) {
        if (!mainGameWindow.selectionWheel.isOpen && !mainGameWindow.interactiveInventory.isInventoryOpen) {
            try {
                Class<?> clazz = Class.forName(specialStructuresType.classesName);
            
                Constructor<?> constructor = clazz.getConstructor(String.class);
            
                Object instance = constructor.newInstance(gameName);
            
                Method method = clazz.getMethod("getInteractionsList",MainGameWindow.class, SpecialStructuresTypes.class);

                @SuppressWarnings("unchecked")
                List<String> options =  (List<String>) method.invoke(instance, mainGameWindow, specialStructuresType);

                if(!options.isEmpty())mainGameWindow.selectionWheel.openSelectionWheel(position[0], position[1], "specialStructure", options);
            } catch (Exception e) {
    
            }
        } else if(mainGameWindow.selectionWheel.isIconSelected){
            try {
                Class<?> clazz = Class.forName(specialStructuresType.classesName);
            
                Constructor<?> constructor = clazz.getConstructor(String.class);
            
                Object instance = constructor.newInstance(gameName);
            
                Method method = clazz.getMethod("interact",MainGameWindow.class, SpecialStructures.class, String.class);

                method.invoke(instance, mainGameWindow, this, mainGameWindow.selectionWheel.iconSelectedId);
                mainGameWindow.selectionWheel.isIconSelected = false;
            } catch (Exception e) {
    
            }
        }
    }
}
