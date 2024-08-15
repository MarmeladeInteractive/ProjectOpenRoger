package com.jv01.generations.specialStructures;

import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.awt.Color;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;

public class SpecialStructures {
    public Save save = new Save();
    public SpecialStructuresTypes specialStructuresType;

    String gameName;

    public Document doc;
    public Element element;

    public String type;
    public String elementId;

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

    public void addSpecialStructureToPanel(int x, int y, String elementId, JPanel backgroundPanel){
        getElementsValues(elementId);

        try {
            Class<?> clazz = Class.forName(specialStructuresType.classesName);
        
            Constructor<?> constructor = clazz.getConstructor(String.class);
        
            Object instance = constructor.newInstance(gameName);
        
            Method method = clazz.getMethod("addSpecialStructureToPanel", SpecialStructuresTypes.class, int.class, int.class, String.class, JPanel.class);
        
            method.invoke(instance, specialStructuresType, x, y, elementId, backgroundPanel);
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }
}
