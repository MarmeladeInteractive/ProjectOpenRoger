package com.jv01.generations.plants;

import java.awt.GridLayout;
import java.awt.Image;
import java.util.Random;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.generations.specialStructures.SpecialStructuresTypes;

public class CultivablePlants {
    public  Save save = new Save();
    String gameName;

    public String name;
    public int sellingPrice;
    public String[] imagesPaths;

    public CultivablePlants(String gameName){
        this.gameName = gameName;
    }

    public void getCultivablePlantsValues(String id){
        Document doc = save.getDocumentXml(gameName, "functional/plants/cultivablePlants");
        Element element = save.getElementById(doc, "cultivablePlant", id);

        this.name = save.getChildFromElement(element, "name");
        this.sellingPrice = Integer.parseInt(save.getChildFromElement(element, "sellingPrice"));   

        this.imagesPaths = save.stringToStringPathArray(save.getChildFromElement(element, "imagesPaths"));
    }

    public JPanel addCultivablePlantToPanels(String idPlant, SpecialStructuresTypes specialStructuresType, JPanel panel) {
        getCultivablePlantsValues(idPlant);

        int hgap = 3;
        int vgap = 3;
        int padding = 5;

        panel.setLayout(new GridLayout(3, 3, hgap, vgap));
        panel.setBorder(new EmptyBorder(padding, padding, padding, padding)); 

        Random random = new Random();

        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        int availableWidth = (panelWidth - 2 * padding - 2 * hgap) / 3;
        int availableHeight = (panelHeight - 2 * padding - 2 * vgap) / 3;

        for (int i = 0; i < 9; i++) {
            String imagePath = imagesPaths[random.nextInt(imagesPaths.length)];
            
            JPanel imagePanel = new JPanel();
            imagePanel.setOpaque(false); 
            
            try {
                ImageIcon icon = new ImageIcon(imagePath);
                Image img = icon.getImage();

                int variation = random.nextInt(20) - 10;
                int newWidth = availableWidth + variation;
                int newHeight = availableHeight + variation;

                if (newWidth > availableWidth) {
                    newWidth = availableWidth;
                }
                if (newHeight > availableHeight) {
                    newHeight = availableHeight;
                }

                Image resizedImage = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                icon = new ImageIcon(resizedImage);

                JLabel label = new JLabel(icon);
                imagePanel.add(label);
            } catch (Exception e) {
                imagePanel.setBackground(Color.BLACK);
                imagePanel.setOpaque(true);
            }

            panel.add(imagePanel);
        }

        return panel;
    }
}
