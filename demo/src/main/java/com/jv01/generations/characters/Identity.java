package com.jv01.generations.characters;

import java.util.Random;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;

public class Identity {
    public  Save save = new Save();
    public String gameName;

    public Identity(String gameName){
        this.gameName = gameName;
    }

    public String getFullName(String gender){
        String name = "";
        String familyName = "";
        Document doc;
        Element[] elements;

        if(gender.equals("male")){
            doc = save.getDocumentXml(gameName, "functional/identitys/maleNames");
        }else {
            doc = save.getDocumentXml(gameName, "functional/identitys/femaleNames");
        }

        elements = save.getElementsByTagName(doc, "Name");

        if (elements.length > 0) {
            Random random = new Random();
            int index = random.nextInt(elements.length);

            name = elements[index].getTextContent();
        }

        doc = save.getDocumentXml(gameName, "functional/identitys/familyNames");
        elements = save.getElementsByTagName(doc, "Name");

        if (elements.length > 0) {
            Random random = new Random();
            int familyIndex = random.nextInt(elements.length);
            familyName = elements[familyIndex].getTextContent();
        }

        return name + ' ' + familyName;
    }
}
