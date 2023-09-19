package com.jv01;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import javax.sound.sampled.*;

public class SoundManagerTest {
    public static void main(String[] args) {
        try {
            // Charger le fichier XML contenant les informations sur les sons
            File xmlFile = new File("demo/xml/save/functional/sounds.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Récupérer la liste des éléments de son depuis le XML
            NodeList soundList = doc.getElementsByTagName("sound");

            // Créer un tableau de SoundManager pour stocker les sons
            SoundManager[] soundManagers = new SoundManager[soundList.getLength()];

            // Parcourir la liste des éléments de son
            for (int i = 0; i < soundList.getLength(); i++) {
                Node soundNode = soundList.item(i);
                if (soundNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element soundElement = (Element) soundNode;

                    // Récupérer le nom du son
                    String soundName = soundElement.getElementsByTagName("name").item(0).getTextContent();
                    System.out.println(soundName);

                    // Récupérer le chemin d'accès du son
                    String soundPath = soundElement.getElementsByTagName("soundUrl").item(0).getTextContent().trim();
                    System.out.println(soundPath);

                    // Créer une instance de SoundManager avec le chemin d'accès du son
                    SoundManager soundManager = new SoundManager(soundPath);

                    // Ajouter le SoundManager au tableau
                    soundManagers[i] = soundManager;
                }
            }

            // Vous avez maintenant un tableau de SoundManager pour chaque son
            // Vous pouvez jouer les sons en utilisant les méthodes de SoundManager
            soundManagers[0].playSound(); // Joue le premier son
            Thread.sleep(5000);
            soundManagers[1].playSound(); // Joue le deuxième son, et ainsi de suite
            Thread.sleep(5000);            
            soundManagers[2].playSound(); // Joue le eme son
            Thread.sleep(5000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}