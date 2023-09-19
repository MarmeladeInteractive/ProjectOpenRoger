package com.jv01.genarations;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.javafaker.Faker;
import com.jv01.fonctionals.Save;

public class Corporation {
    public  Faker faker = new Faker();
    public  Save save = new Save();

    public  String gameName;

    public  String corporationName;
    public  long workers;
    public  long wealth;

    public  int stockValueBuy;
    public  int stockValueSell;

    public  int conservatismForce;
    public  int nationalismForce;
    public  int ecologismForce;
    public  int feminismForce;
    public  int anarchismForce;
    public  int populismForce;

    public  String catchPhrase;

    public Corporation(String gameName){
        this.gameName = gameName;

        corporationName = faker.company().name();
        workers = faker.number().numberBetween(1, 2000);

        stockValueBuy = faker.number().numberBetween(5,1000);
        stockValueSell = stockValueBuy*faker.number().numberBetween(0,2);
        if (stockValueSell==0)
        {
            stockValueSell=stockValueBuy/2;
        }
        wealth = (workers) * faker.number().numberBetween(1, 100) * (stockValueBuy/stockValueSell);

        conservatismForce = faker.number().numberBetween(-10, 10);
        nationalismForce = faker.number().numberBetween(-10, 10);
        ecologismForce = faker.number().numberBetween(-10, 10);
        feminismForce = faker.number().numberBetween(-10, 10);
        anarchismForce = faker.number().numberBetween(-10, 10);
        populismForce = faker.number().numberBetween(-10, 10);

        catchPhrase = faker.company().catchPhrase();
    }

    public void createCorporatioElement(Document doc){
        Element partyElement = doc.createElement("corporation");

        int lastId = save.findLastId(doc, "corporation");
        int newId = lastId + 1;

        partyElement.setAttribute("id", String.valueOf(newId));

        save.createXmlElement(partyElement,doc,"corporationName",String.valueOf(corporationName));
        save.createXmlElement(partyElement,doc,"workers",String.valueOf(workers));
        save.createXmlElement(partyElement,doc,"wealth",String.valueOf(wealth));
        save.createXmlElement(partyElement,doc,"stockValueBuy",String.valueOf(stockValueBuy));
        save.createXmlElement(partyElement,doc,"stockValueSell",String.valueOf(stockValueSell));
        save.createXmlElement(partyElement,doc,"conservatismScore",String.valueOf(conservatismForce));
        save.createXmlElement(partyElement,doc,"nationalismScore",String.valueOf(nationalismForce));
        save.createXmlElement(partyElement,doc,"ecologismScore",String.valueOf(ecologismForce));
        save.createXmlElement(partyElement,doc,"feminismScore",String.valueOf(feminismForce));
        save.createXmlElement(partyElement,doc,"anarchismScore",String.valueOf(anarchismForce));
        save.createXmlElement(partyElement,doc,"populismScore",String.valueOf(populismForce));
        save.createXmlElement(partyElement,doc,"catchPhrase",String.valueOf(catchPhrase));

        doc.getDocumentElement().appendChild(partyElement);
    }

    public void saveCorporation(){
        Document doc = save.getDocumentXml(gameName, "corporations");

        createCorporatioElement(doc);

        save.saveXmlFile(doc, gameName, "corporations");
    }
}