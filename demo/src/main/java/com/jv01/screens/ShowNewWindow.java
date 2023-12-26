package com.jv01.screens;

import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;
import com.jv01.screens.Windows.Inventory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;

public class ShowNewWindow {
    public Save save = new Save();
    public int boxSize;

    private JPanel panel;
    public JPanel newWindowPanel = new JPanel();

    private String idWindow = "";

    public MainGameWindow mainGameWindow;

    private JButton closeWindowButton;

    public JTextField setFocus = new JTextField(1);

    //private String docName = "stats/miniGamesStats";
    //private String eleName;

    /*private LocalTime startTime;
    private LocalTime currentTime;
    private long currentPlayedTime;
    private long startTimePlayed = 0;

    private long totalBet;
    private long totalLoss;
    private long totalGain;
    private long gamesPlayed;
    private long timePlayed;*/

    public ShowNewWindow(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;

        this.boxSize = mainGameWindow.boxSize;
        this.panel = mainGameWindow.backgroundPanel;

        this.idWindow = mainGameWindow.newWindowId;

        focusOnNewWindow();

        addNewWindow();

        dispayNewWindow();

        panel.add(newWindowPanel);
    }

    private void addNewWindow() {
        panel.setLayout(null);

        closeWindowButton = new JButton("X");
        closeWindowButton.setBounds(boxSize - 50 - 10, 10, 50, 50);

        for (ActionListener al : closeWindowButton.getActionListeners()) {
            closeWindowButton.removeActionListener(al);
        }   
        closeWindowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGameWindow.showMainGameWindow();
            }
        });

        panel.add(closeWindowButton);
        panel.setComponentZOrder(closeWindowButton, 0);
    
        newWindowPanel.setLayout(null);
        newWindowPanel.setBounds(0, 0, boxSize, boxSize);
    }

    private void dispayNewWindow(){
        //startTime = LocalTime.now();
        mainGameWindow.player.canWalk = false;
        switch (idWindow) {
            case "Inventory":
                new Inventory(this);
                break;
        
            default:
                //new NoGame(newWindowPanel,boxSize);
                break;
        }
    }

    public void setCloseButtonVisibility(boolean v){
        closeWindowButton.setVisible(v);
    }

    private void focusOnNewWindow(){
        setFocus.setFocusTraversalKeysEnabled(false);
        setFocus.setMinimumSize(new Dimension(1, 1));
        setFocus.setPreferredSize(new Dimension(1, 1));

        newWindowPanel.add(setFocus);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setFocus.requestFocusInWindow();
            }
        });
    }

    /*private void getValues(){
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
    }*/

    /*public void saveXml(long totBet, long gain){
        getValues();
        if(gain<0){
            save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"totalLoss",String.valueOf(totalLoss+gain));
        }else{
            save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"totalGain",String.valueOf(totalGain+gain));
        }
        save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"totalBet",String.valueOf(totalBet+totBet));
        save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"gamesPlayed",String.valueOf(gamesPlayed+1));
        save.changeElementChildValue(mainGameWindow.gameName,docName,eleName,eleName,"timePlayed",String.valueOf(startTimePlayed+currentPlayedTime));
    }*/
}
