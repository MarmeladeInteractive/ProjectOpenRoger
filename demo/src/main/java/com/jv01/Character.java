package com.jv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.javafaker.Faker;

public class Character{
    public  Faker faker = new Faker();
    public  Save save = new Save();

    public  String gameName;

    int ageMin = 18;
    int ageMax = 100;

    int healthMin = 60;
    int energyMin = 30;
    int beliefMin = 10;
    int HygieneMin = 40;

    int moneyMin = 100;
    int moneyMax = 10000;
    
    int valueMax = 100;

    public  String name;
    public  int age;
    public  int health;
    public  int energy;
    public  long money;
    public  int belief;
    public  int hygiene;
    public  int mood;
    public  String location;

    public  int conservatismScore;
    public  int nationalismScore;
    public  int ecologismScore;
    public  int feminismScore;
    public  int anarchismScore;
    public  int populismScore;

    public  String ideologicalCode;
    public  String politicalPartyId;

    public Character(String gameName){
        this.gameName = gameName;

        name = faker.name().fullName();
        age = faker.number().numberBetween(ageMin, ageMax);
        health = faker.number().numberBetween(healthMin, valueMax) - ((age) * (faker.number().numberBetween(0, 10)))/20;
        energy = faker.number().numberBetween(energyMin, valueMax) - ((age) * (faker.number().numberBetween(0, 6)))/20;
        belief = faker.number().numberBetween(beliefMin, valueMax);
        money = (age) * (faker.number().numberBetween(1, 4)) * (belief) * (faker.number().numberBetween(1, 6));
        hygiene = (faker.number().numberBetween(HygieneMin, valueMax) * (belief)) / 100;
        mood = (energy + belief) / 2;

        Ideology ideology = new Ideology(gameName, age);

        conservatismScore = ideology.conservatismScore;
        nationalismScore = ideology.nationalismScore;
        ecologismScore = ideology.ecologismScore;
        feminismScore = ideology.feminismScore;
        populismScore = ideology.populismScore;
        anarchismScore = ideology.anarchismScore;

        ideologicalCode = ideology.ideologicalCode;

        politicalPartyId = partyChoice(ideology, ideologicalCode);
    }

    public  void createCharacterElement(Document doc){
        Element characterElement = doc.createElement("character");

        int lastId = save.findLastId(doc, "character");
        int newId = lastId + 1;

        characterElement.setAttribute("id", String.valueOf(newId));
        
        save.createXmlElement(characterElement,doc,"name",String.valueOf(name));

        save.createXmlElement(characterElement,doc,"age",String.valueOf(age));

        save.createXmlElement(characterElement,doc,"health",String.valueOf(health));

        save.createXmlElement(characterElement,doc,"energy",String.valueOf(energy));

        save.createXmlElement(characterElement,doc,"belief",String.valueOf(belief));

        save.createXmlElement(characterElement,doc,"money",String.valueOf(money));

        save.createXmlElement(characterElement,doc,"Hygiene",String.valueOf(hygiene));

        save.createXmlElement(characterElement,doc,"mood",String.valueOf(mood));

        save.createXmlElement(characterElement,doc,"conservatismScore",String.valueOf(conservatismScore));

        save.createXmlElement(characterElement,doc,"nationalismScore",String.valueOf(nationalismScore));

        save.createXmlElement(characterElement,doc,"ecologismScore",String.valueOf(ecologismScore));

        save.createXmlElement(characterElement,doc,"feminismScore",String.valueOf(feminismScore));

        save.createXmlElement(characterElement,doc,"anarchismScore",String.valueOf(anarchismScore));

        save.createXmlElement(characterElement,doc,"populismScore",String.valueOf(populismScore));

        save.createXmlElement(characterElement,doc,"ideologicalCode",String.valueOf(ideologicalCode));

        save.createXmlElement(characterElement,doc,"politicalPartyId",String.valueOf(politicalPartyId));

        save.createXmlElement(characterElement,doc,"location",String.valueOf(location));

        save.createXmlElement(characterElement,doc,"inventory",String.valueOf(null));

        doc.getDocumentElement().appendChild(characterElement);
    }

    public  void saveCharacter(){
        Document doc = save.getDocumentXml(gameName, "characters");

        createCharacterElement(doc);

        save.saveXmlFile(doc, gameName, "characters");
    }

    public  String partyChoice(Ideology ideology, String ideologicalCode){
        Document doc = save.getDocumentXml(gameName, "parties");
        NodeList partyNodes = doc.getElementsByTagName("party");
        int chosenPartyIdeologicalCompatibility=10000;
        String chosenPartyId="";

        for (int i = 0; i < partyNodes.getLength(); i++) {
            Element partyElement = (Element) partyNodes.item(i);
            String partyId = partyElement.getAttribute("id");
            
            String partyIdeologicalCode = save.getChildFromElement(partyElement,"ideologicalCode");

            int compatibility = ideology.ideologicalCompatibility(ideologicalCode,partyIdeologicalCode);

            //System.out.println((i+1)+" : "+ideologicalCode + " : "+partyIdeologicalCode + " : "+compatibility);
            if(compatibility < chosenPartyIdeologicalCompatibility){
                chosenPartyIdeologicalCompatibility = compatibility;
                chosenPartyId = partyId;
            }
        }
        
        ideology = null;
        return(chosenPartyId);
    }
}