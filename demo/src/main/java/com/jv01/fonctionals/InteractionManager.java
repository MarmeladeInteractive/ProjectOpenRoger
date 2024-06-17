package com.jv01.fonctionals;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.generations.Panels.Menus.SelectionWheel;
import com.jv01.models.InteractionModel;
import com.jv01.player.Player;

public class InteractionManager {

  SelectionWheel selectWheel;
  List<String> possibleInteractions;
  Save save;

  public InteractionManager(){
    this.save = new Save();
  }

  public void HandleInteraction(Player player, InteractionModel interactionModel, String objectID, int x, int y){
    List<String> possibleInteractions;
    possibleInteractions = GetInteractionTypes(player.gameName, interactionModel, objectID);
    System.out.println("interaction model : " + interactionModel.toStringSingular());
    System.out.println("interaction model : " + interactionModel.toStringPlural());
    System.out.println("gamneme : " + player.gameName);
    System.out.println("obj id : " + objectID);
      switch(interactionModel) {
          case NPCS:
            System.out.println("NPCS level");
            break;
          case BUILDINGS:
            System.out.println("BUILDINGS level");
            break;
          case ITEMS:
            System.out.println("ITEMS level");
            break;
          case TOOLS:
            System.out.println("TOOLS level");
            break;
          case MERCHANTS:
            System.out.println("MERCHANTS level");
            break;
          case ARCADES:
            System.out.println("ARCADES level");
            break;
          default :
            System.out.println("unrecognized interaction");
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
      }
    } else {
      System.out.println("Element not found for ID: " + id);
    }

    return interactionTypesList;
  }

  public void OpenInteractionList(InteractionModel interactionModel, int x, int y)
  {
    if (!selectWheel.isOpen) {
      selectWheel.openSelectionWheel(x, y, interactionModel.toStringSingular(), possibleInteractions);
    }
  }
}
