package com.jv01.miniGames.games.roulette;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Tokens {
    public Roulette roulette;

    public JLabel[] simpleNumberBet = new JLabel[36];
    public JLabel[] squareNumberBet = new JLabel[22];
    public JLabel[] double01NumberBet = new JLabel[33];
    public JLabel[] double02NumberBet = new JLabel[24];
    public JLabel[] twoToOneNumberBet = new JLabel[3];
    public JLabel[] thirdNumberBet = new JLabel[3];
    public JLabel[] otherNumberBet = new JLabel[6];
    public JLabel number0Bet = new JLabel();

    public Tokens(Roulette roulette){
        this.roulette = roulette;

        addTokens();
    }

    private void addTokens(){
        int in = 0;
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 3; j++){
                JLabel label = new JLabel();
                label.setBounds((int)(105.0 + j*1.0 * 62*1.0), (int)(75.0 + 39.2*1.0 * i), 50, 50);
                label.setVisible(false);
                roulette.tablePanel.add(label);
                simpleNumberBet[in]=label;
                in++;
            } 
        }

        in = 0;
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 2; j++){
                JLabel label = new JLabel();
                label.setBounds((int)(137.0 + j*1.0 * 62*1.0), (int)(92.0 + 39.2*1.0 * i), 50, 50);
                label.setVisible(false);
                roulette.tablePanel.add(label);
                squareNumberBet[in]=label;
                in++;
            } 
        }

        in = 0;
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < 3; j++){
                JLabel label = new JLabel();
                label.setBounds((int)(105.0 + j*1.0 * 62*1.0), (int)(95.0 + 39.2*1.0 * i), 50, 50);
                label.setVisible(false);
                roulette.tablePanel.add(label);
                double01NumberBet[in]=label;
                in++;
            } 
        }

        in = 0;
        for(int i = 0; i < 12; i++){
            for(int j = 0; j < 2; j++){
                JLabel label = new JLabel();
                label.setBounds((int)(137.0 + j*1.0 * 62*1.0), (int)(75.0 + 39.2*1.0 * i), 50, 50);
                label.setVisible(false);
                roulette.tablePanel.add(label);
                double02NumberBet[in]=label;
                in++;
            } 
        }

        in = 0;
        for(int i = 0; i < 1; i++){
            for(int j = 0; j < 3; j++){
                JLabel label = new JLabel();
                label.setBounds((int)(105.0 + j*1.0 * 62*1.0), (int)(545.0 + 39.2*1.0 * i), 50, 50);
                label.setVisible(false);
                roulette.tablePanel.add(label);
                twoToOneNumberBet[in]=label;
                in++;
            } 
        }

        in = 0;
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 1; j++){
                JLabel label = new JLabel();
                label.setBounds((int)(53.0 + j*1.0 * 62*1.0), (int)(135.0 + 156*1.0 * i), 50, 50);
                label.setVisible(false);
                roulette.tablePanel.add(label);
                thirdNumberBet[in]=label;
                in++;
            } 
        }

        in = 0;
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 1; j++){
                JLabel label = new JLabel();
                label.setBounds((int)(7.0 + j*1.0 * 62*1.0), (int)(95.0 + 78*1.0 * i), 50, 50);
                label.setVisible(false);
                roulette.tablePanel.add(label);
                otherNumberBet[in]=label;
                in++;
            } 
        }

        JLabel label = new JLabel();
        label.setBounds((int)(167.0 ), (int)(20.0 ), 50, 50);
        label.setVisible(false);
        roulette.tablePanel.add(label);
        number0Bet=label;     
    }


    private static BufferedImage createCircleImage(int width, int height, Color color, String text) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        int circleDiameter = Math.min(width, height) - 20;
        int x = (width - circleDiameter) / 2;
        int y = (height - circleDiameter) / 2;

        Ellipse2D.Double circle = new Ellipse2D.Double(x, y, circleDiameter, circleDiameter);

        g2d.setColor(color);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);

        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textX = (width - fontMetrics.stringWidth(text)) / 2;
        int textY = (height - fontMetrics.getHeight()) / 2 + fontMetrics.getAscent();
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, textX, textY);

        g2d.dispose();

        return image;
    }

    public void changeTokenValue(JLabel jl, int value){
        jl.setIcon(new ImageIcon(createCircleImage(50, 50, new Color(0,104,255),String.valueOf(value))));
        if(value>0){
            jl.setVisible(true);
        }else{
            jl.setVisible(false);
        }
    }

}
