package com.jv01.generations.specialStructures.structures;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.Color;
import java.awt.Image;
import java.util.Random;

import com.jv01.fonctionals.Save;
import com.jv01.generations.plants.CultivablePlants;
import com.jv01.generations.specialStructures.SpecialStructuresTypes;

public class Fields {
    public Save save = new Save();
    String gameName;

    public Document doc;
    public Element element;

    String elementId;
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

    public JPanel getSpecialStructureToPanel(SpecialStructuresTypes specialStructuresType, int x, int y, String elementId) {
        getFieldElement(elementId);
    
        JPanel cultivablePlantsPanel = new JPanel(null);
        cultivablePlantsPanel.setBounds(0, 0, specialStructuresType.size[0], specialStructuresType.size[1]);
        cultivablePlantsPanel.setOpaque(false);
        
        CultivablePlants cultivablePlants = new CultivablePlants(gameName);
        cultivablePlantsPanel = cultivablePlants.addCultivablePlantToPanels(type, specialStructuresType, cultivablePlantsPanel);
    
        return cultivablePlantsPanel;
    }   
}
