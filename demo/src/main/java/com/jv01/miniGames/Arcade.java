package com.jv01.miniGames;

import javax.swing.*;

import com.jv01.miniGames.games.horsesRace.HorsesRace;
import com.jv01.generations.MainGameWindow;

import java.awt.*;

public class Arcade {
    private int boxSize;

    private JPanel panel;
    private JPanel gamePanel = new JPanel();

    private int idGame = 0;

    private MainGameWindow mainGameWindow;

    public JTextField setFocus = new JTextField(1);

    public Arcade(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;

        this.boxSize = mainGameWindow.boxSize;
        this.panel = mainGameWindow.backgroundPanel;

        this.idGame = mainGameWindow.arcadeGameId;

        mainGameWindow.player.positionX = 0;
        mainGameWindow.player.positionY = 0;

        focusOnArcade();

        addArcade();

        runGame(this.idGame);

        panel.add(gamePanel);
    }

    private void addArcade() {
        JLabel imageLabel = new JLabel();  
    
        try {
            ImageIcon imageIcon = new ImageIcon("demo/img/arcades/arcade01.png");
            Image image = imageIcon.getImage().getScaledInstance(boxSize, boxSize, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
    
            imageLabel.setBounds(0, 0, boxSize, boxSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        panel.setLayout(null);
        panel.add(imageLabel);
        panel.setComponentZOrder(imageLabel, 0);
    
        gamePanel.setLayout(null);
        gamePanel.setBounds(0, 0, 800, 800);
    }

    private void runGame(int id){
        switch (id) {
            case 0:
                showHorsesRace();
                break;
        
            default:
                break;
        }
    }
    

    public void showHorsesRace() {
        HorsesRace horsesRace = new HorsesRace(gamePanel);
    }

    private void focusOnArcade(){
        setFocus.setFocusTraversalKeysEnabled(false);
        setFocus.setMinimumSize(new Dimension(1, 1));
        setFocus.setPreferredSize(new Dimension(1, 1));

        gamePanel.add(setFocus);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setFocus.requestFocusInWindow();
            }
        });
    }
}
