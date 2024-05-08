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

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        if(!mainGameWindow.selectionWheel.isOpen)mainGameWindow.selectionWheel.openSelectionWheel(x, y,"npc", test);
        try {
            if(mainGameWindow.player.inputsManager.interactKeyPressed || (mainGameWindow.selectionWheel.isIconSelected && mainGameWindow.selectionWheel.interactType == "npc")){

                int compatibility = ideology.ideologicalCompatibility(character.ideologicalCode,mainGameWindow.player.ideologicalCode);
                
                int randomNumber = random.nextInt(30) + 1;
                String response = "null";

                switch (mainGameWindow.selectionWheel.iconSelectedId) {
                    case "interactHello":
                        if(!currentResponce.equals("interactHello")){
                            if((compatibility+interactScore*5) > 40){
                                response = "greet";
                                mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                            }else{
                                element = save.getElementById(doc, "response", "reject");
                                allElements = save.getAllChildsFromElement(element);

                                response = "reject";
                                mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                            }
                            currentResponce = "interactHello";
                            sayHello = true;
                        }
                        break;

                        case "interactGiveBread":
                        if(!currentResponce.equals("interactGiveBread")){
                            if((compatibility + interactScore*5) > 30){
                                element = save.getElementById(doc, "response", "acceptedGift");
                                allElements = save.getAllChildsFromElement(element);

                                response = "acceptedGift";
                                mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                                interactScore ++;
                            }else{
                                element = save.getElementById(doc, "response", "deniedGift");
                                allElements = save.getAllChildsFromElement(element);

                                response = "deniedGift";
                                mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                            }
                            currentResponce = "interactGiveBread";
                        }
                        break;

                        case "interactJoinUs":

                        if(!currentResponce.equals("interactJoinUs")){
                            if(!sayHello){
                                if((compatibility) > 70){
                
                                }else{
                                    element = save.getElementById(doc, "response", "dontSayHello");
                                    allElements = save.getAllChildsFromElement(element);
                                    response = "dontSayHello";
                                    mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                                    interactScore --;
                                    currentResponce = "interactJoinUs";
                                    break;
                                }
                            }
                            if((interactScore <= -2) || (interactScore >= 5)){
                                element = save.getElementById(doc, "response", "angry");
                                allElements = save.getAllChildsFromElement(element);
    
                                response = "angry";
                                mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                                interactScore --;
                                currentResponce = "interactJoinUs";
                                break;
                            }
                            if((compatibility + interactScore*5) > 80){
                                if(random.nextInt(6) == 1){
                                    element = save.getElementById(doc, "response", "undecided");
                                    allElements = save.getAllChildsFromElement(element);
                                    response = "undecided";
                                }else{
                                    element = save.getElementById(doc, "response", "accepted");
                                    allElements = save.getAllChildsFromElement(element);
                                    response = "accepted";
                                    interactScore ++;
                                }

                                mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));   
                            }else if((compatibility + interactScore*5) > 60){
                                if(random.nextInt(4) == 1){
                                    element = save.getElementById(doc, "response", "accepted");
                                    allElements = save.getAllChildsFromElement(element);
                                    response = "accepted";
                                    interactScore ++;
                                }else{
                                    element = save.getElementById(doc, "response", "undecided");
                                    allElements = save.getAllChildsFromElement(element);
                                    response = "undecided";
                                }
                                
                                mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                            }else if((compatibility + interactScore*5) > 30){
                                element = save.getElementById(doc, "response", "denied");
                                allElements = save.getAllChildsFromElement(element);

                                response = "denied";
                                mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                                interactScore --;
                            }else{
                                element = save.getElementById(doc, "response", "angry");
                                allElements = save.getAllChildsFromElement(element);

                                response = "angry";
                                mainGameWindow.openChatPanel("Roger", "hjjhkhkhj", "test Name", save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
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
