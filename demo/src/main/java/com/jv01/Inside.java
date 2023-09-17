package com.jv01;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Inside {

    public String gameName;
    public String name;
    public int type;
    public String imageUrl;

    public int boxSize;

    public JPanel backgroundPanel;

    public List<int[][]> restrictedAreas = new ArrayList<>();
    public List<Object[]> trigerEvents = new ArrayList<>();


    public String[] imagesUrl = 
        {
            "demo\\img\\insides\\partyHouse01.jpg",
            "demo\\img\\insides\\emptyProperty01.jpg",
            "demo\\img\\insides\\cityHall01.jpg",
            "demo\\img\\insides\\printingPress01.jpg",
            "demo\\img\\insides\\bakery01.jpg",
            "demo\\img\\insides\\store01.jpg",
            "demo\\img\\insides\\abandonedHouse01.jpg",
            "demo\\img\\insides\\house01.jpg",
            "demo\\img\\insides\\corpoHouse01.jpg",
        };


    public String[] names = {
        "Maison du parti",
        "Maison vide",
        "Mairie",
        "Imprimerie",
        "Boulangerie",
        "Magasin",
        "Maison abandonnee",
        "Maison",
        "Siege corporation"
    };

    public Inside(int type, int boxSize, String gameName, JPanel backgroundPanel){
        this.gameName = gameName;
        this.boxSize =  boxSize;

        this.type = type;
        this.name = names[type];
        this.imageUrl = imagesUrl[type];
        this.backgroundPanel = backgroundPanel;

        addRestrictedBorders();

        switch (type) {
            case 0:
                createPartieHouseInside();
                break;
            case 1:
                createEmptyHouseInside();
                break;

            case 4:
                createBakeryInside();
                break;

            case 6:
                createAbandonedHouseInside();
                break;

            case 8:
                createCorpoHouseInside();
                break;
        
            default:
                break;
        }
    }

    private void addRestrictedBorders(){
        this.restrictedAreas.add(new int[][]{ 
            {0,0},
            {120,boxSize} 
        });
        this.restrictedAreas.add(new int[][]{ 
            {0,0},
            {boxSize,190} 
        });
        this.restrictedAreas.add(new int[][]{ 
            {boxSize-120,0},
            {boxSize-120,boxSize} 
        });
        this.restrictedAreas.add(new int[][]{ 
            {0,boxSize-100},
            {(boxSize/2) - 50,boxSize} 
        });
        this.restrictedAreas.add(new int[][]{ 
            {(boxSize/2) + 80,boxSize-100},
            {boxSize-50,boxSize}  
        });
    }

    private void createPartieHouseInside(){
        PartieHouse partieHouse = new PartieHouse(gameName,boxSize, backgroundPanel);
        addRestrictedAreas(partieHouse.restrictedAreas);
        addTrigerEventsAreas(partieHouse.trigerEvents);
    }

    private void createAbandonedHouseInside(){
        AbandonedHouse abandonedHouse = new AbandonedHouse(gameName,boxSize, backgroundPanel);
        addRestrictedAreas(abandonedHouse.restrictedAreas);
        addTrigerEventsAreas(abandonedHouse.trigerEvents);
    }

    private void createBakeryInside(){
        Bakery bakery = new Bakery(gameName,boxSize, backgroundPanel);
        addRestrictedAreas(bakery.restrictedAreas);
        addTrigerEventsAreas(bakery.trigerEvents);
    }

    private void createEmptyHouseInside(){
        EmptyHouse emptyHouse = new EmptyHouse(gameName,boxSize, backgroundPanel);
        addRestrictedAreas(emptyHouse.restrictedAreas);
        addTrigerEventsAreas(emptyHouse.trigerEvents);
    }

    private void createCorpoHouseInside(){
        CorporationHouse corpoHouse = new CorporationHouse(gameName,boxSize, backgroundPanel);
        addRestrictedAreas(corpoHouse.restrictedAreas);
        addTrigerEventsAreas(corpoHouse.trigerEvents);
    }


    private void addRestrictedAreas(List<int[][]> buildingRestrictedAreas){
        for (int[][] rest : buildingRestrictedAreas) {
            restrictedAreas.add(rest);
        }
    }
    private void addTrigerEventsAreas(List<Object[]> buildingTrigerEventsAreas){
        for (Object[] trigEvent : buildingTrigerEventsAreas) {
            trigerEvents.add(trigEvent);
        }
    }
    

}
