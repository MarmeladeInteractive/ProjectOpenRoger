package com.jv01.screens;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.generations.Game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;

import java.util.Map;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewsWindow extends JFrame {

    public NewsWindow(String gameName) {
        setTitle("Morning News");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        displayMorningNews();
        generateRandomEvent();
    }

    private void displayMorningNews() {
        // Ajoutez ici le contenu du journal du matin, par exemple des articles et des images.
        // Vous pouvez utiliser des composants Swing comme JLabel, JTextArea, etc.
        // Exemple :
        JTextArea newsText = new JTextArea("Voici les nouvelles du matin :\n\n"
                + "1. Titre de l'article 1\n"
                + "   Contenu de l'article 1...\n\n"
                + "2. Titre de l'article 2\n"
                + "   Contenu de l'article 2...\n\n");
        newsText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(newsText);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void generateRandomEvent() {
        // Faire des trucs sympa genre MANIFESTATION
    }
}
