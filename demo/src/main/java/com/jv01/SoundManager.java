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

public class SoundManager {
    private Document soundDocument;

    public SoundManager() {
        loadSoundsFromXML("demo/xml/save/functional/sounds.xml"); // Chargez le fichier XML des sons lors de la création de SoundManager
    }

    public void playSound(int soundID) {
        Element soundElement = getSoundElementByID(soundID);

        if (soundElement != null) {
            String soundURL = soundElement.getElementsByTagName("soundUrl").item(0).getTextContent();
            
            try {
                // Charger le son à partir de l'URL
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundURL));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                Thread.sleep(10000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Sound not found for ID: " + soundID);
        }
    }

    private void loadSoundsFromXML(String xmlFilePath) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            soundDocument = builder.parse(new File(xmlFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element getSoundElementByID(int soundID) {
        NodeList soundNodes = soundDocument.getElementsByTagName("sound");

        for (int i = 0; i < soundNodes.getLength(); i++) {
            Element soundElement = (Element) soundNodes.item(i);
            int id = Integer.parseInt(soundElement.getAttribute("id"));
            if (id == soundID) {
                return soundElement;
            }
        }
        return null; // Retourne null si le son avec l'ID donné n'est pas trouvé.
    }

    public static void main(String[] args) {
        SoundManager soundManager = new SoundManager();
        soundManager.playSound(0); // Joue le son avec l'ID 0 (vous pouvez remplacer l'ID par celui que vous souhaitez).

        soundManager.playSound(1);

        soundManager.playSound(2);
    }
}