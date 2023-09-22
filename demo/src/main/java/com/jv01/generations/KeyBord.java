package com.jv01.generations;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.Tempo;

public class KeyBord {
    public Save save = new Save();

    public KeyListener keyListener;

    public int leftKey = 81; //
    public int rightKey = 68; //
    public int upKey = 90; //
    public int downKey = 83; //
    public int runKey = 16; //

    public int intercatKey = 69; //
    public int upgradeKey = 85; // u

    public int mapKey = 77; // m

    public int quitKey = 27; // esc
    public int menuKey = 80; // p
    public int cheatCodeMenuKey = 113;

    public boolean leftKeyPressed = false;
    public boolean rightKeyPressed = false;
    public boolean upKeyPressed = false;
    public boolean downKeyPressed = false;
    public boolean runKeyPressed = false;

    public boolean interactKeyPressed = false;
    public Tempo tempoInteractKey = new Tempo();

    public boolean upgradeKeyPressed = false;
    public Tempo tempoUpgradeKey = new Tempo();

    public boolean mapKeyPressed = false;
    public Tempo tempoMapKey = new Tempo();

    public boolean quitKeyPressed = false;

    public boolean menuKeyPressed = false;
    public Tempo tempoMenuKey = new Tempo();

    public boolean cheatCodeMenuKeyPressed = false;
    public Tempo tempoCheatCodeMenuKey = new Tempo();
    
    public String gameName;
    
    public KeyBord(String gameName){
        this.gameName = gameName;

        getKeyBoardValues();  

        initializeKeyListener();
    }

    public void getKeyBoardValues(){
        Document doc = save.getDocumentXml(gameName,"functional/keyBoard");
        Element element = save.getElementById(doc, "keyBoard", "keyBoard");

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        this.leftKey = Integer.parseInt(save.getChildFromMapElements(allElements,"leftKey"));
        this.rightKey = Integer.parseInt(save.getChildFromMapElements(allElements,"rightKey"));
        this.upKey = Integer.parseInt(save.getChildFromMapElements(allElements,"upKey"));
        this.downKey = Integer.parseInt(save.getChildFromMapElements(allElements,"downKey"));
        this.runKey = Integer.parseInt(save.getChildFromMapElements(allElements,"runKey"));

        this.mapKey = Integer.parseInt(save.getChildFromMapElements(allElements,"mapKey"));

        this.intercatKey = Integer.parseInt(save.getChildFromMapElements(allElements,"intercatKey"));
        this.upgradeKey = Integer.parseInt(save.getChildFromMapElements(allElements,"upgradeKey"));

        this.quitKey = Integer.parseInt(save.getChildFromMapElements(allElements,"quitKey"));
        this.menuKey = Integer.parseInt(save.getChildFromMapElements(allElements,"menuKey"));

        this.cheatCodeMenuKey = Integer.parseInt(save.getChildFromMapElements(allElements,"cheatCodeMenuKey"));
    }

    public void initializeKeyListener(){
        keyListener = new KeyListener() {
             @Override
             public void keyTyped(KeyEvent e) {
             }
 
             @Override
             public void keyPressed(KeyEvent e) {
                 int keyCode = e.getKeyCode();
 
                 if (keyCode == KeyEvent.VK_LEFT || keyCode == leftKey) {
                     leftKeyPressed = true;
                 } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == rightKey) {
                     rightKeyPressed = true;
                 } else if (keyCode == KeyEvent.VK_UP || keyCode == upKey) {
                     upKeyPressed = true;
                 } else if (keyCode == KeyEvent.VK_DOWN || keyCode == downKey) {
                     downKeyPressed = true;
                 } else if (keyCode == runKey) {
                     runKeyPressed = true;
                 }
 
                 else if (keyCode == intercatKey && !tempoInteractKey.isRunning()) {
                     interactKeyPressed = true;
                     tempoInteractKey.start(100);
                 }else if (keyCode == upgradeKey && !tempoUpgradeKey.isRunning()) {
                     upgradeKeyPressed = true;
                     tempoUpgradeKey.start(100);
                 }
 
                 else if (keyCode == mapKey  && !tempoMapKey.isRunning()) {
                     mapKeyPressed = true;
                     tempoMapKey.start(100);
                 }

                 else if (keyCode == quitKey) {
                     quitKeyPressed = true;
                     int result = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to quit the game?",
                        "Quit Game Confirmation",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        // User clicked "Yes," so exit the game.
                        System.exit(0);
                    }
                 }
                 else if (keyCode == menuKey && !tempoMenuKey.isRunning()) {
                    menuKeyPressed = true;
                    tempoMenuKey.start(100);
                 }
                 
                 else if (keyCode == cheatCodeMenuKey && !tempoCheatCodeMenuKey.isRunning()) {
                    cheatCodeMenuKeyPressed = true;
                    tempoCheatCodeMenuKey.start(100);
                 } 
 
                 else{
                     System.out.println(keyCode);
                 }
             }
 
             @Override
             public void keyReleased(KeyEvent e) {
                 int keyCode = e.getKeyCode();
 
                 if (keyCode == KeyEvent.VK_LEFT || keyCode == leftKey) {
                     leftKeyPressed = false;
                 } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == rightKey) {
                     rightKeyPressed = false;
                 } else if (keyCode == KeyEvent.VK_UP || keyCode == upKey) {
                     upKeyPressed = false;
                 } else if (keyCode == KeyEvent.VK_DOWN || keyCode == downKey) {
                     downKeyPressed = false;
                 } else if (keyCode == runKey) {
                     runKeyPressed = false;
                 }
 
                 else if (keyCode == intercatKey) {
                     interactKeyPressed = false;
                 }else if (keyCode == upgradeKey) {
                     upgradeKeyPressed = false;
                 }
 
                 else if (keyCode == mapKey) {
                     mapKeyPressed = false;
                 }

                 else if (keyCode == quitKey) {
                     quitKeyPressed = false;;
                 }
                 else if (keyCode == menuKey) {
                    menuKeyPressed = false;;
                 }
                 else if (keyCode == cheatCodeMenuKey) {
                    cheatCodeMenuKeyPressed = false;
                 }
             }
         };
     }

     
}
