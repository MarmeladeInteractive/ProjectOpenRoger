package com.jv01.generations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.javafaker.Faker;
import com.jv01.fonctionals.Save;

public class Party {
    public  Faker faker = new Faker();
    public  Save save = new Save();

    public  String gameName;

    public  String partyName;
    public  long members;
    public  long wealth;
    public  int membersTargetedAge;
    public  int membersTargetedWealth;

    public  int conservatismScore;
    public  int nationalismScore;
    public  int ecologismScore;
    public  int feminismScore;
    public  int anarchismScore;
    public  int populismScore;

    public  String ideologicalCode;
    public  int leftRightDifference;

    int ageMax = 100;
    int ageMin = 20;

    long membersMax = 120000;
    long membersMin = 12000;

    public Party(String gameName){
        this.gameName = gameName;

        partyName = faker.gameOfThrones().house();
        members = faker.number().numberBetween(membersMin, membersMax);
        membersTargetedAge = faker.number().numberBetween(ageMin, ageMax);
        membersTargetedWealth = faker.number().numberBetween(10, 100);
        wealth = members * faker.number().numberBetween(10, 100) * (membersTargetedWealth / 10);

        Ideology ideology = new Ideology(gameName, membersTargetedAge);

        conservatismScore = ideology.conservatismScore;
        nationalismScore = ideology.nationalismScore;
        ecologismScore = ideology.ecologismScore;
        feminismScore = ideology.feminismScore;
        populismScore = ideology.populismScore;
        anarchismScore = ideology.anarchismScore;

        ideologicalCode = ideology.ideologicalCode;

        leftRightDifference = Party.calculateLeftRightDifference(ideologicalCode);
        ideology = null;
    }


    public void createpartyElement(Document doc){
        Element partyElement = doc.createElement("party");

        int lastId = save.findLastId(doc, "party");
        int newId = lastId + 1;

        partyElement.setAttribute("id", String.valueOf(newId));

        save.createXmlElement(partyElement,doc,"partyName",String.valueOf(partyName));

        save.createXmlElement(partyElement,doc,"members",String.valueOf(members));

        save.createXmlElement(partyElement,doc,"membersTargetedAge",String.valueOf(membersTargetedAge));

        save.createXmlElement(partyElement,doc,"membersTargetedWealth",String.valueOf(membersTargetedWealth));

        save.createXmlElement(partyElement,doc,"wealth",String.valueOf(wealth));

        save.createXmlElement(partyElement,doc,"conservatismScore",String.valueOf(conservatismScore));

        save.createXmlElement(partyElement,doc,"nationalismScore",String.valueOf(nationalismScore));

        save.createXmlElement(partyElement,doc,"ecologismScore",String.valueOf(ecologismScore));

        save.createXmlElement(partyElement,doc,"feminismScore",String.valueOf(feminismScore));

        save.createXmlElement(partyElement,doc,"anarchismScore",String.valueOf(anarchismScore));

        save.createXmlElement(partyElement,doc,"populismScore",String.valueOf(populismScore));

        save.createXmlElement(partyElement,doc,"ideologicalCode",String.valueOf(ideologicalCode));

        doc.getDocumentElement().appendChild(partyElement);
    }

    public void saveParty(){
        Document doc = save.getDocumentXml(gameName, "parties");

        createpartyElement(doc);

        save.saveXmlFile(doc, gameName, "parties");
    }

    public static int calculateLeftRightDifference(String ideologicalCode) {
        String[] str1Array = ideologicalCode.split("-");
        int LeftRightDifference = 0;

        for(int i = 0;i < (str1Array.length / 2); i++){
            LeftRightDifference = LeftRightDifference + (Integer.parseInt(str1Array[i]) - Integer.parseInt(str1Array[str1Array.length - i - 1]));
        }

        return (LeftRightDifference);
    }
}
