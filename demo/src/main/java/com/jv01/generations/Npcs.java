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
        test.add("interactHello");
        test.add("interactHello");
        test.add("interactHello");

        Document doc = save.getDocumentXml(gameName,"functional/responces");
        Element element = save.getElementById(doc, "response", "greet");

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        if(!mainGameWindow.selectionWheel.isOpen)mainGameWindow.selectionWheel.openSelectionWheel(x, y,"npc", test);
        try {
            if(mainGameWindow.player.inputsManager.interactKeyPressed || (mainGameWindow.selectionWheel.isIconSelected && mainGameWindow.selectionWheel.interactType == "npc")){
                int compatibility = ideology.ideologicalCompatibility(character.ideologicalCode,mainGameWindow.player.ideologicalCode);
                Random rand = new Random();
                int randomNumber = rand.nextInt(30) + 1;

                switch (mainGameWindow.selectionWheel.iconSelectedId) {
                    case "interactHello":
                        if(!currentResponce.equals("interactHello")){
                            if(compatibility > 30){
                                mainGameWindow.openMsgLabels(save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                            }else{
                                element = save.getElementById(doc, "response", "reject");
                                allElements = save.getAllChildsFromElement(element);

                                mainGameWindow.openMsgLabels(save.getChildFromMapElements(allElements, "res"+String.valueOf(randomNumber)));
                            }
                            currentResponce = "interactHello";
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
