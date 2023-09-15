package com.jv01;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class EmptyHouse{
    public String gameName;
    public int boxSize;

    public JPanel backgroundPanel;

    public List<int[][]> restrictedAreas = new ArrayList<>();
    public List<Object[]> trigerEvents = new ArrayList<>();
    public int trigerSize = 50;


    public EmptyHouse(String gameName, int boxSize, JPanel backgroundPanel){
        this.gameName = gameName;
        this.boxSize = boxSize;

        this.backgroundPanel = backgroundPanel;

        addTools();
    }

    public void addTools(){
        Tools house = new Tools(gameName, 4, 0);
        Objects obj = new Objects(boxSize /2 , house.size[1] + 50, house.size, house.imageUrl, 1, backgroundPanel);
        
        restrictedAreas.add(new int[][]{ 
            {boxSize/2 - house.size[0]/2,50},
            {boxSize/2 + house.size[0]/2,house.size[1] +100},
        });

        Object[] item = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "tool",
            house,
            140
        };
        trigerEvents.add(item);

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
