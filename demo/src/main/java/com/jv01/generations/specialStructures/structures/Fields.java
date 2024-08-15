package com.jv01.generations.specialStructures.structures;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.Color;

import com.jv01.fonctionals.Save;
import com.jv01.generations.specialStructures.SpecialStructuresTypes;

public class Fields {
    public Save save = new Save();
    String gameName;

    public Document doc;
    public Element element;

    String elementId;

    public Fields(String gameName){
        this.gameName = gameName;
    }

    private void createFieldElement(long[] chunk, int[] cell){
        elementId = "field_" + save.generateRandomString(10);
        Element fieldElement = doc.createElement("fields");

        fieldElement.setAttribute("id", elementId);
        
        save.createXmlElement(fieldElement,doc,"chunk", '{'+String.valueOf(chunk[0])+','+String.valueOf(chunk[1])+'}');
        save.createXmlElement(fieldElement,doc,"cell", '{'+String.valueOf(cell[0])+','+String.valueOf(cell[1])+'}');
        save.createXmlElement(fieldElement,doc,"type", "default");
        save.createXmlElement(fieldElement,doc,"elementId", elementId);

        doc.getDocumentElement().appendChild(fieldElement);
    }

    public void getFieldElement(String elementId){

    }

    public String createDefaultStructure(long[] chunk, int[] cell){
        this.doc = save.getDocumentXml(gameName,"functional/specialStructures/structures/fields");
        createFieldElement(chunk, cell);
        save.saveXmlFile(doc, gameName, "functional/specialStructures/structures/fields");
        return elementId;
    }

    public void addSpecialStructureToPanel(SpecialStructuresTypes specialStructuresType, int x, int y, String elementId, JPanel backgroundPanel){
        getFieldElement(elementId);
        
        JPanel panel = new JPanel();

        panel.setBackground(Color.yellow);

        panel.setVisible(true);
        panel.setOpaque(true);

        panel.setBounds(x - (specialStructuresType.size[0]/2), y - (specialStructuresType.size[1]/2), specialStructuresType.size[0], specialStructuresType.size[1]);

        backgroundPanel.add(panel);
    
        backgroundPanel.revalidate();
        backgroundPanel.repaint();
    }
}
