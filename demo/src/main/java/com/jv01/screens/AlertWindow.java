package com.jv01.screens;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AlertWindow {

    private JPanel backgroundPanel; 
    private int boxSize = 800;

    private JLabel msgLabel;
    private int msgBoxSizeX = 300;
    private int msgBoxSizeY = 80;

    public AlertWindow(JPanel backgroundPanel, int boxSize){
        this.backgroundPanel = backgroundPanel;
        this.boxSize = boxSize;
        addMsgLabel();
    }

    public void showTimedAlert(String message, int delayMillis) {
        msgLabel.setText(
            "<html>"+
                message+
            "</html>"
        );
        msgLabel.setVisible(true);

        Timer timer = new Timer(delayMillis, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                msgLabel.setText(
                    "<html>"+
                        ""+
                    "</html>"
                );
                msgLabel.setVisible(false);
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void addMsgLabel(){
        msgLabel = new JLabel();
        msgLabel.setOpaque(true);
        msgLabel.setForeground(new Color(0, 0, 0));
        msgLabel.setBackground(new Color(255, 150, 150, 155));

        Border bord= BorderFactory.createLineBorder(Color.red,2);
        msgLabel.setBorder(bord);

        int labelX = (boxSize - msgBoxSizeX) / 2;
        int labelY = (boxSize - msgBoxSizeY) / 2;

        msgLabel.setBounds(labelX, labelY , msgBoxSizeX, msgBoxSizeY);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        msgLabel.setFont(labelFont);
        
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        msgLabel.setVerticalAlignment(SwingConstants.CENTER);

        backgroundPanel.add(msgLabel);
        msgLabel.setVisible(false);
    }

}
