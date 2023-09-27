package com.jv01.screens;

import javax.swing.*;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.ArrayList;
import java.util.List;

public class CheatCodeMenu {
    public boolean refresh = false;
    public boolean refreshDisplay = false;

    public MainGameWindow mainGameWindow;
    public Save save = new Save();

    public JFrame frame = new JFrame("Cheat code");

    public JTextField cheatCodeTextField = new JTextField(40);

    public JPanel panel;
    //public JList<String> cheatCodesJList;
    public GridBagConstraints constraints;

    public JButton cheatCodeButton;
    public JButton exitButton;

    public String cheatCode = "";

    public String[] allCMDs= {
            "$SET-MONEY",
            "$SET-STEP",
            "$SET-SPEED",
            "$SET-HOUR",
            "$SET-TIME",
            "$SET-DAY-DURATION",

            "$GET-SEED",
            "$GET-VERSION",
            "$GET-DATE",

            "$FILL-INVENTORY",
            "$EMPTY-INVENTORY",

            "$MOTHERLODE",

            "$LOAD-NEARBY-CHUNKS",
            "$LOAD-NEARBY-CHUNKS_",

            "$TP",

            "$STOP-TIME",
            "$START-TIME",

            "$DEV-MODE",

            "$EXIT",
    };

    public List<String> PossibleCMDs = new ArrayList<>();
    public int indexCurrentPossibleCMDs = 0;
    public String thisCMD;
    public boolean getTabed = false;

    public CheatCodeMenu(){
        cheatCodeTextField.setFocusTraversalKeysEnabled(false);
        createCheatCodeMenu();

        for (KeyListener kl : cheatCodeTextField.getKeyListeners()) {
            cheatCodeTextField.removeKeyListener(kl);
        }
        cheatCodeTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    runCMD();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    close();
                }else if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    if(!getTabed){
                        thisCMD = cheatCodeTextField.getText();
                        getTabed = true;
                    }
                    getPossibleCMDs();
                    if(PossibleCMDs.size()>0){
                        if(indexCurrentPossibleCMDs>=PossibleCMDs.size())indexCurrentPossibleCMDs=0;
                        cheatCodeTextField.setText(PossibleCMDs.get(indexCurrentPossibleCMDs));
                        indexCurrentPossibleCMDs++; 
                    }                    
                }else{
                    cheatCodeTextField.setForeground(Color.BLACK);
                    panel.setBackground(new Color(170, 170, 170, 255));
                    if(cheatCodeTextField.getText().length()==0){
                        indexCurrentPossibleCMDs = 0;
                        PossibleCMDs.clear();
                    }
                    getTabed = false;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }

    private void createCheatCodeMenu() {
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setSize(400, 40);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(170, 170, 170, 255));
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0; 
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(2, 5, 2, 5);

        constraints.gridy++; 

        cheatCodeTextField.setMinimumSize(new Dimension(220, cheatCodeTextField.getPreferredSize().height));
        cheatCodeTextField.setPreferredSize(new Dimension(220, cheatCodeTextField.getPreferredSize().height));

        panel.add(cheatCodeTextField, constraints);

        constraints.gridx++;

        cheatCodeButton = new JButton("Valider");

        panel.add(cheatCodeButton, constraints); 

        cheatCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCMD();
            }
        });

        exitButton = new JButton("Exit");

        constraints.gridx++;

        panel.add(exitButton, constraints); 

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        frame.add(panel);
        frame.setLocation(0, 0);
        frame.setVisible(false);  
    }
    private void runCMD(){
        cheatCodeTextField.requestFocus();
        String cheatCode = cheatCodeTextField.getText();
        String[] mots = cheatCode.split(" ");

        if (mots.length == 1) {
            mots = new String[]{mots[0], "@_é_@"};
        }

        switch (mots[0]) {
            case "$SET-MONEY":
                setMoney(mots[1]);
                break;
            case "$SET-STEP":
                setStep(mots[1]);
                break;
            case "$SET-SPEED":
                setSpeed(mots[1]);
                break;
            case "$SET-HOUR":
                setHour(mots[1]);
                break;
            case "$SET-TIME":
                setTime(mots[1]);
                break;
            case "$SET-DAY-DURATION":
                setDayDuration(mots[1]);
                break;

            case "$GET-SEED":
                cheatCodeTextField.setText(mainGameWindow.seed);
                break;
            case "$GET-VERSION":
                cheatCodeTextField.setText(save.getGameValue(mainGameWindow.gameName,"version"));
                break;
            case "$GET-DATE":
                cheatCodeTextField.setText(mainGameWindow.date.getDate() + " " + mainGameWindow.date.getHour());
                break;

            case "$FILL-INVENTORY":
                fillInventory();
                break;
            case "$EMPTY-INVENTORY":
                emptyInventory();
                break;

            case "$MOTHERLODE":
                motherLode();
                break;
            case "$LOAD-NEARBY-CHUNKS":
                loadNearbyChunks("10");//10
                break;
            case "$LOAD-NEARBY-CHUNKS_":
                loadNearbyChunks(mots[1]);
                break;

            case "$TP":
                tp(mots[1]);
                break;

            case "$STOP-TIME":
                setDayDuration("0");
                break;
            case "$START-TIME":
                setDayDuration("1440");
                break;

            case "$DEV-MODE":
                devMode();
                break;

            case "$EXIT":
                System.exit(0);
                break;

            default:
                cheatCodeTextField.setForeground(Color.RED);
                panel.setBackground(new Color(210, 170, 170, 255));
                break;
        }

    }

    private void getPossibleCMDs(){
        PossibleCMDs.clear();
        for(String cmd : allCMDs){
            if(cmd.toLowerCase().contains(thisCMD)){
                PossibleCMDs.add(cmd);
            }
        }
    }

    public void open(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        frame.setVisible(true);
        cheatCodeTextField.requestFocus();
    }

    public void close(){
        cheatCodeTextField.setText("");
        frame.setVisible(false);
    }

    public void error(){
        cheatCodeTextField.setForeground(Color.RED);
        panel.setBackground(new Color(210, 170, 170, 255));
    }


    private void setMoney(String value){
        try {         
            int number = Integer.parseInt(value);
            mainGameWindow.player.money = number;
            mainGameWindow.player.saveMoney();
            refreshDisplay = true;

        } catch (NumberFormatException e) {
            error();
        }
    }

    private void tp(String value) {
        try {
            int[] chunkInt = save.stringToIntArray(value);
            long[] chunk = new long[chunkInt.length];

            for (int i = 0; i < chunkInt.length; i++) {
                chunk[i] = (long) chunkInt[i];
            }
 
            mainGameWindow.currentChunk = chunk;
            mainGameWindow.player.chunk = chunk;
            mainGameWindow.changeChunk("TP");
            refreshDisplay = true;

        } catch (NumberFormatException e) {
            error();
        }
    }

    private void chargeChunk(String value) {
        try {
            int[] chunkInt = save.stringToIntArray(value);
            long[] chunk = new long[chunkInt.length];

            for (int i = 0; i < chunkInt.length; i++) {
                chunk[i] = (long) chunkInt[i];
            }
 
            mainGameWindow.currentChunk = chunk;
            mainGameWindow.changeChunk("chargeChunk");
            refreshDisplay = true;
            ////mainGameWindow.player.saveChunk();
            //refresh = true;

        } catch (NumberFormatException e) {
            error();
        }
    }

    private void setStep(String value){
        try {
            int step = Integer.parseInt(value);
 
            mainGameWindow.player.step = step;
            mainGameWindow.player.saveStep();
            refreshDisplay = true;
        } catch (NumberFormatException e) {
            error();
        }
    }
    private void setSpeed(String value){
        try {
            int speed = Integer.parseInt(value);
 
            mainGameWindow.player.speed = speed;
            mainGameWindow.player.saveSpeed();
            refreshDisplay = true;
        } catch (NumberFormatException e) {
            error();
        }
    }

    private void setHour(String value){
        try {
            String[] hourString = value.split(":");
            int[] hourInt = new int[]{Integer.parseInt(hourString[0]),Integer.parseInt(hourString[1])};

            if(hourInt[0]>=0 && hourInt[0]<=24 && hourInt[1]>=0 && hourInt[1]<=60){
                mainGameWindow.date.setHour(value);
                refreshDisplay = true;
            }else{
                error();
            }           
        } catch (NumberFormatException e) {
            error();
        }
    }

    private void setTime(String value){
        if(value.equals("nuit") || value.equals("night")){
            setHour("24:00");
        }else if(value.equals("jour") || value.equals("day")){
            setHour("12:00");
        }
    }

    private void setDayDuration(String value){
        try {
            long secondsPerDay = Long.parseLong(value);
            mainGameWindow.date.changeTimeForDay(secondsPerDay);       
        } catch (NumberFormatException e) {
            error();
        }
    }

    private void devMode(){
        setSpeed("4");
        setMoney("100000");
        refreshDisplay = true;
    }

    private void fillInventory(){
        mainGameWindow.player.inventory.fillInventory();
        mainGameWindow.player.inventory.saveAll();
        refreshDisplay = true;
    }

    private void emptyInventory(){
        mainGameWindow.player.inventory.emptyInventory();
        mainGameWindow.player.inventory.saveAll();
        refreshDisplay = true;
    }

    private void motherLode(){
        long newValue = mainGameWindow.player.money + 50000;
        setMoney(String.valueOf(newValue));
        refreshDisplay = true;
    }

    private void loadNearbyChunks(String value){
        int n = 10;
        try {
            n = Integer.parseInt(value);
        
            long[] playerChunk = mainGameWindow.currentChunk;
            long[] currentChunk = new long[] {playerChunk[0]-(n/2),playerChunk[1]-(n/2)};

            for(int i = 0; i <= (n); i++){
                for(int j = 0; j <= (n); j++){
                    chargeChunk("{"+String.valueOf(currentChunk[0])+","+String.valueOf(currentChunk[1])+"}");
                    currentChunk[1]++;
                }
                currentChunk[1] -= n+1;
                currentChunk[0]++;
            }

            tp("{"+String.valueOf(playerChunk[0])+","+String.valueOf(playerChunk[1])+"}");
        } catch (NumberFormatException e) {

        }
    }
}
