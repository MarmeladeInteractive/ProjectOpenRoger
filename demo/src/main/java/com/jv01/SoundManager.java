package com.jv01;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javax.sound.sampled.*;

import java.io.File;
import javax.sound.sampled.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import org.w3c.dom.*;

import com.jv01.fonctionals.Save;

public class SoundManager {
    public Save save = new Save();

    public String gameName;

    private Document sfxDocument;
    private Document musicDocument;

    private Clip currentSFXClip;
    private Clip currentMusicClip;

    public SoundManager(String gameName){
        this.gameName = gameName;
        //loadSoundsFromXML("demo/xml/save/functional/sounds.xml"); 
        //loadMusicsFromXML("demo/xml/save/functional/music.xml"); 

        sfxDocument = save.getDocumentXml(gameName, "functional/sounds");
        musicDocument = save.getDocumentXml(gameName, "functional/music");
        // Chargez le fichier XML des sons lors de la création de SoundManager
    }

    public void playSFX(int sfxID) {
       // Element sfxElement = getSoundElementByID(sfxID);

        Element sfxElement = save.getElementById(sfxDocument,"sound",String.valueOf(sfxID));

        if (sfxElement != null) {
            //String soundURL = sfxElement.getElementsByTagName("soundUrl").item(0).getTextContent();
            String soundURL = save.stringToStringArray(save.getChildFromElement(sfxElement, "soundUrl"))[0];
            soundURL = save.dropSpaceFromString(soundURL);

            System.out.println(soundURL);
            try {
                // Charger le son à partir de l'URL
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundURL));
                currentSFXClip = AudioSystem.getClip();
                currentSFXClip.open(audioInputStream);
                currentSFXClip.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sound not found for ID: " + sfxID);
        }
    }

    public void playMusic(int musicID) {
        Element musicElement = getMusicElementByID(musicID);

        if (musicElement != null) {
            String musicURL = musicElement.getElementsByTagName("musicUrl").item(0).getTextContent();
            
            try {
                // Charger le son à partir de l'URL
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(musicURL));
                currentMusicClip = AudioSystem.getClip();
                currentMusicClip.open(audioInputStream);
                currentMusicClip.start();
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sound not found for ID: " + musicID);
        }
    }

    public void stopSFX() {
        currentSFXClip.stop();
    }

    public void stopMusic() {
        currentMusicClip.stop();
    }

    private void loadSoundsFromXML(String xmlFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            sfxDocument = builder.parse(new File(xmlFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadMusicsFromXML(String xmlFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            musicDocument = builder.parse(new File(xmlFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element getSoundElementByID(int soundID) {
        NodeList soundNodes = sfxDocument.getElementsByTagName("sound");

        for (int i = 0; i < soundNodes.getLength(); i++) {
            Element soundElement = (Element) soundNodes.item(i);
            int id = Integer.parseInt(soundElement.getAttribute("id"));
            if (id == soundID) {
                return soundElement;
            }
        }
        return null; // Retourne null si le son avec l'ID donné n'est pas trouvé.
    }

    private Element getMusicElementByID(int soundID) {
        NodeList musicNodes = musicDocument.getElementsByTagName("music");

        for (int i = 0; i < musicNodes.getLength(); i++) {
            Element soundElement = (Element) musicNodes.item(i);
            int id = Integer.parseInt(soundElement.getAttribute("id"));
            if (id == soundID) {
                return soundElement;
            }
        }
        return null; // Retourne null si le son avec l'ID donné n'est pas trouvé.
    }


    public static void main(String[] args) {
        
        SoundManager soundManager = new SoundManager("ee");
        //soundManager.playSFX(0); // Joue le son avec l'ID 0 (vous pouvez remplacer l'ID par celui que vous souhaitez).
        //soundManager.playMusic(0);
        //soundManager.stopMusic();
        //soundManager.playSFX(1);
        soundManager.playSFX(2);
        
    }
}