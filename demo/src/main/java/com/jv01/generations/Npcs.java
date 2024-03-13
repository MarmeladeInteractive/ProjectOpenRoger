package com.jv01.generations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;

public class Npcs {
    public String gameName;
    private Random random = new Random();

    public String name;
    public String id = "0";
    public String imageUrl;
    public int[] size;

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

    public Npcs(String gameName){
        this.gameName = gameName;
    }

    public void createCharacter(String id){
        Character character = new Character(gameName,id,true);

        this.name = character.name;
        this.id = id;
        this.imageUrl = character.npcPic;
        this.size = character.size;

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
        test.add("interactBad");
        test.add("interactJoinMe");
        test.add("interactGiveItem");
        if(!mainGameWindow.selectionWheel.isOpen)mainGameWindow.selectionWheel.openSelectionWheel(x, y, test);
    }

    public void getOffset(){
        this.offsetX = random.nextInt(20 + 20) - 20;
        this.offsetY = random.nextInt(20 + 20) - 20;
    }
    
}
