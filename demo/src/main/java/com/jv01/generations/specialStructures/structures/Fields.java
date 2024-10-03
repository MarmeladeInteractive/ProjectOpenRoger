package com.jv01.generations.specialStructures.structures;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.Color;
import java.awt.Image;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;
import com.jv01.generations.plants.CultivablePlants;
import com.jv01.generations.specialStructures.SpecialStructures;
import com.jv01.generations.specialStructures.SpecialStructuresTypes;

public class Fields {
    public Save save = new Save();
    String gameName;

    public Document doc;
    public Element element;

    public String elementId;

    String type;

    public Fields(String gameName){
        this.gameName = gameName;
    }

    private void createFieldElement(long[] chunk, int[] cell){
        elementId = "field_" + save.generateRandomString(10);
        Element fieldElement = doc.createElement("field");

        fieldElement.setAttribute("id", elementId);
        
        save.createXmlElement(fieldElement,doc,"chunk", '{'+String.valueOf(chunk[0])+','+String.valueOf(chunk[1])+'}');
        save.createXmlElement(fieldElement,doc,"cell", '{'+String.valueOf(cell[0])+','+String.valueOf(cell[1])+'}');
        save.createXmlElement(fieldElement,doc,"type", "wheat");
        save.createXmlElement(fieldElement,doc,"elementId", elementId);

        doc.getDocumentElement().appendChild(fieldElement);
    }

    public void getFieldElement(String elementId){
        doc = save.getDocumentXml(gameName, "functional/specialStructures/structures/fields");
        element = save.getElementById(doc, "field", elementId);

        this.type = save.getChildFromElement(element, "type");         
    }

    public String createDefaultStructure(long[] chunk, int[] cell){
        this.doc = save.getDocumentXml(gameName,"functional/specialStructures/structures/fields");
        createFieldElement(chunk, cell);
        save.saveXmlFile(doc, gameName, "functional/specialStructures/structures/fields");
        return elementId;
    }

    public void changeType(String elementId, String newType){
        System.out.println(newType);
        save.changeElementChildValue(gameName, "functional/specialStructures/structures/fields", "field", elementId, "type", newType);
    }

    public JPanel getSpecialStructureToPanel(SpecialStructuresTypes specialStructuresType, int x, int y, String elementId) {
        getFieldElement(elementId);
    
        JPanel cultivablePlantsPanel = new JPanel(null);
        cultivablePlantsPanel.setBounds(0, 0, specialStructuresType.size[0], specialStructuresType.size[1]);
        cultivablePlantsPanel.setOpaque(false);
        
        CultivablePlants cultivablePlants = new CultivablePlants(gameName);
        cultivablePlantsPanel = cultivablePlants.addCultivablePlantToPanels(type, specialStructuresType, cultivablePlantsPanel);
    
        return cultivablePlantsPanel;
    } 
    
    public List<String> getInteractionsList(MainGameWindow mainGameWindow, SpecialStructuresTypes specialStructuresType){
        for (int bt : mainGameWindow.chunk.bType) {
            if(bt == 11){
                return Arrays.asList(specialStructuresType.interactionTypes);
            }
        }

        return null;      
    }

    public void interact(MainGameWindow mainGameWindow, SpecialStructures specialStructure, String interactionType) {
        getFieldElement(specialStructure.elementId);
        String elemenType = interactionType.split("_")[1].toLowerCase();
        if(!elemenType.equals(type)){
            changeType(specialStructure.elementId, elemenType);
            mainGameWindow.refresh();
        }
    }
}
