package com.jv01.miniGames.games.horsesRace;

import com.jv01.miniGames.Arcade;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class HorsesRace {
    public boolean isInGame = true;
    public Arcade arcade;
    public JPanel gamePanel;
    public int boxSize;

    private JButton startGameButton;
    private JButton runRaceButton;

    public Horses horses;

    public HorsesRace(JPanel parentPanel, int boxSize){ 
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

        horses = new Horses(5);
        horses.addHorsesSelectors(gamePanel);

        addRunRaceButton();
    }

    private void addRunRaceButton(){
        runRaceButton = new JButton("Lancer la course");
        runRaceButton.setBounds(boxSize - 200 - 80, (boxSize)-50-100, 200, 50);
 
        runRaceButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(horses.selectedHorse>=0){
                    runRace();
                }
            }
        });

        gamePanel.add(runRaceButton);
    }

    private void runRace(){
        new Race(this);
    }

    public void refreshGamePanel(){
        gamePanel.removeAll();
 
        gamePanel.revalidate();
        gamePanel.repaint();
        //addArcadeBorder(gamePanel);///////////////////////////////////////////////////////////////////////////
    }











    private static void addArcadeBorder(JPanel panel){
        JLabel imageLabel = new JLabel();  

        try{
        ImageIcon imageIcon = new ImageIcon("demo/img/arcades/arcadeInside01.png");
        Image image = imageIcon.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image);
        imageLabel.setIcon(imageIcon);

        imageLabel.setBounds(0, 0, 800, 800);
        } catch (Exception e){
        e.printStackTrace();
        }

        panel.setLayout(null);
        panel.add(imageLabel);
        panel.setComponentZOrder(imageLabel, 0);
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

        addArcadeBorder(panel);

        frame.add(panel);

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);

        new HorsesRace(panel,800);
    }
}
