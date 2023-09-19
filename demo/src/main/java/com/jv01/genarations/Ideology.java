package com.jv01.genarations;

import com.github.javafaker.Faker;

public class Ideology {
    public  Faker faker = new Faker();

    public  String gameName;

    public  int conservatismScore;
    public  int nationalismScore;
    public  int ecologismScore;
    public  int feminismScore;
    public  int anarchismScore;
    public  int populismScore;

    public  String ideologicalCode;
    public  int leftRightDifference;

    public Ideology (String gameName, int age) {
        //gameName = gameName;

        conservatismScore = faker.number().numberBetween(0, 100);
        nationalismScore = generateIdeologicalValue(conservatismScore);
        ecologismScore = generateIdeologicalValue(100 - ((conservatismScore * age) / 100));
        feminismScore = faker.number().numberBetween(0, 100);
        populismScore = generateIdeologicalValue(100 - ((conservatismScore * (100 - age)) / 100));
        anarchismScore = generateIdeologicalValue(((populismScore * ecologismScore) / 100));

        ideologicalCode = getIdeologicalCode();

        leftRightDifference = calculateLeftRightDifference(ideologicalCode);
    }

    public int generateIdeologicalValue(int value){
        int newVal = 0;
        if(value >= 50){
            newVal = ((value)  + (faker.number().numberBetween(80, 135))-100);
        }else{
            newVal = ((value)  + (faker.number().numberBetween(65, 120))-100);
        }
        if(newVal > 100)newVal = 100 - faker.number().numberBetween(0, 20);
        if(newVal < 0)newVal = 0 + faker.number().numberBetween(0, 20);
        return(newVal);
    }

    public String getIdeologicalCode(){
        String code = "";
        code += getIdeologicalStrength(anarchismScore);
        code += "-";
        code += getIdeologicalStrength(populismScore);
        code += "-";
        code += getIdeologicalStrength(ecologismScore);
        code += "-";
        code += getIdeologicalStrength(feminismScore);
        code += "-";
        code += getIdeologicalStrength(nationalismScore);
        code += "-";
        code += getIdeologicalStrength(conservatismScore);

        return(code);
    }

    public String getIdeologicalStrength(int val){
        if(val>75){
            return("4");
        }else if(val>50){
            return("3");
        }else if(val>25){
            return("2");
        }else{
            return("1");
        }
    }

    public int ideologicalCompatibility(String ideologicalCode1, String ideologicalCode2) {

        String[] str1Array = ideologicalCode1.split("-");
        String[] str2Array = ideologicalCode2.split("-");
        
        if (str1Array.length != str2Array.length) {
            throw new IllegalArgumentException("Les chaînes doivent avoir la même longueur.");
        }
        
        int sum = 0;
        
        for (int i = 0; i < str1Array.length; i++) {
            int num1 = Integer.parseInt(str1Array[i]);
            int num2 = Integer.parseInt(str2Array[i]);
            int difference = Math.abs(num1 - num2); 
            sum += difference;
        }
        
        return sum;
    }

    public int calculateLeftRightDifference(String ideologicalCode) {
        String[] str1Array = ideologicalCode.split("-");
        int LeftRightDifference = 0;

        for(int i = 0;i < (str1Array.length / 2); i++){
            LeftRightDifference = LeftRightDifference + (Integer.parseInt(str1Array[i]) - Integer.parseInt(str1Array[str1Array.length - i - 1]));
        }

        return (LeftRightDifference);
    }
}
