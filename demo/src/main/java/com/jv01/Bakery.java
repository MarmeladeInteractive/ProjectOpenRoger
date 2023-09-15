package com.jv01;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Bakery {
    public String gameName;
    public int boxSize;

    public JPanel backgroundPanel;

    public List<int[][]> restrictedAreas = new ArrayList<>();
    public List<Object[]> trigerEvents = new ArrayList<>();
    public int trigerSize = 50;

    public Bakery(String gameName, int boxSize, JPanel backgroundPanel){
        this.gameName = gameName;
        this.boxSize = boxSize;

        this.backgroundPanel = backgroundPanel;

        addTools();
        addRestrictedAreas();
    }

    public void addTools(){
        Dealers appleStand = new Dealers(gameName, 0);
        Objects obj = new Objects(boxSize /2 -120, appleStand.size[1] + 225, appleStand.size, appleStand.imageUrl, 1, backgroundPanel);

        Object[] item = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "dealer",
            appleStand,
            50
        };
        trigerEvents.add(item);

        Dealers chocoStand = new Dealers(gameName, 1);
        obj = new Objects(boxSize /2 + 60, chocoStand.size[1] + 225, chocoStand.size, chocoStand.imageUrl, 1, backgroundPanel);

        Object[] item01 = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "dealer",
            chocoStand,
            50
        };
        trigerEvents.add(item01);

        Dealers croissantStand = new Dealers(gameName, 2);
        obj = new Objects(boxSize /2 + 155, croissantStand.size[1] + 225, croissantStand.size, croissantStand.imageUrl, 1, backgroundPanel);

        Object[] item02 = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "dealer",
            croissantStand,
            50
        };
        trigerEvents.add(item02);

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

    public void addRestrictedAreas(){
        restrictedAreas.add(new int[][]{ 
            {240, 0},
            {boxSize , 310},
        });

        restrictedAreas.add(new int[][]{ 
            {0, 380},
            {260 , boxSize},
        });

        restrictedAreas.add(new int[][]{ 
            {boxSize - 270, 380},
            {boxSize , boxSize},
        });

        restrictedAreas.add(new int[][]{ 
            {0, 0},
            {boxSize , 220},
        });
    }
    
}
