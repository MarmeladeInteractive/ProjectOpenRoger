package com.jv01.screens;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jv01.fonctionals.Save;
import com.jv01.generations.Game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

import java.util.Map;
import java.util.Random;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewsWindow extends JFrame {

    private Save save = new Save();
    private Document randomEventsDocument;
    private Element randomEventChoosed;
    private Random random = new Random();

    public NewsWindow(String gameName) {
        setTitle("Morning News");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        randomEventsDocument = GetRandomEventsDoc(gameName);
        randomEventChoosed = getRandomEvent(randomEventsDocument);
        displayMorningNews(randomEventChoosed);
        generateRandomEvent();
    }

    private void displayMorningNews(Element randomEvent) {
        if (randomEvent != null) {
            String eventName = save.getChildFromElement(randomEvent, "name");
            String eventText = save.getChildFromElement(randomEvent, "text");
            
            JTextArea newsText = new JTextArea("Voici les nouvelles du matin :\n\n"
                + "1."+ eventName +"\n"
                + "   "+ eventText +"\n\n");
            newsText.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(newsText);

            add(scrollPane, BorderLayout.CENTER);

            System.out.println("Random Event Name: " + eventName);
            System.out.println("Random Event Text: " + eventText);
        } 
        else 
        {
            JTextArea newsText = new JTextArea("Voici les nouvelles du matin :\n\n"
                + "1.CA NA PAS MARCHE HAHAHAHAHAHAH\n"
                + "  nullosse...\n\n"
                + "2. nulnulnul\n"
                + "   supernul\n\n");
            newsText.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(newsText);

            add(scrollPane, BorderLayout.CENTER);
        }
    }

    private Document GetRandomEventsDoc(String gameName)
    {
        return randomEventsDocument = save.getDocumentXml(gameName, "randomEvents");
    }

    private Element getRandomEvent(Document doc) {
        // Get the list of randomEvent elements
        NodeList randomEventNodes = doc.getElementsByTagName("randomEvent");
        
        // Generate a random number within the range of available IDs
        int randomID = random.nextInt(randomEventNodes.getLength()) + 1;
        
        // Iterate through the randomEvent elements and find the one with the matching ID
        for (int i = 0; i < randomEventNodes.getLength(); i++) {
            Element randomEventElement = (Element) randomEventNodes.item(i);
            int eventID = Integer.parseInt(randomEventElement.getAttribute("id"));
            if (eventID == randomID) {
                return randomEventElement; // Return the selected random event element
            }
        }
        
        return null; // Return null if no matching event is found
    }

    private void generateRandomEvent() {
        System.out.println("Aie aie aie !");
    }
}
