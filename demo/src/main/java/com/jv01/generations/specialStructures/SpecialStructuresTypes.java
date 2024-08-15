package com.jv01.generations.specialStructures;

import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;

public class SpecialStructuresTypes {
    public Save save = new Save();

    String gameName;

    public Document doc;
    public Element element;

    public int[] size;
    public String type;
    public String elementType;
    public String name;
    public String description;
    public String classesName;
    public int[] sise;

    public SpecialStructuresTypes(String gameName){
        this.gameName = gameName;
    }

    public SpecialStructuresTypes getElementsTypeValues(String elementType){
        Document doc = save.getDocumentXml(gameName,"functional/specialStructures/specialStructuresTypes");
        Element element = save.getElementById(doc, "specialStructuresType", String.valueOf(elementType));

        this.type = save.getChildFromElement(element, "type");
        this.name = save.getChildFromElement(element, "name");
        this.description = save.getChildFromElement(element, "description");
        this.classesName = save.getChildFromElement(element, "classesName");
        this.size = save.stringToIntArray(save.getChildFromElement(element, "size"));
        this.elementType = elementType;

        return this;
    }
}
