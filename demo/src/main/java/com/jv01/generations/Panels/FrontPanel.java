package com.jv01.generations.Panels;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jv01.screens.GameWindowsSize;

import java.awt.*;

import com.jv01.generations.MainGameWindow;

public class FrontPanel {
    public MainGameWindow mainGameWindow;
    public JFrame frame;
    public JPanel panel;

    private JLabel coordinatesLabel;
    private JLabel moneyLabel;
    private JLabel dateLabel;

    public GameWindowsSize GWS = new GameWindowsSize(true);

    public FrontPanel(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.frame = mainGameWindow.frame;
    }

    public void createFrontPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 0, 0, 0));
        frame.add(panel);

        addCoordinatesLabel();
        addMoneyLabel();
        addDateLabel();
    }

    public void addCoordinatesLabel() {
        coordinatesLabel = new JLabel(
            "<html>"+
                "Chunk: (" + 0 + ", " + 0 + ")<br>"+
                "Position: ("+0+", "+0+")"+
            "</html>"
            );
        coordinatesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        coordinatesLabel.setForeground(Color.black);

        int xCoord = 10;
        int yCoord = 10;

        coordinatesLabel.setBounds(xCoord, yCoord, 200, 40);

        panel.add(coordinatesLabel);
    }
    public void updatePositionTextLabels(long[] currentChunk, long[] playerPosition){
        coordinatesLabel.setText(
            "<html>"+
                "Chunk: (" + currentChunk[0] + ", " + currentChunk[1] + ")<br>"+
                "Position: ("+(playerPosition[0])+", "+(playerPosition[1])+")"+
            "</html>"
        );
    }

    public void addMoneyLabel(){
        moneyLabel = new JLabel(
            "<html>"+
                "Argent: 1000€"+
            "</html>"
            );
        moneyLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        moneyLabel.setForeground(Color.black);

        int xCoord = 10;
        int yCoord = 10+50;

        moneyLabel.setBounds(xCoord, yCoord, 200, 40);

        panel.add(moneyLabel);
    }
    public void updateMoneyTextLabels(long money){
        moneyLabel.setText(
            "<html>"+
                "Argent: " + money + '€'+
            "</html>"
        );
    }

    public void addDateLabel(){
        dateLabel = new JLabel(
            "<html>"+
                "Date: 12/12/2022 12h22"+
            "</html>"
            );
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dateLabel.setForeground(Color.black);

        int xCoord = 10;
        int yCoord = 10+100;

        dateLabel.setBounds(xCoord, yCoord, 300, 40);

        panel.add(dateLabel);
    }
    public void updateDateTextLabels(String date, String hour){
        dateLabel.setText(
            "<html>"+
                "Date: " + date + " " + hour+
            "</html>"
        );
    }

    public void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
}
