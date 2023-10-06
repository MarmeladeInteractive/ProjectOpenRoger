package com.jv01.miniGames;

import javax.swing.*;

import com.jv01.miniGames.games.horsesRace.HorsesRace;
import com.jv01.miniGames.games.roulette.Roulette;
import com.jv01.miniGames.games.NoGame.NoGame;
import com.jv01.generations.MainGameWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Arcade {
    public int boxSize;

    private JPanel panel;
    public JPanel gamePanel = new JPanel();

    private int idGame = 0;

    public MainGameWindow mainGameWindow;

    private JButton closeGameButton;

    public JTextField setFocus = new JTextField(1);

    public Arcade(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;

        this.boxSize = mainGameWindow.boxSize;
        this.panel = mainGameWindow.backgroundPanel;

        this.idGame = mainGameWindow.arcadeGameId;

        focusOnArcade();

        addArcade();

        runGame();

        panel.add(gamePanel);
    }

    private void addArcade() {
        JLabel imageLabel = new JLabel();  
    
        try {
            ImageIcon imageIcon = new ImageIcon("demo/img/arcades/arcadeInside01.png");
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

        closeGameButton = new JButton("X");
        closeGameButton.setBounds(boxSize - 50 - 10, 10, 50, 50);

        for (ActionListener al : closeGameButton.getActionListeners()) {
            closeGameButton.removeActionListener(al);
        }   
        closeGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGameWindow.showMainGameWindow();
            }
        });

        panel.add(closeGameButton);
        panel.setComponentZOrder(closeGameButton, 0);
    
        gamePanel.setLayout(null);
        gamePanel.setBounds(0, 0, boxSize, boxSize);
    }

    private void runGame(){
        switch (idGame) {
            case 0:
                new HorsesRace(this);
                break;
            case 1:
                new Roulette(this);
                break;
        
            default:
                new NoGame(gamePanel,boxSize);
                break;
        }
    }

    public void setCloseButtonVisibility(boolean v){
        closeGameButton.setVisible(v);
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
