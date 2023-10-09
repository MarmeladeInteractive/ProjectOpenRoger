package com.jv01.miniGames.games.horsesRace;

import com.jv01.miniGames.Arcade;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

    public JLabel betLabel = new JLabel("0");
    public JTextField betField;
    public int currentBet = 0;
    private int maxBet = 1000;
    private int betStep = 10;

    public HorsesRace(JPanel parentPanel, int boxSize){ 
        this.gamePanel = parentPanel; 
        this.gamePanel.setLayout(null);

        this.boxSize = boxSize;

        this.isInGame = false;

        showMenu(); 
    }

    public HorsesRace(Arcade arcade){ 
        this.arcade = arcade;
        this.gamePanel = arcade.gamePanel; 
        this.gamePanel.setLayout(null);

        this.boxSize = arcade.boxSize;

        this.isInGame = true;

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

    public void startGame(){
        refreshGamePanel();

        horses = new Horses(5);
        horses.addHorsesSelectors(gamePanel);

        addBetPanel();

        addRunRaceButton();
    }

    private void addBetPanel(){
        JPanel betBox01 = new JPanel();
        betBox01.setBackground(new Color(205,205,205));
        betBox01.setBounds(boxSize - 300 - 80, (boxSize)-100-200-40, 300, 40);

        betBox01.setLayout(null);

        JLabel miseMax = new JLabel("Mise max : "+String.valueOf(maxBet));
        miseMax.setBounds(20, 0, 300, 40);
        miseMax.setFont(new Font("Arial", Font.PLAIN, 16));
        miseMax.setHorizontalAlignment(SwingConstants.LEFT);
        betBox01.add(miseMax);

        gamePanel.add(betBox01);

        JPanel betBox = new JPanel();
        betBox.setBackground(new Color(205,205,205));
        betBox.setBounds(boxSize - 300 - 80, (boxSize)-100-200, 300, 80);

        betBox.setLayout(null);

        JButton l = new JButton("<");
        l.setBounds(5, 15, 50, 50);
        l.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if((currentBet-betStep)>=0){
                    currentBet-=betStep;
                    betField.setText(String.valueOf(currentBet));
                    betField.setForeground(Color.BLACK);
                }else{
                    currentBet = 0;
                    betField.setText(String.valueOf(currentBet));
                    betField.setForeground(Color.BLACK);
                }
            }
        });
        betBox.add(l);

        betField = new JTextField(String.valueOf(currentBet));
        betField.setBounds(5 + 50, 20, 200 - 10, 40);
        betField.setFont(new Font("Arial", Font.PLAIN, 24));
        betField.setHorizontalAlignment(SwingConstants.CENTER);

        betField.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateBetFromTextField();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateBetFromTextField();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                
            }
        });

        betBox.add(betField);

        JButton r = new JButton(">");
        r.setBounds(300-5-50, 15, 50, 50);
        r.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if((currentBet+betStep)<=maxBet){
                    if(isInGame){
                        if((currentBet+betStep)<=arcade.mainGameWindow.player.money){
                            currentBet+=betStep;
                        }
                    }else{
                        currentBet+=betStep;
                    } 
                    betField.setText(String.valueOf(currentBet));
                    betField.setForeground(Color.BLACK);
                }else{
                    betField.setForeground(Color.RED);
                }
            }
        });
        betBox.add(r);
        

        gamePanel.add(betBox);
    }
    private boolean updateBetFromTextField(){
        String betText = betField.getText();
        try{
            int newBet = Integer.parseInt(betText);
            if(newBet >= 0 && newBet <= maxBet){
                if(isInGame){
                    if(newBet <= arcade.mainGameWindow.player.money){
                        currentBet = newBet;
                        betField.setForeground(Color.BLACK);
                        return true;
                    }else{
                        betField.setForeground(Color.RED);
                    }   
                }else{
                    currentBet = newBet;
                    betField.setForeground(Color.BLACK);
                    return true;
                }
            }else{
                betField.setForeground(Color.RED);
            }
        }catch(NumberFormatException ex){
            betField.setForeground(Color.RED);
        }
        return false;
    }

    private void addRunRaceButton(){
        runRaceButton = new JButton("Lancer la course");
        runRaceButton.setBounds(boxSize - 200 - 80, (boxSize)-50-100, 200, 50);
 
        runRaceButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(horses.selectedHorse>=0){
                    if(updateBetFromTextField()){
                        runRace();
                    }else{
                        betField.setForeground(Color.RED);
                    }
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
        addArcadeBorder(gamePanel);///////////////////////////////////////////////////////////////////////////
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
