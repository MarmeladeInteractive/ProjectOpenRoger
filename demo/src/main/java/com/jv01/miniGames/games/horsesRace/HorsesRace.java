package com.jv01.miniGames.games.horsesRace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HorsesRace {

    private JPanel panel;

    public HorsesRace(JPanel parentPanel) { 
        panel = parentPanel; 
        panel.setLayout(null);

        showMenu();

    }

    public void showMenu(){

        JLabel titleLabel = new JLabel("Horses Race");
        titleLabel.setBounds(400-100, 400-25, 200, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(titleLabel);
    }
}
