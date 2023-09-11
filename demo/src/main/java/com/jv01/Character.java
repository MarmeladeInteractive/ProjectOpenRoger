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

        Element element = doc.createElement("name");
        element.appendChild(doc.createTextNode(name));
        characterElement.appendChild(element);

        element = doc.createElement("age");
        element.appendChild(doc.createTextNode(String.valueOf(age)));
        characterElement.appendChild(element);

        element = doc.createElement("health");
        element.appendChild(doc.createTextNode(String.valueOf(health)));
        characterElement.appendChild(element);

        element = doc.createElement("energy");
        element.appendChild(doc.createTextNode(String.valueOf(energy)));
        characterElement.appendChild(element);

        element = doc.createElement("belief");
        element.appendChild(doc.createTextNode(String.valueOf(belief)));
        characterElement.appendChild(element);

        element = doc.createElement("money");
        element.appendChild(doc.createTextNode(String.valueOf(money)));
        characterElement.appendChild(element);

        element = doc.createElement("Hygiene");
        element.appendChild(doc.createTextNode(String.valueOf(hygiene)));
        characterElement.appendChild(element);

        element = doc.createElement("mood");
        element.appendChild(doc.createTextNode(String.valueOf(mood)));
        characterElement.appendChild(element);

        element = doc.createElement("conservatismScore");
        element.appendChild(doc.createTextNode(String.valueOf(conservatismScore)));
        characterElement.appendChild(element);

        element = doc.createElement("nationalismScore");
        element.appendChild(doc.createTextNode(String.valueOf(nationalismScore)));
        characterElement.appendChild(element);

        element = doc.createElement("ecologismScore");
        element.appendChild(doc.createTextNode(String.valueOf(ecologismScore)));
        characterElement.appendChild(element);

        element = doc.createElement("feminismScore");
        element.appendChild(doc.createTextNode(String.valueOf(feminismScore)));
        characterElement.appendChild(element);

        element = doc.createElement("anarchismScore");
        element.appendChild(doc.createTextNode(String.valueOf(anarchismScore)));
        characterElement.appendChild(element);

        element = doc.createElement("populismScore");
        element.appendChild(doc.createTextNode(String.valueOf(populismScore)));
        characterElement.appendChild(element);

        element = doc.createElement("ideologicalCode");
        element.appendChild(doc.createTextNode(String.valueOf(ideologicalCode)));
        characterElement.appendChild(element);

        element = doc.createElement("politicalPartyId");
        element.appendChild(doc.createTextNode(String.valueOf(politicalPartyId)));
        characterElement.appendChild(element);

        element = doc.createElement("location");
        element.appendChild(doc.createTextNode("null"));
        characterElement.appendChild(element);

        element = doc.createElement("inventory");
        characterElement.appendChild(element);

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