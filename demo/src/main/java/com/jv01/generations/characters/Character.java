package com.jv01.generations.characters;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.github.javafaker.Faker;
import com.jv01.fonctionals.Save;
import com.jv01.generations.Ideology;

public class Character{
    public  Faker faker = new Faker();
    public  Save save = new Save();
    Identity identity;

    public  String gameName;

    public String characterId = "0";

    int ageMin = 18;
    int ageMax = 100;

    int healthMin = 60;
    int energyMin = 30;
    int beliefMin = 10;
    int HygieneMin = 40;

    int moneyMin = 100;
    int moneyMax = 10000;
    
    int valueMax = 100;

    public int size[] = {100,100};

    public  String name;
    public  String gender = "male";
    public  int age;
    public  int health;
    public  int energy;
    public  long money;
    public  int belief;
    public  int hygiene;
    public  int mood;

    public String npcPic = "demo/img/characters/npc/rogerBleu.png";

    public long[] chunk = {0,0};
    public  int[] position = {0,0};

    public  int conservatismScore;
    public  int nationalismScore;
    public  int ecologismScore;
    public  int feminismScore;
    public  int anarchismScore;
    public  int populismScore;

    public  String ideologicalCode;
    public  String politicalPartyId;

    public JPanel backgroundPanel;

    public Character(String gameName){
        this.gameName = gameName;
        this.identity = new Identity(gameName);
        createCharacterValues();
    }

    public Character(String gameName,String id,boolean saveNpc){
        this.gameName = gameName;

        this.identity = new Identity(gameName);

        this.characterId = id;

        Document doc = save.getDocumentXml(gameName, "characters");
        Element element = save.getElementById(doc, "character", id);

        if(element == null){
            createCharacterValues();
            if(saveNpc)saveCharacter();
        }else{
            name = save.getChildFromElement(element, "name");   
            npcPic = save.getChildFromElement(element, "npcPic"); 
            gender = save.getChildFromElement(element, "gender");

            size = save.stringToIntArray(save.getChildFromElement(element, "size"));   
            age = Integer.parseInt(save.getChildFromElement(element, "age"));
            health = Integer.parseInt(save.getChildFromElement(element, "health"));
            energy = Integer.parseInt(save.getChildFromElement(element, "energy"));
            belief = Integer.parseInt(save.getChildFromElement(element, "belief"));
            money = Long.parseLong(save.getChildFromElement(element, "money"));
            hygiene = Integer.parseInt(save.getChildFromElement(element, "hygiene"));
            mood = Integer.parseInt(save.getChildFromElement(element, "mood"));

            conservatismScore = Integer.parseInt(save.getChildFromElement(element, "conservatismScore"));
            nationalismScore = Integer.parseInt(save.getChildFromElement(element, "nationalismScore"));
            ecologismScore = Integer.parseInt(save.getChildFromElement(element, "ecologismScore"));
            feminismScore = Integer.parseInt(save.getChildFromElement(element, "feminismScore"));
            anarchismScore = Integer.parseInt(save.getChildFromElement(element, "anarchismScore"));
            populismScore = Integer.parseInt(save.getChildFromElement(element, "populismScore"));

            ideologicalCode = save.getChildFromElement(element, "ideologicalCode");
            politicalPartyId = save.getChildFromElement(element, "politicalPartyId");

            int chunkInt[] = save.stringToIntArray(save.getChildFromElement(element, "chunk"));
            chunk[0] = (long)chunkInt[0];
            chunk[1] = (long)chunkInt[1];

            position = save.stringToIntArray(save.getChildFromElement(element, "position"));
            
            String inventory = save.getChildFromElement(element, "inventory");
        }   
    }

    private void createCharacterValues(){
        if(faker.number().numberBetween(0, 2)==0){
            gender = "female";
        }else {
            gender = "male";
        }
        name = identity.getFullName(gender);
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

        Document doc = save.getDocumentXml(gameName, "functional/npcsPics");
        Element element;
        if(gender.equals("female")){
            element = save.getElementById(doc, "npcsPic", "female");
        }else{
            element = save.getElementById(doc, "npcsPic", "male");
        }

        String imageUrl = save.randomStringToStringArray(save.getChildFromElement(element, "imagesUrls"));
        npcPic = save.dropSpaceFromString(imageUrl);

        Path path = Paths.get(npcPic);
        if(!Files.exists(path)){
            imageUrl = save.randomStringToStringArray(save.getChildFromElement(element, "default"));
            npcPic = save.dropSpaceFromString(imageUrl);
        }

    }

    public  void createCharacterElement(Document doc){
        Element characterElement = doc.createElement("character");

        String newId;
        if(characterId.equals("0")){
            int lastId = save.findLastId(doc, "character");
            newId = String.valueOf(lastId + 1);
        }else{
            newId = characterId;
            try{
                chunk[0] = save.stringToIntArray(characterId.split("_")[0])[0];
                chunk[1] = save.stringToIntArray(characterId.split("_")[0])[1];

                position[0] = save.stringToIntArray(characterId.split("_")[1])[0];
                position[1] = save.stringToIntArray(characterId.split("_")[1])[1];
            }catch(Exception e){

            }     
        }
        
        characterElement.setAttribute("id", newId);
        
        save.createXmlElement(characterElement,doc,"name",String.valueOf(name));
        save.createXmlElement(characterElement,doc,"gender",String.valueOf(gender));
        save.createXmlElement(characterElement,doc,"npcPic",String.valueOf(npcPic));
        save.createXmlElement(characterElement,doc,"size","{"+String.valueOf(size[0])+","+String.valueOf(size[1])+"}");
        save.createXmlElement(characterElement,doc,"age",String.valueOf(age));
        save.createXmlElement(characterElement,doc,"health",String.valueOf(health));
        save.createXmlElement(characterElement,doc,"energy",String.valueOf(energy));
        save.createXmlElement(characterElement,doc,"belief",String.valueOf(belief));
        save.createXmlElement(characterElement,doc,"money",String.valueOf(money));
        save.createXmlElement(characterElement,doc,"hygiene",String.valueOf(hygiene));
        save.createXmlElement(characterElement,doc,"mood",String.valueOf(mood));
        save.createXmlElement(characterElement,doc,"conservatismScore",String.valueOf(conservatismScore));
        save.createXmlElement(characterElement,doc,"nationalismScore",String.valueOf(nationalismScore));
        save.createXmlElement(characterElement,doc,"ecologismScore",String.valueOf(ecologismScore));
        save.createXmlElement(characterElement,doc,"feminismScore",String.valueOf(feminismScore));
        save.createXmlElement(characterElement,doc,"anarchismScore",String.valueOf(anarchismScore));
        save.createXmlElement(characterElement,doc,"populismScore",String.valueOf(populismScore));
        save.createXmlElement(characterElement,doc,"ideologicalCode",String.valueOf(ideologicalCode));
        save.createXmlElement(characterElement,doc,"politicalPartyId",String.valueOf(politicalPartyId));
        save.createXmlElement(characterElement,doc,"chunk","{"+chunk[0]+","+chunk[1]+"}");
        save.createXmlElement(characterElement,doc,"position","{"+position[0]+","+position[1]+"}");
        save.createXmlElement(characterElement,doc,"inventory","{}");

        doc.getDocumentElement().appendChild(characterElement);
    }

    public void getCharacterValues(String id){

    }

    public void saveCharacter(){
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

    public void addCaracter(JPanel backgroundPanel, int boxSize){
       // Objects obj = new Objects(size + 50, boxSize-size - 150, new int[] {size,size}, "demo\\img\\achat.png", 1, backgroundPanel);
        
    }

    public void changeCharacterPartyId(String partyId){
        politicalPartyId = partyId;
        save.changeElementChildValue(gameName, "characters", "character", characterId, "politicalPartyId", politicalPartyId);
    }
}