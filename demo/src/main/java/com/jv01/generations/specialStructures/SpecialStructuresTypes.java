package com.jv01.generations.specialStructures;

import java.awt.Dimension;
import java.awt.Image;
import java.util.Random;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
    public String[] imagesPaths;

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

        this.imagesPaths = save.stringToStringPathArray(save.getChildFromElement(element, "imagesPaths"));

        return this;
    }

    public JPanel getSpecialStructuresTypeJPanel(){
        Random random = new Random();
        String imagePath = imagesPaths[random.nextInt(imagesPaths.length)];
    
        JPanel backgroundPanelWithImage = new JPanel(null);
        backgroundPanelWithImage.setBounds(0, 0, size[0], size[1]);
    
        try {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();
            Image resizedImage = img.getScaledInstance(size[0], size[1], Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);
    
            JLabel label = new JLabel(icon);
            label.setBounds(0, 0, size[0], size[1]);
            backgroundPanelWithImage.add(label);
        } catch (Exception e) {
            backgroundPanelWithImage.setBackground(Color.yellow);
        }
    
        backgroundPanelWithImage.setVisible(true);
        backgroundPanelWithImage.setOpaque(false); 

        return backgroundPanelWithImage;
    }
}
