package com.jv01.screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jv01.player.Player;

import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

import java.awt.event.KeyListener;

import javax.swing.*;
import java.awt.*;

public class InfoMenuScreen extends JFrame {

    private PartyMenuScreen partyMenuScreen;
    private Player player;
    private String gameName;
    private JPanel panel;
    private JFrame frame = new JFrame("Menu");

    private static final int BOXE_SIZE = 800;

    public InfoMenuScreen(String gameName, Player player) {
        this.player = player;
        this.gameName = gameName;

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 100);

        // Utilisez la même instance de "frame" pour éviter de créer une nouvelle fenêtre JFrame.

        ChangeComponents(player.partyID);
        frame.setVisible(true);
    }

    public void ChangeComponents(int idParty) {
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(BOXE_SIZE, BOXE_SIZE);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        JPanel panel = new JPanel(new FlowLayout());

        JButton playerInfoButton = new JButton("Player Info");
        JButton partyInfoButton = new JButton("Party Info");
        JButton quitButton = new JButton("Quit");

        panel.add(playerInfoButton);
        panel.add(partyInfoButton);
        panel.add(quitButton);

        frame.add(panel);

        playerInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code pour afficher les informations du joueur
                JOptionPane.showMessageDialog(null, "Player Info Button Clicked");
            }
        });

        partyInfoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code pour afficher les informations de la partie
                showPartyMenu();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Ajoutez ici le code pour quitter l'application
                System.exit(0);
            }
        });
    }

    private void showPartyMenu() {
        if (partyMenuScreen == null) {
            partyMenuScreen = new PartyMenuScreen(this.gameName, this.player);
        }
        partyMenuScreen.setVisible(true);
    }
}