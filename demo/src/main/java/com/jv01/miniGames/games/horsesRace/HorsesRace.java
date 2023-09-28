package com.jv01.miniGames.games.horsesRace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HorsesRace {

    private JPanel gamePanel;

    JButton startGameButton;

    public HorsesRace(JPanel parentPanel) { 
        gamePanel = parentPanel; 
        gamePanel.setLayout(null);

        showMenu();
    
    }

    public void showMenu(){

        JLabel titleLabel = new JLabel("Horses Race");
        titleLabel.setBounds(100, 25, 200, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        startGameButton = new JButton("Start Game");
        startGameButton.setBounds(100, 100, 200, 50);

        for (ActionListener al : startGameButton.getActionListeners()) {
            startGameButton.removeActionListener(al);
        }   
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGameButton.setBackground(new Color(255,0,0));
            }
        });

        gamePanel.add(titleLabel);
        gamePanel.add(startGameButton, constraints);
    }
}
