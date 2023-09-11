package com.jv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.javafaker.Faker;

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

        Element element = doc.createElement("partyName");
        element.appendChild(doc.createTextNode(partyName));
        partyElement.appendChild(element);

        element = doc.createElement("members");
        element.appendChild(doc.createTextNode(String.valueOf(members)));
        partyElement.appendChild(element);

        element = doc.createElement("membersTargetedAge");
        element.appendChild(doc.createTextNode(String.valueOf(membersTargetedAge)));
        partyElement.appendChild(element);

        element = doc.createElement("membersTargetedWealth");
        element.appendChild(doc.createTextNode(String.valueOf(membersTargetedWealth)));
        partyElement.appendChild(element);

        element = doc.createElement("wealth");
        element.appendChild(doc.createTextNode(String.valueOf(wealth)));
        partyElement.appendChild(element);

        element = doc.createElement("conservatismScore");
        element.appendChild(doc.createTextNode(String.valueOf(conservatismScore)));
        partyElement.appendChild(element);

        element = doc.createElement("nationalismScore");
        element.appendChild(doc.createTextNode(String.valueOf(nationalismScore)));
        partyElement.appendChild(element);

        element = doc.createElement("ecologismScore");
        element.appendChild(doc.createTextNode(String.valueOf(ecologismScore)));
        partyElement.appendChild(element);

        element = doc.createElement("feminismScore");
        element.appendChild(doc.createTextNode(String.valueOf(feminismScore)));
        partyElement.appendChild(element);

        element = doc.createElement("anarchismScore");
        element.appendChild(doc.createTextNode(String.valueOf(anarchismScore)));
        partyElement.appendChild(element);

        element = doc.createElement("populismScore");
        element.appendChild(doc.createTextNode(String.valueOf(populismScore)));
        partyElement.appendChild(element);

        element = doc.createElement("ideologicalCode");
        element.appendChild(doc.createTextNode(String.valueOf(ideologicalCode)));
        partyElement.appendChild(element);

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
