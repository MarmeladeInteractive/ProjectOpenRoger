package com.jv01.miniGames;

import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.miniGames.games.horsesRace.HorsesRace;
import com.jv01.miniGames.games.roulette.Roulette;
import com.jv01.screens.GameWindowsSize;
import com.jv01.miniGames.games.NoGame.NoGame;
import com.jv01.miniGames.games.blackjack.Blackjack;
import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;

public class Arcade {
    public Save save = new Save();

    public GameWindowsSize GWS = new GameWindowsSize(false);

    public int boxSize;

    private JPanel panel;
    public JPanel gamePanel = new JPanel();

    private int idGame = 0;

    public MainGameWindow mainGameWindow;

    private JButton closeGameButton;

    public JTextField setFocus = new JTextField(1);

    private String docName = "stats/miniGamesStats";
    private String eleName;

    private LocalTime startTime;
    private LocalTime currentTime;
    private long currentPlayedTime;
    private long startTimePlayed = 0;

    private long totalBet;
    private long totalLoss;
    private long totalGain;
    private long gamesPlayed;
    private long timePlayed;

    public Arcade(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;

        this.boxSize = GWS.boxSize;
        this.panel = mainGameWindow.backgroundPanel.panel;

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
        startTime = LocalTime.now();
        mainGameWindow.player.canWalk = false;
        switch (idGame) {
            case 0:
                eleName = "horsesRace";
                new HorsesRace(this);
                break;
            case 1:
                eleName = "roulette";
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

    private void getValues(){
        Document doc = save.getDocumentXml(mainGameWindow.gameName, "stats/miniGamesStats");
        Element element = save.getElementById(doc, eleName, eleName);
        totalBet = Long.parseLong(save.getChildFromElement(element,"totalBet"));
        totalLoss = Long.parseLong(save.getChildFromElement(element,"totalLoss"));
        totalGain = Long.parseLong(save.getChildFromElement(element,"totalGain"));
        gamesPlayed = Long.parseLong(save.getChildFromElement(element,"gamesPlayed"));
        timePlayed = Long.parseLong(save.getChildFromElement(element,"timePlayed"));
        if(startTimePlayed==0)startTimePlayed = timePlayed;

        currentTime= LocalTime.now();
        currentPlayedTime = Duration.between(startTime, currentTime).getSeconds();
    }

    public void saveXml(long totBet, long gain){
        getValues();
        if(gain<0){
            save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"totalLoss",String.valueOf(totalLoss+gain));
        }else{
            save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"totalGain",String.valueOf(totalGain+gain));
        }
        save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"totalBet",String.valueOf(totalBet+totBet));
        save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"gamesPlayed",String.valueOf(gamesPlayed+1));
        save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"timePlayed",String.valueOf(startTimePlayed+currentPlayedTime));
    }
}
