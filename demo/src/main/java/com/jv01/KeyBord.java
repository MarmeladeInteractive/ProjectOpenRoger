package com.jv01;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class KeyBord {
    public Save save = new Save();

    public KeyListener keyListener;

    public int leftKey = 81;
    public int rightKey = 68;
    public int upKey = 90;
    public int downKey = 83;
    public int runKey = 16;

    public int intercatKey = 69;
    public int upgradeKey = 85;

    public int mapKey = 77;

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
    
    public String gameName;
    
    public KeyBord(String gameName){
        this.gameName = gameName;

        Document doc = save.getDocumentXml(gameName, "keyBoard");
        Element element = save.getElementById(doc, "keyBoard", "keyBoard");
        if(element == null){
            saveKeyBoard();
        }else{
            getKeyBoardValues();  
        }

        initializeKeyListener();
    }

    public void createKeyBoardElement(Document doc){
        Element keyBoardElement = doc.createElement("keyBoard");

        keyBoardElement.setAttribute("id", "keyBoard");

        Element element = doc.createElement("rightKey");
        element.appendChild(doc.createTextNode(String.valueOf(rightKey)));
        keyBoardElement.appendChild(element);

        element = doc.createElement("leftKey");
        element.appendChild(doc.createTextNode(String.valueOf(leftKey)));
        keyBoardElement.appendChild(element);

        element = doc.createElement("upKey");
        element.appendChild(doc.createTextNode(String.valueOf(upKey)));
        keyBoardElement.appendChild(element);

        element = doc.createElement("downKey");
        element.appendChild(doc.createTextNode(String.valueOf(downKey)));
        keyBoardElement.appendChild(element);

        element = doc.createElement("runKey");
        element.appendChild(doc.createTextNode(String.valueOf(runKey)));
        keyBoardElement.appendChild(element);

        element = doc.createElement("mapKey");
        element.appendChild(doc.createTextNode(String.valueOf(mapKey)));
        keyBoardElement.appendChild(element);

        element = doc.createElement("intercatKey");
        element.appendChild(doc.createTextNode(String.valueOf(intercatKey)));
        keyBoardElement.appendChild(element);

        element = doc.createElement("upgradeKey");
        element.appendChild(doc.createTextNode(String.valueOf(upgradeKey)));
        keyBoardElement.appendChild(element);

        doc.getDocumentElement().appendChild(keyBoardElement);
    }

    public void saveKeyBoard(){
        Document doc = save.getDocumentXml(gameName, "keyBoard");

        createKeyBoardElement(doc);

        save.saveXmlFile(doc, gameName, "keyBoard");
    }

    public void getKeyBoardValues(){
        Document doc = save.getDocumentXml(gameName,"keyBoard");
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
                     tempoInteractKey.start(1000);
                 }else if (keyCode == upgradeKey && !tempoUpgradeKey.isRunning()) {
                     upgradeKeyPressed = true;
                     tempoUpgradeKey.start(1000);
                 }
 
                 else if (keyCode == mapKey  && !tempoMapKey.isRunning()) {
                     mapKeyPressed = true;
                     tempoMapKey.start(100);
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
             }
         };
     }

     
}
