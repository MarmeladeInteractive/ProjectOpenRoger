package com.jv01.screens;

import javax.swing.*;

import com.jv01.generations.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen {
   
    public  Game game = new Game();

    public String gameTitle = "Jeux";
    private JFrame frame = new JFrame("Main Menu"); 

    public JTextField nameTextField = new JTextField(20);
    public JTextField seedTextField = new JTextField(20);

    public JButton chooseSeedButton;

    public JPanel panel;
    public JList<String> gameNamesJList;
    public GridBagConstraints constraints;

    public String gameName = "";

    public void showMainMenu() {
        frame.getContentPane().removeAll();
        frame.repaint();
    
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
    
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);
    
        JLabel titleLabel = new JLabel(gameTitle);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, constraints);
    
        JButton newGameButton = new JButton("Nouvelle partie");
        JButton loadGameButton = new JButton("Charger une partie");
        JButton quitGameButton = new JButton("Quit");
        panel.add(newGameButton, constraints);
        panel.add(loadGameButton, constraints);
        panel.add(quitGameButton,constraints);
    
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewGamePage();
            }
        });
    
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoadGamePage();
            }
        });
        
        quitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });        
    
        frame.add(panel);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }
    

    private void openNewGamePage() {
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        JLabel titleLabel = new JLabel("Entrez le nom de la nouvelle partie:");
        panel.add(titleLabel, constraints);

        panel.add(nameTextField, constraints);

        JButton chooseSeedButton = new JButton("Choisir une seed");
        panel.add(chooseSeedButton, constraints);

        chooseSeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewGamePageSeed();
            }
        });

        JButton startGameButton = new JButton("Lancer la partie");
        panel.add(startGameButton, constraints);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gameName = nameTextField.getText();
                startNewGame(gameName, null);
            }
        });

        JButton backButton = new JButton("Retour au menu");
        panel.add(backButton, constraints);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });

        frame.add(panel);
        frame.setVisible(true);  
        frame.setLocationRelativeTo(null);
    }

    private void openNewGamePageSeed() {
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        JLabel titleLabel = new JLabel("Entrez le nom de la nouvelle partie:");
        panel.add(titleLabel, constraints);

        panel.add(nameTextField, constraints);

        JLabel titleLabelSeed = new JLabel("Entrez une seed:");
        panel.add(titleLabelSeed, constraints);

        panel.add(seedTextField, constraints);

        JButton startGameButton = new JButton("Lancer la partie");
        panel.add(startGameButton, constraints);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gameName = nameTextField.getText();
                String seed = seedTextField.getText();
                startNewGame(gameName, seed);
            }
        });

        JButton backButton = new JButton("Retour au menu");
        panel.add(backButton, constraints);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });

        frame.add(panel);
        frame.setVisible(true);  
        frame.setLocationRelativeTo(null);
    }


    private void openLoadGamePage() {
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        JLabel titleLabel = new JLabel("Sélectionnez une partie:");
        panel.add(titleLabel, constraints);

        try{
            File savesDirectory = new File("saves");
            File[] gameFolders = savesDirectory.listFiles();

            List<String> gameNamesList = new ArrayList<>();
            for (File gameFolder : gameFolders) {
                if (gameFolder.isDirectory()) {
                    gameNamesList.add(gameFolder.getName());
                }
            }

            gameNamesJList = new JList<>(gameNamesList.toArray(new String[0]));
            JScrollPane scrollPane = new JScrollPane(gameNamesJList);
            panel.add(scrollPane, constraints);
        } catch (Exception e) {
            
        }

        JButton loadButton = new JButton("Lancer la partie");
        panel.add(loadButton, constraints);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedGame = gameNamesJList.getSelectedValue();
                if (selectedGame != null) {
                    loadGame(selectedGame);
                }
            }
        });

        JButton backButton = new JButton("Retour au menu");
        panel.add(backButton, constraints);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });

        frame.add(panel);
        frame.setVisible(true);     
        frame.setLocationRelativeTo(null);
    }

    private void startNewGame(String gameName, String seed) {
        frame.dispose();
        game.runNewGame(gameName, seed);
    }

    private void loadGame(String gameName) {
        frame.dispose();
        game.runGame(gameName);
    }
}
