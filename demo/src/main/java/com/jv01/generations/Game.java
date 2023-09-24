package com.jv01.generations;

import java.io.File;
import java.nio.CharBuffer;
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
import com.jv01.fonctionals.Save;
import com.jv01.screens.CustomizePartyScreen;
import com.jv01.screens.LoadingScreen;

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

        editElement();

        generateParties();
        generateCorporations(5,20);

        loadingScreen.closeLoadingScreen();
        //startGame();
        new CustomizePartyScreen(name,seed);
    }

    public void editElement(){
        LocalDateTime now = LocalDateTime.now();
        save.changeElementChildValue(name,"game","game","game","date",Objects.toString(now));
        save.changeElementChildValue(name,"game","game","game","seed",seed);
        save.changeElementChildValue(name,"game","game","game","name",name);
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

    public  void generateCorporations(int numCorpos, int radius){
        Corporation corporation = null;
        CorporationsHousesFirstGeneration corporationsHousesFirstGeneration = new CorporationsHousesFirstGeneration(seed, numCorpos, radius);
        for(int i = 1; i <= numCorpos; i++){
            corporation = new Corporation(name);
            corporation.saveCorporation();
            new Chunks(corporationsHousesFirstGeneration.chunks.get(i-1), seed, name, 8);
        }
    }

    private void startGame(){
        MainGameWindow mainGameWindow = new MainGameWindow(name, seed);
        mainGameWindow.showMainGameWindow();
    }

    public void startGame(String gamename, String seed){                 
        MainGameWindow mainGameWindow = new MainGameWindow(gamename, seed);
        mainGameWindow.showMainGameWindow();
    }
}
