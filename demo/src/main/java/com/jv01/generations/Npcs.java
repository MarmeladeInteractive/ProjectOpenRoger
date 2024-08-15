package com.jv01.generations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;
import com.jv01.generations.characters.Character;

public class Npcs {
    public Save save = new Save();
    public String gameName;
    private Random random = new Random();

    public Ideology ideology = new Ideology(gameName, 0);

    public String name;
    public String id = "0";
    public String imageUrl;
    public int[] size;

    public Character character;

    public Document doc;
    public Element element;

    public JLabel npcLabel;
    public JPanel backgroundPanel;

    public boolean isExist = false;
    public int[][] restrictedAreas;

    public int offsetX;
    public int offsetY;

    public int x;
    public int y;

    public String currentResponce = "";
    public int interactScore = 0;
    public boolean sayHello = false;

    public Npcs(String gameName){
        this.gameName = gameName;
    }

    public void createCharacter(String id){
        this.character = new Character(gameName,id,true);

        this.name = this.character.name;
        this.id = id;
        this.imageUrl = this.character.npcPic;
        this.size = this.character.size;

        getOffset();
    }

    public Object[] addNpc(int newX, int newY, JPanel backgroundPanel){
        x = newX + offsetX;
        y = newY + offsetY;
        Objects obj = new Objects(x, y, size, imageUrl, 0, backgroundPanel);  
        //restrictedAreas = obj.restrictedAreas;
        
        Object[] npc01 = {
            new int[]{
                obj.position[0],
                obj.position[1],
            },
            "npc",
            this
        };

        this.npcLabel = obj.objectLabel;
        this.backgroundPanel = backgroundPanel;
        this.isExist = true;

        return npc01;
    }

    public void interact(MainGameWindow mainGameWindow) {
        List<String> options = Arrays.asList("interactHello", "interactGiveBread", "interactJoinUs");

        Document docNPC = save.getDocumentXml(gameName, "functional/responses");
        Document docRoger = save.getDocumentXml(gameName, "functional/questions");

        Element responseElement = save.getElementById(docNPC, "response", "greet");
        Element questionElement = save.getElementById(docRoger, "question", "join");

        Map<String, List<String>> npcResponses = save.getAllChildsFromElement(responseElement);
        Map<String, List<String>> rogerQuestions = save.getAllChildsFromElement(questionElement);

        if (!mainGameWindow.selectionWheel.isOpen && !mainGameWindow.interactiveInventory.isInventoryOpen) {
            mainGameWindow.selectionWheel.openSelectionWheel(x, y, "npc", options);
        }

        try {
            if (mainGameWindow.player.inputsManager.interactKeyPressed || 
                (mainGameWindow.selectionWheel.isIconSelected && "npc".equals(mainGameWindow.selectionWheel.interactType))) {

                int compatibility = ideology.ideologicalCompatibility(character.ideologicalCode, mainGameWindow.player.ideologicalCode);
                int randomNumber = random.nextInt(30) + 1;
                String response = "null";

                switch (mainGameWindow.selectionWheel.iconSelectedId) {
                    case "interactHello":
                        response = handleHelloInteraction(mainGameWindow, docRoger, compatibility, randomNumber);
                        break;
                    case "interactGiveBread":
                        response = handleGiveBreadInteraction(mainGameWindow, docRoger, compatibility, randomNumber);
                        break;
                    case "interactJoinUs":
                        response = handleJoinUsInteraction(mainGameWindow, docNPC, docRoger, compatibility, randomNumber);
                        break;
                    default:
                        break;
                }

                if ("accepted".equals(response)) {
                    joinParti();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // Better error handling
        }
    }

    private String handleHelloInteraction(MainGameWindow mainGameWindow, Document docRoger, int compatibility, int randomNumber) {
        if (!"interactHello".equals(currentResponce)) {
            currentResponce = "interactHello";
            sayHello = true;
            Element questionElement = save.getElementById(docRoger, "question", "hello");
            Map<String, List<String>> rogerQuestions = save.getAllChildsFromElement(questionElement);

            if ((compatibility + interactScore * 5) > 40) {
                return openChat(mainGameWindow, "greet", rogerQuestions, randomNumber);
            } else {
                return openChat(mainGameWindow, "reject", rogerQuestions, randomNumber);
            }
        }
        return "null";
    }

    private String handleGiveBreadInteraction(MainGameWindow mainGameWindow, Document docRoger, int compatibility, int randomNumber) {
        if (!"interactGiveBread".equals(currentResponce)) {
            currentResponce = "interactGiveBread";
            if (!mainGameWindow.interactiveInventory.isInventoryOpen) {
                mainGameWindow.interactiveInventory.open(mainGameWindow.player);
            }
        } else if ("interactGiveBread".equals(currentResponce)) {
            if (!mainGameWindow.interactiveInventory.clickedItemName.isEmpty()) {
                if (mainGameWindow.player.inventory.incrementValue(mainGameWindow.interactiveInventory.clickedItemName, -1)) {
                    Element questionElement = save.getElementById(docRoger, "question", "gift");
                    Map<String, List<String>> rogerQuestions = save.getAllChildsFromElement(questionElement);

                    mainGameWindow.player.save();
                    mainGameWindow.player.inventory.saveAll();
                    mainGameWindow.interactiveInventory.clearInventoryPanel();

                    if ((compatibility + interactScore * 5) > 30) {
                        return openChat(mainGameWindow, "acceptedGift", rogerQuestions, randomNumber);
                    } else {
                        mainGameWindow.player.inventory.incrementValue(mainGameWindow.interactiveInventory.clickedItemName, 1);
                        return openChat(mainGameWindow, "deniedGift", rogerQuestions, randomNumber);
                    }
                }
            }
        }
        return "null";
    }

    private String handleJoinUsInteraction(MainGameWindow mainGameWindow, Document docNPC, Document docRoger, int compatibility, int randomNumber) {
        if (!"interactJoinUs".equals(currentResponce)) {
            currentResponce = "interactJoinUs";
            Element questionElement = save.getElementById(docRoger, "question", "join");
            Map<String, List<String>> rogerQuestions = save.getAllChildsFromElement(questionElement);

            if (!sayHello && compatibility <= 70) {
                return openChat(mainGameWindow, "dontSayHello", rogerQuestions, randomNumber);
            }

            if (interactScore <= -2 || interactScore >= 5) {
                return openChat(mainGameWindow, "angry", rogerQuestions, randomNumber);
            }

            if ((compatibility + interactScore * 5) > 80) {
                return openChat(mainGameWindow, random.nextInt(6) == 1 ? "undecided" : "accepted", rogerQuestions, randomNumber);
            } else if ((compatibility + interactScore * 5) > 60) {
                return openChat(mainGameWindow, random.nextInt(4) == 1 ? "accepted" : "undecided", rogerQuestions, randomNumber);
            } else if ((compatibility + interactScore * 5) > 30) {
                return openChat(mainGameWindow, "denied", rogerQuestions, randomNumber);
            } else {
                return openChat(mainGameWindow, "angry", rogerQuestions, randomNumber);
            }
        }
        return "null";
    }

    private String openChat(MainGameWindow mainGameWindow, String responseType, Map<String, List<String>> questions, int randomNumber) {
        doc = save.getDocumentXml(gameName, "functional/responses");
        Element responseElement = save.getElementById(doc, "response", responseType);
        Map<String, List<String>> npcResponses = save.getAllChildsFromElement(responseElement);
        mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(questions, "res" + randomNumber), name, save.getChildFromMapElements(npcResponses, "res" + randomNumber));
        //currentResponce = responseType;
        return responseType;
    }


    public void getOffset(){
        this.offsetX = random.nextInt(20 + 20) - 20;
        this.offsetY = random.nextInt(20 + 20) - 20;
    }

    public void joinParti(){

    }
    
}
