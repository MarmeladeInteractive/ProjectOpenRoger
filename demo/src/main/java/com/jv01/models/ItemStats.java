package com.jv01.models;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;

public class ItemStats {
    private int sellPrice;
    private int buyPrice;
    private int hungerValue;
    private int thirstValue;
    private int tiringValue;
    private int cleanlynessValue;
    private InteractionModel interactionModel = InteractionModel.ITEMS;

    private Save save;

    public ItemStats(String id, String gameName) {
        this.save = new Save();
        this.GetItemStats(id, gameName);
    }

    public void GetItemStats (String id, String gameName)
    {
        Document doc = save.getDocumentXml(gameName,"functional/items");
        Element element = save.getElementById(doc, "item", String.valueOf(id));

        this.buyPrice = Integer.parseInt(save.getChildFromElement(element, "purchasePrice"));
        this.sellPrice = Integer.parseInt(save.getChildFromElement(element, "sellingPrice"));

        this.hungerValue = Integer.parseInt(save.getChildFromElement(element, "hungerValue"));
        this.thirstValue = Integer.parseInt(save.getChildFromElement(element, "thirstValue"));
        this.tiringValue = Integer.parseInt(save.getChildFromElement(element, "tiringValue"));
        this.cleanlynessValue = Integer.parseInt(save.getChildFromElement(element, "cleanlynessValue"));
        
        this.interactionModel = InteractionModel.ITEMS;
    }
}
