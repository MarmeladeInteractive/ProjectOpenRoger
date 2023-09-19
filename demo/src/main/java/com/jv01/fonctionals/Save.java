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

public class Save {

    public void createSaveFolder() {
        File savesDirectory = new File("saves");
        if (!savesDirectory.exists()) {
            savesDirectory.mkdirs();
        }
    }

    public boolean createSaveFile(String gameName, String name) {
        if (saveFileExist(gameName, name)) {
            return false;
        } else {
            String filePath = "saves/" + gameName + "/" + name + ".xml";
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.newDocument();

                Element rootElement = doc.createElement(name);
                doc.appendChild(rootElement);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(filePath));
                transformer.transform(source, result);

                //System.out.println("Fichier XML créé avec succès.");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
    }

    public void createAllFiles(String gameName) {
        createSaveFolder();

        File source = new File("demo/xml/save");
        File dest = new File("saves/" + gameName);
        try {
            FileUtils.copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document getDocumentXml(String gameName, String fileName) {
        Document doc = null;
        String filePath = "saves/" + gameName + "/" + fileName + ".xml";
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(xmlFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }

    public boolean saveXmlFile(Document doc, String gameName, String fileName) {
        try {
            String filePath = "saves/" + gameName + "/" + fileName + ".xml";
            File xmlFile = new File(filePath);

            Source xslt = new StreamSource(new File("demo/utils/transformer.xls")); 
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer(xslt);  
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public int findLastId(Document doc, String name) {
        NodeList elementNodes = doc.getElementsByTagName(name);
        if (elementNodes.getLength() > 0) {
            Element lastElement = (Element) elementNodes.item(elementNodes.getLength() - 1);
            String idValue = lastElement.getAttribute("id");
            return Integer.parseInt(idValue);
        } else {
            return 0;
        }
    }

    public boolean saveFileExist(String gameName, String name) {
        String filePath = "saves/" + gameName + "/" + name + ".xml";
        File file = new File(filePath);
        return file.exists();
    }

    public Element createXmlElement(Element element, Document doc, String childName, String value){
        Element newElement = doc.createElement(childName);
        newElement.appendChild(doc.createTextNode(value));
        element.appendChild(newElement);

        return element;
    }

    public Element getElementById(Document doc, String elementName,String id) {
        NodeList characterNodes = doc.getElementsByTagName(elementName);
        for (int i = 0; i < characterNodes.getLength(); i++) {
            Element characterElement = (Element) characterNodes.item(i);
            String characterId = characterElement.getAttribute("id");
            if (characterId.equals(id)) {
                return characterElement;
            }
        }
        return null;
    }

    public String getChildFromElement(Element element, String ChildName){
        Element ChildElement = (Element) element.getElementsByTagName(ChildName).item(0);
        String ChildElementContent = ChildElement.getTextContent();

        return(ChildElementContent);
    }
    public String getChildFromMapElements(Map<String, List<String>> map, String key){   
        String value = map.get(key).get(0);
        return value;
    }

    public Map<String, List<String>> getAllChildsFromElement(Element element){
        Map<String, List<String>> result = new HashMap<>();
        NodeList childNodes = element.getChildNodes();

        for (int i = 0; i < childNodes.getLength(); i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) childNode;
                String tagName = childElement.getTagName();
                String value = childElement.getTextContent();

                if (result.containsKey(tagName)) {
                    List<String> values = result.get(tagName);
                    values.add(value);
                } else {
                    List<String> values = new ArrayList<>();
                    values.add(value);
                    result.put(tagName, values);
                }
            }
        }
        return result;
    }

    public synchronized void changeElementChildValue(String gameName, String fileName, String elementName, String childId, String valueName, String newValue) {
        try {
            Document doc = getDocumentXml(gameName,fileName);
            Element element = getElementById(doc, elementName, childId);
    
            if (element != null) {
                Element newElement = (Element) element.getElementsByTagName(valueName).item(0);
                newElement.setTextContent(String.valueOf(newValue));
    
                if (saveXmlFile(doc, gameName, fileName)) {
                    //System.out.println("XML file saved successfully after modification.");
                } else {
                    System.out.println("Failed to save XML file after modification.");
                }
            } else {
                System.out.println("Element not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred during XML modification.");
        }
    }

    public int[] stringToIntArray(String arrayString) {
        String cleanedString = arrayString.replace("{", "").replace("}", "");
        
        String[] valueStrings = cleanedString.split(",");
        
        int[] array = new int[valueStrings.length];

        for (int i = 0; i < valueStrings.length; i++) {
            array[i] = Integer.parseInt(dropSpaceFromString(valueStrings[i]));
        }    
        return array;
    }

    public String[] stringToStringArray(String arrayString) {
        String cleanedString = arrayString.replace("{", "").replace("}", "");
        
        String[] valueStrings = cleanedString.split(",");
        
        String[] array = new String[valueStrings.length];

        for (int i = 0; i < valueStrings.length; i++) {
            array[i] = valueStrings[i];
        }    
        return array;
    }

    public String dropSpaceFromString(String link){
        return link.replaceAll("\\s", "");
    }

    public void changeChunkBuildingType(String gameName, long[] chunk, int newBuildingType){
        String chunkId = String.valueOf(chunk[0])+"_"+String.valueOf(chunk[1]);
        Document doc = getDocumentXml(gameName, "chunks");
        Element element = getElementById(doc, "chunk", chunkId);
        String typesString = getChildFromElement(element,"buildingsTypes");
        String newTypesString = typesString.substring(0, 1) + String.valueOf(newBuildingType) + typesString.substring(2);

        changeElementChildValue(gameName,"chunks","chunk",chunkId,"buildingsTypes",newTypesString);
    }
}
