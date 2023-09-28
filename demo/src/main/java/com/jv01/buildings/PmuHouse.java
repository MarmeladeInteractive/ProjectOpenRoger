package com.jv01.buildings;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import com.jv01.generations.Objects;
import com.jv01.generations.Tools;

public class PmuHouse{
    public String gameName;
    public int boxSize;

    public JPanel backgroundPanel;

    public List<int[][]> restrictedAreas = new ArrayList<>();
    public List<Object[]> trigerEvents = new ArrayList<>();
    public int trigerSize = 50;


    public PmuHouse(String gameName, int boxSize, JPanel backgroundPanel){
        this.gameName = gameName;
        this.boxSize = boxSize;

        this.backgroundPanel = backgroundPanel;

        addTools();
    }

    public void addTools(){

        Tools arcade = new Tools(gameName, 6, 0);
        Objects obj = new Objects(boxSize /2 , arcade.size[1] + 50, arcade.size, arcade.imageUrl, 1, backgroundPanel);
        
        restrictedAreas.add(new int[][]{ 
            {boxSize/2 - arcade.size[0]/2,50},
            {boxSize/2 + arcade.size[0]/2,arcade.size[1] +100},
        });

        Object[] item = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "tool",
            arcade,
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
