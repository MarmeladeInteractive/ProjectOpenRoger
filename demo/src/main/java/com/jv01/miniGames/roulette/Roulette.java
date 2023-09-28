package com.jv01.miniGames.roulette;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;

public class Roulette{
    private JPanel gamePanel;
    private int boxSize;

    JButton startGameButton;

    private JPanel wheelPanel;

    public Roulette(JPanel parentPanel, int boxSize){
        this.gamePanel = parentPanel; 
        this.gamePanel.setLayout(null);

        this.boxSize = boxSize;

        showMenu();
    }

    public void showMenu(){
        JLabel titleLabel = new JLabel("Horses Race");
        titleLabel.setBounds((boxSize/2)-100, (boxSize/2)-25, 200, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        startGameButton = new JButton("Start Game");
        startGameButton.setBounds((boxSize/2)-100, (boxSize/2)+25, 200, 50);

        for (ActionListener al : startGameButton.getActionListeners()){
            startGameButton.removeActionListener(al);
        }   
        startGameButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                startGame();
            }
        });

        gamePanel.add(titleLabel);
        gamePanel.add(startGameButton, constraints);
    }

    private void startGame(){
        refreshGamePanel();
        createWheel();
    }

    private void createWheel(){
        wheelPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("");
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        wheelPanel.setBounds((boxSize/2)-150, (boxSize/2)-150, 300, 300);

        gamePanel.add(wheelPanel);
    }

    private void refreshGamePanel(){
        gamePanel.removeAll();
 
        gamePanel.revalidate();
        gamePanel.repaint();
    }














    public static void main(String[] args){
        JFrame frame;
        frame = new JFrame("Main Game Window");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        
        frame.getContentPane().setBackground(new java.awt.Color(90, 90, 90));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 800, 800);

        frame.add(panel);

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);

        new Roulette(panel,800);
    }
}
