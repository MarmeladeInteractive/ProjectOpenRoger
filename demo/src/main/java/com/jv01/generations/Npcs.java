package com.jv01.generations;

import java.util.ArrayList;
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

    public void interact(MainGameWindow mainGameWindow){
        List<String> test = new ArrayList<>();
        test.add("interactHello");
        test.add("interactGiveBread");
        test.add("interactJoinUs");

        Document doc = save.getDocumentXml(gameName,"functional/responces");
        Element element = save.getElementById(doc, "response", "greet");

        Map<String, List<String>> allElementsNPCs = save.getAllChildsFromElement(element);

        Document docRoger = save.getDocumentXml(gameName,"functional/questions");
        Element elementRoger = save.getElementById(docRoger, "question", "join");

        Map<String, List<String>> allElementsRoger = save.getAllChildsFromElement(elementRoger);

        if(!mainGameWindow.selectionWheel.isOpen && !mainGameWindow.interactiveInventory.isInventoryOpen)mainGameWindow.selectionWheel.openSelectionWheel(x, y,"npc", test);
        try {
            if(mainGameWindow.player.inputsManager.interactKeyPressed || (mainGameWindow.selectionWheel.isIconSelected && mainGameWindow.selectionWheel.interactType == "npc")){

                int compatibility = ideology.ideologicalCompatibility(character.ideologicalCode,mainGameWindow.player.ideologicalCode);
                
                int randomNumber = random.nextInt(30) + 1;
                String response = "null";

                switch (mainGameWindow.selectionWheel.iconSelectedId) {
                    case "interactHello":
                        if(!currentResponce.equals("interactHello")){
                            elementRoger = save.getElementById(docRoger, "question", "hello");
                            allElementsRoger = save.getAllChildsFromElement(elementRoger);

                            if((compatibility+interactScore*5) > 40){
                                response = "greet";
                                mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));
                            }else{
                                element = save.getElementById(doc, "response", "reject");
                                allElementsNPCs = save.getAllChildsFromElement(element);

                                response = "reject";
                                mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));
                            }
                            currentResponce = "interactHello";
                            sayHello = true;
                        }
                        break;

                        case "interactGiveBread":
                        if(!currentResponce.equals("interactGiveBread")){
                            currentResponce = "interactGiveBread";
                            if(!mainGameWindow.interactiveInventory.isInventoryOpen)mainGameWindow.interactiveInventory.open(mainGameWindow.player);
                        }else if(currentResponce.equals("interactGiveBread")){
                            if(mainGameWindow.interactiveInventory.clickedItemName != ""){
                                if(mainGameWindow.player.inventory.incrementValue(mainGameWindow.interactiveInventory.clickedItemName, -1)){
                                    elementRoger = save.getElementById(docRoger, "question", "gift");
                                    allElementsRoger = save.getAllChildsFromElement(elementRoger);
        
                                    if((compatibility + interactScore*5) > 30){
                                        element = save.getElementById(doc, "response", "acceptedGift");
                                        allElementsNPCs = save.getAllChildsFromElement(element);
        
                                        response = "acceptedGift";
                                        mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));
                                        interactScore ++;
                                    }else{
                                        element = save.getElementById(doc, "response", "deniedGift");
                                        allElementsNPCs = save.getAllChildsFromElement(element);
        
                                        response = "deniedGift";
                                        mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));
                                        mainGameWindow.player.inventory.incrementValue(mainGameWindow.interactiveInventory.clickedItemName, 1);
                                    }
                                    mainGameWindow.player.save();
                                    mainGameWindow.player.inventory.saveAll();
                                    mainGameWindow.interactiveInventory.clearInventoryPanel();
                                }
                            }
                        }
                        break;

                        case "interactJoinUs":

                        if(!currentResponce.equals("interactJoinUs")){
                            elementRoger = save.getElementById(docRoger, "question", "join");
                            allElementsRoger = save.getAllChildsFromElement(elementRoger);

                            if(!sayHello){
                                if((compatibility) > 70){
                
                                }else{
                                    element = save.getElementById(doc, "response", "dontSayHello");
                                    allElementsNPCs = save.getAllChildsFromElement(element);
                                    response = "dontSayHello";
                                    mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));
                                    interactScore --;
                                    currentResponce = "interactJoinUs";
                                    break;
                                }
                            }
                            if((interactScore <= -2) || (interactScore >= 5)){
                                element = save.getElementById(doc, "response", "angry");
                                allElementsNPCs = save.getAllChildsFromElement(element);
    
                                response = "angry";
                                mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));
                                interactScore --;
                                currentResponce = "interactJoinUs";
                                break;
                            }
                            if((compatibility + interactScore*5) > 80){
                                if(random.nextInt(6) == 1){
                                    element = save.getElementById(doc, "response", "undecided");
                                    allElementsNPCs = save.getAllChildsFromElement(element);
                                    response = "undecided";
                                }else{
                                    element = save.getElementById(doc, "response", "accepted");
                                    allElementsNPCs = save.getAllChildsFromElement(element);
                                    response = "accepted";
                                    interactScore ++;
                                }

                                mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));   
                            }else if((compatibility + interactScore*5) > 60){
                                if(random.nextInt(4) == 1){
                                    element = save.getElementById(doc, "response", "accepted");
                                    allElementsNPCs = save.getAllChildsFromElement(element);
                                    response = "accepted";
                                    interactScore ++;
                                    save.changeElementChildValue(mainGameWindow.player.gameName, "partyHouse", "partyHouse", "partyHouse", "printerLevel", String.valueOf(1));
                                }else{
                                    element = save.getElementById(doc, "response", "undecided");
                                    allElementsNPCs = save.getAllChildsFromElement(element);
                                    response = "undecided";
                                }
                                
                                mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));
                            }else if((compatibility + interactScore*5) > 30){
                                element = save.getElementById(doc, "response", "denied");
                                allElementsNPCs = save.getAllChildsFromElement(element);

                                response = "denied";
                                mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));
                                interactScore --;
                            }else{
                                element = save.getElementById(doc, "response", "angry");
                                allElementsNPCs = save.getAllChildsFromElement(element);

                                response = "angry";
                                mainGameWindow.openChatPanel("Roger", save.getChildFromMapElements(allElementsRoger, "res"+String.valueOf(randomNumber)), name, save.getChildFromMapElements(allElementsNPCs, "res"+String.valueOf(randomNumber)));
                                interactScore --;
                            }
                            currentResponce = "interactJoinUs";
                        }
                        break;

                    default:
                        break;
                }
                
            }
        } catch (Exception e) {
        }
    }

    public void getOffset(){
        this.offsetX = random.nextInt(20 + 20) - 20;
        this.offsetY = random.nextInt(20 + 20) - 20;
    }
    
}
