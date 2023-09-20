package com.jv01.screens;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.generations.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainMenuScreen {
    public Save save = new Save();
    public  Game game = new Game();

    public String gameTitle = "Jeux";
    private JFrame frame = new JFrame("Main Menu"); 

    public JTextField nameTextField = new JTextField(20);
    public JTextField seedTextField = new JTextField(20);

    public JButton chooseSeedButton;

    public JPanel panel;
    public JList<String> gameNamesJList;

    public List<String> gameNamesList = new ArrayList<>();
    public List<String> gameNamesListDisplay = new ArrayList<>();
    public List<String> oldVersionGameNamesList = new ArrayList<>();

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
        
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                nameTextField.setForeground(Color.BLACK);
                nameTextField.setBackground(new Color(255, 255, 255, 255));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               
            }
        });

        seedTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                seedTextField.setForeground(Color.BLACK);
                seedTextField.setBackground(new Color(255, 255, 255, 255));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               
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
                if(isValideInput(gameName)){
                     startNewGame(gameName, null);
                }else{
                    nameTextField.setForeground(Color.RED);
                    nameTextField.setBackground(new Color(255, 120, 120, 255));
                    nameTextField.requestFocus();
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
        nameTextField.requestFocus();
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

                if(isValideInput(gameName)){
                    if(isValideInput(seed)){
                        startNewGame(gameName, seed);
                    }else{
                        seedTextField.setForeground(Color.RED);
                        seedTextField.setBackground(new Color(255, 120, 120, 255));
                        seedTextField.requestFocus();
                    }
                }else{
                    nameTextField.setForeground(Color.RED);
                    nameTextField.setBackground(new Color(255, 120, 120, 255));
                    nameTextField.requestFocus();
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
        seedTextField.requestFocus();
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
            Document doc = save.getDocumentXmlFromRoot("game");
            Element element = save.getElementById(doc, "game", "game");
            String currentVersion = save.getChildFromElement(element,"version");

            File savesDirectory = new File("saves");
            File[] gameFolders = savesDirectory.listFiles();

            

            String fileName;
            Document newDoc;
            Element newElement;
            String newCurrentVersion;

            for (File gameFolder : gameFolders) {
                if (gameFolder.isDirectory()) {
                    fileName = gameFolder.getName();
                    newDoc = save.getDocumentXml(fileName,"game");
                    newElement = save.getElementById(newDoc, "game", "game");
                    newCurrentVersion = save.getChildFromElement(newElement,"version");

                    if(newCurrentVersion.equals(currentVersion)){
                        gameNamesList.add(fileName);
                        gameNamesListDisplay.add(fileName);
                    }else {
                        gameNamesListDisplay.add("<html><font color='red'>" + fileName + "</font></html>");
                        gameNamesList.add(fileName);
                        oldVersionGameNamesList.add(fileName);
                    }                   
                }
            }

            gameNamesJList = new JList<>(gameNamesListDisplay.toArray(new String[0]));
            JScrollPane scrollPane = new JScrollPane(gameNamesJList);
            panel.add(scrollPane, constraints);
        } catch (Exception e) {
            
        }

        JButton loadButton = new JButton("Lancer la partie");
        panel.add(loadButton, constraints);

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int index = gameNamesJList.getSelectedIndex();
                String selectedGame = gameNamesList.get(index);

                if (selectedGame != null) {
                    for(String name : oldVersionGameNamesList){
                        if(name.equals(selectedGame)){
                            int choise = JOptionPane.showConfirmDialog(frame,
                            "La partie que vous souhaitez charger n'est pas de la bonne version!\n"+
                            "Êtes-vous sûr de vouloir la lancer?",

                            "Attention",
                            JOptionPane.YES_NO_OPTION);

                            if(choise == JOptionPane.YES_OPTION){
                                loadGame(selectedGame);
                            }else if(choise == JOptionPane.NO_OPTION){
                                gameNamesJList.clearSelection();
                            }
                        }else{
                            loadGame(selectedGame);
                        }
                    }           
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

    private boolean isValideInput(String input){
        boolean isValideInput = false;
        if(save.dropSpaceFromString(input).length() > 0){
            isValideInput = true;
        }
        return isValideInput;
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
