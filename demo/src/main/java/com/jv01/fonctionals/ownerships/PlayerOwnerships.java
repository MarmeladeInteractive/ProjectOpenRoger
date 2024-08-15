package com.jv01.fonctionals.ownerships;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;

public class PlayerOwnerships {
    public Save save = new Save();
    MainGameWindow mainGameWindow;
    Ownerships ownerships;

    String gameName;

    public Document doc;
    public Element element;

    public PlayerOwnerships(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.ownerships = new Ownerships(mainGameWindow);

        this.gameName = mainGameWindow.gameName;
    }

    private void createPlayerOwnershipElement(String type, long[] chunk, String elementId){
        Element ownershipElement = doc.createElement("playerOwnership");

        ownershipElement.setAttribute("id", elementId);
        
        save.createXmlElement(ownershipElement,doc,"chunk", '{'+String.valueOf(chunk[0])+','+String.valueOf(chunk[1])+'}');
        save.createXmlElement(ownershipElement,doc,"type", type);
        save.createXmlElement(ownershipElement,doc,"isDeclared", "false");

        doc.getDocumentElement().appendChild(ownershipElement);
    }

    public void addOwnership(String type, long[] chunk, String elementId){
        this.doc = save.getDocumentXml(gameName,"functional/ownerships/playerOwnerships");
        createPlayerOwnershipElement(type, chunk, elementId);
        save.saveXmlFile(doc, gameName, "functional/ownerships/playerOwnerships");
    }

    public long getDailyRevenues(){
        return 100L;
    }
}
