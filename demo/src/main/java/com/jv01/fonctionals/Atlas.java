package com.jv01.fonctionals;

import java.io.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.commons.io.FileUtils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Atlas {

    public String gameName;
    public Save save = new Save();
    public Document specialHousesAtlas;

    public Atlas(String gameString)
    {
        this.gameName = gameString;
        this.specialHousesAtlas = save.getDocumentXml(this.gameName, "specialHousesAtlas");
    }

    public void addHouseToAtlas(Document doc, String atlasName, int id, String chunkX, String chunkY, String position) {
        Element root = doc.getDocumentElement();
        Element atlasElement = null;
    
        // Recherche de l'élément d'atlas spécifique
        NodeList atlasNodes = root.getElementsByTagName(atlasName);
        if (atlasNodes.getLength() > 0) {
            atlasElement = (Element) atlasNodes.item(0);
        } else {
            // Créer un nouvel élément d'atlas s'il n'existe pas
            atlasElement = doc.createElement(atlasName);
            root.appendChild(atlasElement);
        }
    
        // Création de l'élément de maison
        Element houseElement = doc.createElement("house");
        houseElement.setAttribute("id", String.valueOf(id));
    
        // Création des éléments chunkx, chunky, et position
        houseElement.appendChild(createXmlElement(doc, "chunkx", chunkX));
        houseElement.appendChild(createXmlElement(doc, "chunky", chunkY));
        houseElement.appendChild(createXmlElement(doc, "position", position));
    
        // Ajouter l'élément de maison à l'atlas
        atlasElement.appendChild(houseElement);

        save.saveXmlFile(doc, gameName, "specialHousesAtlas");
    }
    
    private Element createXmlElement(Document doc, String elementName, String value) {
        Element element = doc.createElement(elementName);
        element.appendChild(doc.createTextNode(value));
        return element;
    }
}



//addHouseToAtlas(specialHousesAtlas, "houseAtlas", 1, "5", "10", "100,200");
//
// Sauvegarder le document XML modifié
//save.saveXmlFile(specialHousesAtlas, gameName, "specialHousesAtlas");