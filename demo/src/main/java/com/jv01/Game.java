package com.jv01;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.security.SecureRandom;

import com.github.javafaker.Faker;

public class Game{
    public  Faker faker = new Faker();
    public  Save save = new Save();
    //public static Party party = new Party();
    //public  Player player = new Player();
    public  LoadingScreen loadingScreen = new LoadingScreen();

    public  String name;
    public  String seed;

    public Game(){
        name = "null";
    }

    public Game(String name){
        this.name = name;
    }

    public void runNewGame(String name, String seed01){
        seed = seed01;
        runGame(name);
    }

    public  void runGame(String gameName){
        name = gameName;
        File savesDirectory = new File("saves/" + gameName);
        if (!savesDirectory.exists()) {
            loadingScreen.showLoadingScreen();
            Timer timer = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    createGame(name, loadingScreen);
                }
            });
            timer.setRepeats(false);
            timer.start();
        
        }else
        {
            name = gameName;
            Document doc = save.getDocumentXml(gameName, "game");
            seed = readgameElementValue(doc,"seed");
            startGame();   
        }    
    }

    public  void createGame(String gameName, LoadingScreen loadingScreen){
        File savesDirectory = new File("saves/" + gameName);
        savesDirectory.mkdirs(); 

        name = gameName;

        if(seed == null){
            seed = generateSeed();
        }
        
        save.createAllFiles(gameName);

        Document doc = save.getDocumentXml(gameName, "game");

        createGameElement(doc);

        save.saveXmlFile(doc, gameName, "game");

        generateParties();

        loadingScreen.closeLoadingScreen();
        //startGame();
        CustomizePartyScreen customizePartyScreen = new CustomizePartyScreen(name);
    }

    public  Element createGameElement(Document doc){
        LocalDateTime now = LocalDateTime.now();
        Element rootElement = doc.getDocumentElement();
    
        Element gameElement = null;

        gameElement = doc.createElement("date");
        gameElement.appendChild(doc.createTextNode(Objects.toString(now)));
        rootElement.appendChild(gameElement);

        gameElement = doc.createElement("seed");
        gameElement.appendChild(doc.createTextNode(seed));
        rootElement.appendChild(gameElement);

        gameElement = doc.createElement("name");
        gameElement.appendChild(doc.createTextNode(name));
        rootElement.appendChild(gameElement);

        return rootElement;
    }

    public  String readgameElementValue(Document doc, String element) {
        NodeList seedElements = doc.getElementsByTagName(element);

        if (seedElements.getLength() > 0) {
            Element seedElement = (Element) seedElements.item(0);
            return seedElement.getTextContent();
        } else {
            return null;
        }
    }

    public  String generateSeed(){
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int SEED_LENGTH = 20;

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(SEED_LENGTH);

        for (int i = 0; i < SEED_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public  void generateParties(){
        int nParties = 0;

        boolean dd = false;
        boolean dc = false;
        boolean cc = false;
        boolean gc = false;
        boolean gg = false;

        Party party = null;
        while(nParties < 5){
            
            party = new Party(name);

            if(party.leftRightDifference > 5){
                if(!dd){
                    dd = true;
                    nParties++;
                    party.saveParty();
                }
            }else if(party.leftRightDifference > 3){
                if(!dc){
                    dc = true;
                    nParties++;
                    party.saveParty();
                }
            }else if((party.leftRightDifference < 3) && (party.leftRightDifference > -3)){
                if(!cc){
                    cc = true;
                    nParties++;
                    party.saveParty();
                }
            }else if(party.leftRightDifference < -5){
                if(!gg){
                    gg = true;
                    nParties++;
                    party.saveParty();
                }
            }else if(party.leftRightDifference < -3){
                if(!gc){
                    gc = true;
                    nParties++;
                    party.saveParty();
                }
            }else{
                party = null;
            }
        }
    }

    private void startGame(){
        MainGameWindow mainGameWindow = new MainGameWindow(name, seed);
        mainGameWindow.showMainGameWindow();
    }

    public void startGame(String gamename){
        MainGameWindow mainGameWindow = new MainGameWindow(gamename, seed);
        mainGameWindow.showMainGameWindow();
    }
}
