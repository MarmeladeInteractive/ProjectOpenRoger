package com.jv01.screens;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;
import com.jv01.generations.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

    public JButton startGameButton;
    public JButton backButton;

    public JButton chooseSeedButton;

    public JPanel panel;
    public JList<String> gameNamesJList;

    public List<String> gameNamesList = new ArrayList<>();
    public List<String> gameNamesListDisplay = new ArrayList<>();
    public List<String> oldVersionGameNamesList = new ArrayList<>();

    public GridBagConstraints constraints;

    public String gameName = "";

    public SoundManager soundManager = new SoundManager();
    public boolean isMusicPlaying = false;

    public void showMainMenu() {

        if(!isMusicPlaying){
            soundManager.playMusic(0);
            isMusicPlaying = true;
        }
        
        
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

        for (ActionListener al : newGameButton.getActionListeners()) {
            newGameButton.removeActionListener(al);
        }   
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openNewGamePage();
            }
        });
    
        for (ActionListener al : loadGameButton.getActionListeners()) {
            loadGameButton.removeActionListener(al);
        }
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoadGamePage();
            }
        });
        
        for (ActionListener al : quitGameButton.getActionListeners()) {
            quitGameButton.removeActionListener(al);
        }
        quitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        for (KeyListener kl : nameTextField.getKeyListeners()) {
            nameTextField.removeKeyListener(kl);
        }
        nameTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startGameButton.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    backButton.doClick();
                }else{
                    nameTextField.setForeground(Color.BLACK);
                    nameTextField.setBackground(new Color(255, 255, 255, 255));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        for (KeyListener kl : seedTextField.getKeyListeners()) {
            seedTextField.removeKeyListener(kl);
        }
        seedTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startGameButton.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    backButton.doClick();
                }else{
                    seedTextField.setForeground(Color.BLACK);
                    seedTextField.setBackground(new Color(255, 255, 255, 255));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
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

        startGameButton = new JButton("Lancer la partie");
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

        backButton = new JButton("Retour au menu");
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

        startGameButton = new JButton("Lancer la partie");
        panel.add(startGameButton, constraints);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String gameName = nameTextField.getText();
                String seed = seedTextField.getText();

                if(isValideInput(gameName)){
                    if(isValideInput(seed)){
                        startNewGame(gameName, null);
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

        backButton = new JButton("Retour au menu");
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

            List<String> savesNames = getAllSavesNames();

            Document newDoc;
            Element newElement;
            String newCurrentVersion;


            gameNamesList.clear();
            gameNamesListDisplay.clear();
            oldVersionGameNamesList.clear();

            for (String fileName : savesNames) {
                try{
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
                } catch (Exception e) {
        
                }                
            }


            gameNamesJList = new JList<>(gameNamesListDisplay.toArray(new String[0]));
            JScrollPane scrollPane = new JScrollPane(gameNamesJList);

            panel.add(scrollPane, constraints);
        } catch (Exception e) {
            
        }

        startGameButton = new JButton("Lancer la partie");
        panel.add(startGameButton, constraints);

        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index;
                String selectedGame = null;

                try{
                    index = gameNamesJList.getSelectedIndex();
                    selectedGame = gameNamesList.get(index);
                } catch (Exception e1){
                    JOptionPane.showMessageDialog(frame,"Choisisez une sauvegarde","Attention",JOptionPane.CANCEL_OPTION);
                }              

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
                    if(oldVersionGameNamesList.size()==0){
                        loadGame(selectedGame);
                    }          
                }
            }
        });

        backButton = new JButton("Retour au menu");
        panel.add(backButton, constraints);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMainMenu();
            }
        });

        gameNamesJList.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    startGameButton.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    backButton.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
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

    private boolean isExistingNameMap(String newNameMap){
        boolean isExisting = false;
        List<String> savesNames = getAllSavesNames();
        try{
            for (String gameName : savesNames) {
                if(gameName.equals(newNameMap)){
                    isExisting = true;
                    break;
                }              
            }
        } catch (Exception e) {
            
        }
        return isExisting;
    }

    private List<String> getAllSavesNames(){
        List<String> savesNames = new ArrayList<>();
        try{
            File savesDirectory = new File("saves");
            File[] gameFolders = savesDirectory.listFiles();

            for (File gameFolder : gameFolders) {
                if (gameFolder.isDirectory()) {
                    savesNames.add(gameFolder.getName());         
                }
            }
        } catch (Exception e) {
            
        }
        return savesNames;
    }

    private void startNewGame(String gameName, String seed) {
        if(isExistingNameMap(gameName)){
            nameTextField.setForeground(Color.RED);
            nameTextField.setBackground(new Color(255, 120, 120, 255));
            nameTextField.requestFocus();
            JOptionPane.showMessageDialog(frame,"Une sauvegarde porte déja ce nom","Attention",JOptionPane.CANCEL_OPTION);
        }else{
            soundManager.stopMusic(false);
            frame.dispose();
            game.runNewGame(gameName, seed);
        }
    }

    private void loadGame(String gameName) {
        soundManager.stopMusic(false);
        frame.dispose();
        game.runGame(gameName);
    }
}
