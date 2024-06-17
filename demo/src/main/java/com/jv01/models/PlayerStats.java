package com.jv01.models;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;

public class PlayerStats {
    private long money;
    private int hunger;
    private int thirst;
    private int tiredness;
    private int hygiene;
    private Save save;

    public PlayerStats(String gameName)
    {
        this.GetPlayerStats(gameName);
    }

    public void GetPlayerStats(String gameName)
    {
        Document doc = save.getDocumentXml(gameName,"player");
        Element element = save.getElementById(doc, "player", "player");

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        this.money = Integer.parseInt(save.getChildFromMapElements(allElements,"money"));
        this.hunger = Integer.parseInt(save.getChildFromMapElements(allElements,"hunger"));
        this.thirst = Integer.parseInt(save.getChildFromMapElements(allElements,"thirst"));
        this.tiredness = Integer.parseInt(save.getChildFromMapElements(allElements,"tiredness"));
        this.hygiene = Integer.parseInt(save.getChildFromMapElements(allElements,"hygiene"));
    }
}
