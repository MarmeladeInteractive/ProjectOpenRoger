package com.jv01.miniGames.games.blackjack;


import com.jv01.miniGames.Arcade;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class Blackjack {

    public boolean isInGame = true;
    public Arcade arcade;
    public JPanel gamePanel;
    public int boxSize;

    public Decks decks;

    private JButton startGameButton;

    private JLabel playerLabel;
    private JLabel dealerLabel;
    private JLabel dealerCardLabel;
    private JButton hitButton;
    private JButton standButton;
    private JTextArea outputArea;
    private JPanel tablePanel;

    private JPanel playerCardsLaJPanel;

    public Hand playerHand;
    public int playerValue = 0;
    public Hand dealerHand;
    
    public Blackjack(JPanel parentPanel, int boxSize){
        this.gamePanel = parentPanel; 
        this.gamePanel.setLayout(null);

        this.boxSize = boxSize;

        this.isInGame = false;

        showMenu(); 
    }

    public void showMenu(){
        JLabel titleLabel = new JLabel("Blackjack");
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

    public void startGame(){
        refreshGamePanel();

        addButtons();
        addPlayerLabel();

        addPlayerCardsLabel();

        createTable(); 
        //gamePanel.add(tablePanel);

        PlayBJ();
    }

    public void PlayBJ() {
        decks = new Decks();
        decks.shuffle();

        playerHand = new Hand();
        dealerHand = new Hand();

        playerHand.addCard(decks.drawCard());
        playerHand.addCard(decks.drawCard());
        dealerHand.addCard(decks.drawCard());

        //updatePlayerHand(playerHand.getCards());
        

        System.out.println("Welcome to Blackjack!");
        System.out.println("Your hand: " + playerHand.getCards());
        System.out.println("Dealer's face-up card: " + dealerHand.getCards().get(0));

        displayCards();

        changeResultValues();
    }

    public void hitCard(){
        playerHand.addCard(decks.drawCard());
        System.out.println("Your hand: " + playerHand.getCards());
        changeResultValues();
    }

    public void standCard(){
        int dealerValue = dealerHand.getHandValue();
        while (dealerValue < 17) {
            dealerHand.addCard(decks.drawCard());
            dealerValue = dealerHand.getHandValue();
        }

        System.out.println("Dealer's hand: " + dealerHand.getCards());
        if (dealerValue > 21 || dealerValue < playerValue) {
            System.out.println("You win!");
        } else if (dealerValue > playerValue) {
            System.out.println("Dealer wins!");
        } else {
            System.out.println("It's a push (tie)!");
        }
        changeResultValues();
    }


    public void addButtons(){
        hitButton = new JButton("Hit");
        hitButton.setBounds((boxSize)-250, (boxSize/2)+25+50+200, 200, 50);
        hitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                hitCard();
            }
        });

        standButton = new JButton("Stand");
        standButton.setBounds((boxSize)-250, (boxSize/2)+25+200, 200, 50);
        standButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                standCard();
            }
        });

        gamePanel.add(hitButton);
        gamePanel.add(standButton);
    }

    public void createTable(){
        tablePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
        
                ImageIcon backgroundImage = new ImageIcon("demo/src/main/java/com/jv01/miniGames/games/blackjack/img/table/table.jpg");
                Image img = backgroundImage.getImage();
        
                Graphics2D g2d = (Graphics2D) g;
        
                g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        tablePanel.setOpaque(false);
        tablePanel.setBounds(0, 100, 800, 600);
         
        tablePanel.setLayout(null);
    }

    public void addPlayerLabel(){
        playerLabel = new JLabel("Your hand:");
        playerLabel.setBounds(50, 100, 400, 100);
        playerLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        gamePanel.add(playerLabel);
    }

    public void addPlayerCardsLabel(){
        playerCardsLaJPanel = new JPanel();
        playerCardsLaJPanel.setBounds(100,500,600,200);
        playerCardsLaJPanel.setBackground(new Color(255,255,0));
        gamePanel.add(playerCardsLaJPanel);
    }

    public void refreshGamePanel(){
        gamePanel.removeAll();
 
        gamePanel.revalidate();
        gamePanel.repaint();
        addArcadeBorder(gamePanel);///////////////////////////////////////////////////////////////////////////
    }

    public void displayCards(){
        int i = 0;
        playerCardsLaJPanel.removeAll();
        playerCardsLaJPanel.revalidate();
        playerCardsLaJPanel.repaint();
        for(Cards card:playerHand.getCards()){
            JLabel cardLabel = new JLabel(card.getIcon());
            cardLabel.setBounds(100+(card.widthCard+10)*i,200, card.widthCard, card.heightCard);
            playerCardsLaJPanel.add(cardLabel);
            i++;
        }
    }

    private void changeResultValues(){
        playerLabel.setText("Your hand:" + playerHand.getCards());
        displayCards();
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

        new Blackjack(panel,800);
    }
}
