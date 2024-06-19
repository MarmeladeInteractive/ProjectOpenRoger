package com.jv01.fonctionals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.generations.Arcades;
import com.jv01.generations.Dealers;
import com.jv01.generations.Items;
import com.jv01.generations.Npcs;
import com.jv01.generations.Tools;
import com.jv01.generations.Panels.InteractiveListPanel.InteractiveListPanel;
import com.jv01.generations.Panels.Menus.ChatPanel;
import com.jv01.generations.Panels.Menus.SelectionWheel;
import com.jv01.models.InteractionModel;
import com.jv01.player.Player;

public class InteractionManager {

  SelectionWheel selectionWheel;
  List<String> possibleInteractions;
  Save save;

  public InteractionManager(SelectionWheel selectWheel) {
    this.save = new Save();
    this.selectionWheel = selectWheel;
  }

  public void OpenInteractionList(InteractionModel interactionModel, int x, int y) {
    if (!selectionWheel.isOpen) {
      this.selectionWheel.openSelectionWheel(x, y, interactionModel.toStringSingular(), this.possibleInteractions);
    }
  }

  public void ProcessInteraction(Player player, boolean isTool, boolean isItem, boolean isDealer, boolean isArcade,
      boolean isNpc, boolean isBuilding,
      Tools tool, Items item, Dealers dealer, Arcades arcade, Npcs npc, String spam,
      ChatPanel chatPanel, DefaultListModel<List<String>> listModelInteractive,
      InteractiveListPanel interactiveListPanel, SelectionWheel selectionWheel) {

    InteractionModel interactionModel = null;
    int entityId = -1;
    int entityX = -1;
    int entityY = -1;

    if (isTool) {
      interactionModel = InteractionModel.TOOLS;
      entityId = tool.getId();
      entityX = player.positionX;
      entityY = player.positionY;
    } else if (isItem) {
      interactionModel = InteractionModel.ITEMS;
      entityId = item.getId();
      entityX = item.x;
      entityY = item.y;
    } else if (isDealer) {
      interactionModel = InteractionModel.MERCHANTS;
      entityId = dealer.getId();
      entityX = player.positionX;
      entityY = player.positionY;
    } else if (isArcade) {
      interactionModel = InteractionModel.ARCADES;
      entityId = arcade.getId();
      entityX = player.positionX;
      entityY = player.positionY;
    } else if (isNpc) {
      interactionModel = InteractionModel.NPCS;
      entityId = npc.getId();
      entityX = npc.x;
      entityY = npc.y;
    } else if (isBuilding) {
      interactionModel = InteractionModel.BUILDINGS;
      // entityId = building.getId();
      // entityX = player.positionX;
      // entityY = player.positionY;
    }

    if (interactionModel != null && entityId >= 0) {
      this.possibleInteractions = GetInteractionTypes(player.gameName, interactionModel, String.valueOf(entityId));
      System.out.println("interaction model : " + interactionModel.toStringSingular());
      System.out.println(" x : " + entityX);
      System.out.println(" y : " + entityY);
      System.out.println("interaction model : " + interactionModel.toStringPlural());
      System.out.println("gamneme : " + player.gameName);
      System.out.println("obj id : " + entityId);

      OpenInteractionList(interactionModel, entityX, entityY);
    }
  }

  public List<String> GetInteractionTypes(String gameName, InteractionModel interactionModel, String id) {
    List<String> interactionTypesList = new ArrayList<>();
    Document doc = save.getDocumentXml(gameName, interactionModel.getPath());
    Element element = save.getElementById(doc, interactionModel.toStringSingular(), id);

    if (element != null) {
      String interactionTypesString = save.getChildFromElement(element, "interactionTypes");
      // Supprimer les accolades et les espaces
      interactionTypesString = interactionTypesString.replace("{", "").replace("}", "").trim();
      // Diviser la chaîne en fonction des virgules
      String[] interactionTypesArray = interactionTypesString.split(",");

      // Ajouter chaque interaction type à la liste en supprimant les espaces
      for (String interactionTypeEntry : interactionTypesArray) {
        interactionTypesList.add(interactionTypeEntry.trim());
        System.out.println(interactionTypeEntry.trim());
      }
    } else {
      System.out.println("Element not found for ID: " + id);
    }

    return interactionTypesList;
  }

  public int getDistanceFromPlayer(Player player, int x, int y) {
    int distance = 0;

    distance = (int) Math
        .sqrt(((x - player.positionX) * (x - player.positionX)) + ((y - player.positionY) * (y - player.positionY)));

    return distance;
  }
}
