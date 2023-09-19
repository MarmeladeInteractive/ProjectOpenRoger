package com.jv01.screens;

import javax.swing.*;
import javax.swing.event.DocumentListener;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;

import javax.swing.event.DocumentEvent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheatCodeMenu {
    public boolean refresh = false;
    public boolean refreshDisplay = false;

    MainGameWindow mainGameWindow;
    Save save = new Save();


    public JFrame frame = new JFrame("Cheat code");

    public JTextField cheatCodeTextField = new JTextField(40);

    public JPanel panel;
    public JList<String> cheatCodesJList;
    public GridBagConstraints constraints;

    public String cheatCode = "";

    public CheatCodeMenu(){
        createCheatCodeMenu();

        cheatCodeTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                cheatCodeTextField.setForeground(Color.BLACK);
                panel.setBackground(new Color(170, 170, 170, 255));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
               
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

        JButton cheatCodeButton = new JButton("Valider");

        constraints.gridx++;

        panel.add(cheatCodeButton, constraints); 

        cheatCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cheatCodeTextField.requestFocus();
                String cheatCode = cheatCodeTextField.getText();
                String[] mots = cheatCode.split(" ");

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

                    case "$GET-SEED":
                        cheatCodeTextField.setText(mainGameWindow.seed);
                        break;

                    case "$FILL-INVENTORY":
                        fillInventory();
                        break;
                    case "$EMPTY-INVENTORY":
                        emptyInventory();
                        break;

                    case "$MOTHERLOAD":
                        motherLoad();
                        break;
                    case "$LOAD-NEARBY-CHUNKS":
                        loadNearbyChunks();
                        break;

                    case "$TP":
                        tp(mots[1]);
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
        });

        JButton exitButton = new JButton("Exit");

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

    public void open(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        frame.setVisible(true);
        cheatCodeTextField.requestFocus();
    }

    public void close(){
        cheatCodeTextField.setText("");
        frame.setVisible(false);
    }


    private void setMoney(String value){
        try {         
            int number = Integer.parseInt(value);
            mainGameWindow.player.money = number;
            mainGameWindow.player.saveMoney();
            refreshDisplay = true;

        } catch (NumberFormatException e) {
            
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
            mainGameWindow.changeChunk("TP");
            ////mainGameWindow.player.saveChunk();
            //refresh = true;

        } catch (NumberFormatException e) {

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
            ////mainGameWindow.player.saveChunk();
            //refresh = true;

        } catch (NumberFormatException e) {

        }
    }

    private void setStep(String value){
        try {
            int step = Integer.parseInt(value);
 
            mainGameWindow.player.step = step;
            mainGameWindow.player.saveStep();
        } catch (NumberFormatException e) {

        }
    }
    private void setSpeed(String value){
        try {
            int speed = Integer.parseInt(value);
 
            mainGameWindow.player.speed = speed;
            mainGameWindow.player.saveSpeed();
        } catch (NumberFormatException e) {

        }
    }

    private void devMode(){
        setSpeed("4");
        setMoney("100000");
    }

    private void fillInventory(){
        mainGameWindow.player.inventory.fillInventory();
        mainGameWindow.player.inventory.saveAll();
    }

    private void emptyInventory(){
        mainGameWindow.player.inventory.emptyInventory();
        mainGameWindow.player.inventory.saveAll();
    }

    private void motherLoad(){
        long newValue = mainGameWindow.player.money + 50000;
        setMoney(String.valueOf(newValue));
    }

    private void loadNearbyChunks(){
        int n = 10;
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
    }
}
