package com.jv01.generations;

public class Decorations {
    public String name;
    public int type;
    public String imageUrl;
    public long[] chunk;
    public int[] cell;
    public int[] dimension;
    public String decorationKey;

    public int offsetX;
    public int offsetY;

    public double scale;

    public int file = 0;

    public String[][][] imagesUrl = {
        {
            {
                "demo/img/decorations/arbres/sapin01.png",
                "demo\\img\\decorations\\arbres\\pommier01.png"
            },
            {
                "demo/img/decorations/arbres/sapin02.png",
                "demo\\img\\decorations\\arbres\\pommier02.png"
            },
            {
                "demo/img/decorations/arbres/sapin03.png",
                "demo\\img\\decorations\\arbres\\pommier03.png"
            },
            {
                "demo/img/decorations/arbres/sapin04.png",
                "demo\\img\\decorations\\arbres\\pommier04.png"
            },
        },
        {
            {
                "demo\\img\\decorations\\lacs\\lac01.png"
            },
            {
                "demo\\img\\decorations\\lacs\\lac02.png"
            },
            {
                "demo\\img\\decorations\\lacs\\lac03.png"
            },
            {
                "demo\\img\\decorations\\lacs\\lac04.png"
            },
        }
    };
    public String[][] names = {
        {
            "Sapin",
            "Pommier"
        },
        {
            "Lac"
        }
    };
    public int[][][] dimensions = {
        {
            {90,90},
            {90,90},
        },
        {
            {300,300}
        }
    };

    public Decorations(String fileName, int type, long[] chunk, int[] cell, String decorationKey){

        switch (fileName) {
            case "arbres":
                file = 0;
                break;
            case "lacs":
                file = 1;
                break;
            default:
                file = 0;
                break;
        }

        this.type = type;
        this.name = names[file][type];
        this.decorationKey = decorationKey;
        this.imageUrl = imagesUrl[file][getDecorationVariety()][type];   
        this.chunk = chunk;
        this.cell = cell;
        this.dimension = dimensions[file][type];  
        
        getOffSet();
        getScale();
    }

    public int getDecorationVariety(){
        int variety = 0;
        char key01 = decorationKey.charAt(1);
        
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

    public void getOffSet(){
        int key02 = Integer.parseInt(String.valueOf(decorationKey.charAt(2)), 16);
        int key03 = Integer.parseInt(String.valueOf(decorationKey.charAt(3)), 16);   

        offsetX = (int)mapRange(key02,0,16,-20,20);
        offsetY = (int)mapRange(key03,0,16,-20,20);
    }

    public void getScale(){
        int key01 = Integer.parseInt(String.valueOf(decorationKey.charAt(1)), 16);
        scale = mapRange(key01,0,16,0.8,1.1);
    }

    public double mapRange(double value, double minInput, double maxInput, double minOutput, double maxOutput) {
        return minOutput + ((value - minInput) / (maxInput - minInput)) * (maxOutput - minOutput);
    }


}
