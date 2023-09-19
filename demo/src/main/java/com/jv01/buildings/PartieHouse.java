package com.jv01.buildings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.genarations.Objects;
import com.jv01.genarations.Tools;

public class PartieHouse {
    public Save save = new Save();

    public String gameName;
    public int boxSize;

    public int printerLevel = 0;
    public int ovenLevel = 0;
    public int transportationLevel = 0;

    public Document doc;
    public Element element;

    public JPanel backgroundPanel;

    public List<int[][]> restrictedAreas = new ArrayList<>();
    public List<Object[]> trigerEvents = new ArrayList<>();
    public int trigerSize = 50;


    public PartieHouse(String gameName, int boxSize, JPanel backgroundPanel){
        this.gameName = gameName;
        this.boxSize = boxSize;

        this.backgroundPanel = backgroundPanel;

        getPartieHouseValue();

        initializePartieHouse();
    }

    public void getPartieHouseValue(){
        this.doc = save.getDocumentXml(gameName,"partyHouse");
        this.element = save.getElementById(doc, "partyHouse", "partyHouse");

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        this.printerLevel = Integer.parseInt(save.getChildFromMapElements(allElements,"printerLevel"));
        this.ovenLevel = Integer.parseInt(save.getChildFromMapElements(allElements,"ovenLevel"));
        this.transportationLevel = Integer.parseInt(save.getChildFromMapElements(allElements,"transportationLevel"));
    }

    private void initializePartieHouse(){
        addTools();
    }

    public void addTools(){
        Tools printer = new Tools(gameName, 0, printerLevel);
        Objects obj = new Objects(printer.size[0]/2 + 50, boxSize-printer.size[1]/2 - 150, printer.size, printer.imageUrl, 1, backgroundPanel);
        
        restrictedAreas.add(new int[][]{ 
            {50,boxSize-150-printer.size[1]},
            {50+printer.size[0],boxSize-150} 
        });

        Object[] item = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "tool",
            printer,
            140
        };
        trigerEvents.add(item);
        
        Tools transportation = new Tools(gameName, 2, transportationLevel);
        obj = new Objects(boxSize - transportation.size[0]/2 - 50 -50 , boxSize-transportation.size[1]/2 - 50 - 50, transportation.size, transportation.imageUrl, 1, backgroundPanel);
        restrictedAreas.add(new int[][]{ 
            {boxSize-50-50-transportation.size[0],boxSize-50-50-transportation.size[1]},
            {boxSize - 50-50,boxSize-50-100} 
        });

        Object[] item2 = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "tool",
            transportation,
            140
        };
        trigerEvents.add(item2);
        
        Tools oven = new Tools(gameName, 1, ovenLevel);
        obj = new Objects(oven.size[0]/2 + 150 , oven.size[1]/2 + 150, oven.size, oven.imageUrl, 1, backgroundPanel);
        restrictedAreas.add(new int[][]{ 
            {150,150},
            {150 + oven.size[0],150 + oven.size[1]} 
        });
        
        Object[] item3 = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "tool",
            oven,
            140
        };
        trigerEvents.add(item3);

        Tools dump = new Tools(gameName, 5, 0);
        obj = new Objects(dump.size[0]/2 + boxSize/2 -150 , boxSize - 80 - dump.size[1]/2, dump.size, dump.imageUrl, 0, backgroundPanel);
        
        Object[] item4 = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "tool",
            dump,
            80
        };
        trigerEvents.add(item4);
    }
    
}
