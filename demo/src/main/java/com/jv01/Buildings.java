package com.jv01;

public class Buildings {
    public String name;
    public int type;
    public String imageUrl;
    public long[] chunk;
    public int[] cell;
    public int[] dimension;
    public String buildingKey;

    public int offsetX = 0;
    public int offsetY = 0;

    public String seed;
    public String mapKey;

    public String[][] imagesUrl = {
        {
            "demo\\img\\buildings\\partyHouse01.png",
            "demo\\img\\buildings\\emptyProperty01.png",
            "demo\\img\\buildings\\cityHall01.png",
            "demo\\img\\buildings\\printingPress01.png",
            "demo\\img\\buildings\\bakery01.png",
            "demo\\img\\buildings\\store01.png",
            "demo\\img\\buildings\\abandonedHouse01.png",
            "demo\\img\\buildings\\house01.png",
        },
        {
            "demo\\img\\buildings\\partyHouse02.png",
            "demo\\img\\buildings\\emptyProperty02.png",
            "demo\\img\\buildings\\cityHall02.png",
            "demo\\img\\buildings\\printingPress02.png",
            "demo\\img\\buildings\\bakery02.png",
            "demo\\img\\buildings\\store02.png",
            "demo\\img\\buildings\\abandonedHouse02.png",
            "demo\\img\\buildings\\house02.png",
        },
        {
            "demo\\img\\buildings\\partyHouse03.png",
            "demo\\img\\buildings\\emptyProperty03.png",
            "demo\\img\\buildings\\cityHall03.png",
            "demo\\img\\buildings\\printingPress03.png",
            "demo\\img\\buildings\\bakery03.png",
            "demo\\img\\buildings\\store03.png",
            "demo\\img\\buildings\\abandonedHouse03.png",
            "demo\\img\\buildings\\house03.png",
        },
        {
            "demo\\img\\buildings\\partyHouse04.png",
            "demo\\img\\buildings\\emptyProperty04.png",
            "demo\\img\\buildings\\cityHall04.png",
            "demo\\img\\buildings\\printingPress04.png",
            "demo\\img\\buildings\\bakery04.png",
            "demo\\img\\buildings\\store04.png",
            "demo\\img\\buildings\\abandonedHouse04.png",
            "demo\\img\\buildings\\house04.png",
        },
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
    };
    public int[][] dimensions = {
        {200,200},
        {200,200},
        {200,200},
        {200,200},
        {200,200},
        {200,200},
        {200,200},
        {200,200},
    };

    public Buildings(int type, long[] chunk, int[] cell, String buildingKey){
        this.type = type;
        this.name = names[type];
        this.buildingKey = buildingKey;
        this.imageUrl = imagesUrl[getBuildingVariety()][type];
        this.chunk = chunk;
        this.cell = cell;
        this.dimension = dimensions[type];
    }

    public Buildings(){

    }

    public int getBuildingVariety(){
        int variety = 0;
        char key01 = buildingKey.charAt(1);
        
        if(key01 >= '0' && key01 <= '3'){
            variety = 0;
        }else if(key01 >= '4' && key01 <= '7'){
            variety = 1;
        }else if(key01 >= '8' && key01 <= 'b'){
            variety = 2;
        }else{
            variety = 3;
        }
        return variety;
    }

    public void getOffset(){
        int key02 = Integer.parseInt(String.valueOf(buildingKey.charAt(2)), 16);
        int key03 = Integer.parseInt(String.valueOf(buildingKey.charAt(3)), 16);   

        offsetX = (int)mapRange(key02,0,16,-20,20);
        offsetY = (int)mapRange(key03,0,16,-20,20);
    }

    public double mapRange(double value, double minInput, double maxInput, double minOutput, double maxOutput) {
        return minOutput + ((value - minInput) / (maxInput - minInput)) * (maxOutput - minOutput);
    }


}
