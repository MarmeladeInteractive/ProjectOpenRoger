package com.jv01.miniGames.games.NoGame;

import javax.swing.*;

public class NoGame {

    private JPanel gamePanel;
    private int boxSize;

    public NoGame(JPanel parentPanel, int boxSize) { 
        this.gamePanel = parentPanel; 
        this.gamePanel.setLayout(null);

        this.boxSize = boxSize;

        showMenu(); 
    }

    public void showMenu(){

        JLabel titleLabel = new JLabel("Ce jeux est corrompu");
        titleLabel.setBounds((boxSize/2) - 100, (boxSize/2) - 25, 200, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gamePanel.add(titleLabel);
    }
}
