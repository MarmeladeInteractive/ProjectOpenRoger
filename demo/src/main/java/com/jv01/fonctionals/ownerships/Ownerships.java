package com.jv01.fonctionals.ownerships;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;

public class Ownerships {
    public Save save = new Save();
    MainGameWindow mainGameWindow;

    String gameName;

    public Document doc;
    public Element element;

    public Ownerships(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;

        this.gameName = mainGameWindow.gameName;
    }

    public void getOwnershipTypeValues(String type){
        this.doc = save.getDocumentXml(gameName,"ownerships");

    }
}
