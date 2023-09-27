package com.jv01.miniGames;

import javax.swing.*;

import com.jv01.miniGames.games.horsesRace.HorsesRace;

import java.awt.*;

public class Arcade {
    JPanel panel = new JPanel();

    public Arcade() {
        JFrame frame = new JFrame("Arcade");
        frame.setUndecorated(true);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        
        addArcade();

        showHorsesRace();
        
        frame.add(panel);
        frame.setVisible(true);
    }

    private void addArcade() {
        JLabel imageLabel = new JLabel();

        try {
            ImageIcon imageIcon = new ImageIcon("demo/img/arcades/arcade01.png");
            Image image = imageIcon.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);

            imageLabel.setBounds(0, 0, 800, 800);
        } catch (Exception e) {
            e.printStackTrace();
        }

        panel.setLayout(null);
        panel.add(imageLabel);
        panel.setComponentZOrder(imageLabel, 0);
    }

    public void showHorsesRace() {
        HorsesRace horsesRace = new HorsesRace(panel);
    }

    public static void main(String[] args) {

        new Arcade();

    }
}
