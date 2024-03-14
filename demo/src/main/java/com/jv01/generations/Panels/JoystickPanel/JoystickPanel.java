package com.jv01.generations.Panels.JoystickPanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.jv01.screens.GameWindowsSize;
import com.jv01.screens.Windows.GameMap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;

public class JoystickPanel {
    public Save save = new Save();
    public MainGameWindow mainGameWindow;
    public String gameName;
    public JPanel panel;
    public JFrame frame;

    public Document doc;
    public Element element;

    public GameWindowsSize GWS = new GameWindowsSize(true);

    private Color joystickColor = Color.BLACK;
    private Color joystickCenterColor = Color.BLACK;
    private Color joystickBorderColor = Color.BLACK;

    private int joystickAlpha = 200;
    private int joystickBorderAlpha = 255;
    private int joystickCenterAlpha = 255;

    public int diagonalSensitivity = 25;

    private int centerCircleDiameter;
    private int borderCircleDiameter;
    public int joystickDiameter = 100;

    private JPanel joystick;

    public int mouse1stClickX;
    public int mouse1stClickY;

    public JoystickPanel(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.gameName = mainGameWindow.gameName;
        this.frame = mainGameWindow.frame;
    }

    public void createJoystickPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 0, 0, 0));
        frame.add(panel);

        if(mainGameWindow.player.inputsManager.joystickIsVisible){
            addTheWholeJoystick(mainGameWindow.player.inputsManager.mouse1stClickX,mainGameWindow.player.inputsManager.mouse1stClickY,mainGameWindow.player.inputsManager.diagonalSensitivity);
            updateJoystickLocation(mainGameWindow.player.inputsManager.currentMouseLocationX,mainGameWindow.player.inputsManager.currentMouseLocationY);
        }
    }
    public void clearJoystickPanel(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();  
    }

    public void addTheWholeJoystick(int x, int y, int newDiagonalSensitivity) {
        addJoystick(x, y, newDiagonalSensitivity);
        addJoystickBorders(x, y, newDiagonalSensitivity);
    }

    public void addJoystick(int x, int y, int newDiagonalSensitivity) {
        centerCircleDiameter = diagonalSensitivity*2;
        borderCircleDiameter = joystickDiameter*2;
        diagonalSensitivity = newDiagonalSensitivity;

        mouse1stClickX = x;
        mouse1stClickY = y;
 
        joystick = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color newColor = new Color(joystickColor.getRed(), joystickColor.getGreen(), joystickColor.getBlue(), joystickAlpha);
                g2d.setColor(newColor);
                
                Stroke oldStroke = g2d.getStroke();
                g2d.setStroke(new BasicStroke(3));
                
                g2d.fillOval(0, 0, joystickDiameter, joystickDiameter);
                
                g2d.setStroke(oldStroke);
            }
    
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }
        };

        joystick.setBounds(x - joystickDiameter / 2, y - joystickDiameter / 2, joystickDiameter, joystickDiameter);
        joystick.setOpaque(false);

        panel.add(joystick); 

        panel.revalidate();
        panel.repaint();
    }

    public void addJoystickBorders(int x, int y, int newDiagonalSensitivity) {
        centerCircleDiameter = diagonalSensitivity*2;
        borderCircleDiameter = joystickDiameter*2;
        diagonalSensitivity = newDiagonalSensitivity;

        mouse1stClickX = x;
        mouse1stClickY = y;

        JPanel centerCirclePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color newColor = new Color(joystickCenterColor.getRed(), joystickCenterColor.getGreen(), joystickCenterColor.getBlue(), joystickCenterAlpha);
                g2d.setColor(newColor);
                
                Stroke oldStroke = g2d.getStroke();
                g2d.setStroke(new BasicStroke(3));
                
                g2d.drawOval(0, 0, centerCircleDiameter, centerCircleDiameter);
                
                g2d.setStroke(oldStroke);
            }
    
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }
        };
        JPanel borderCirclePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color newColor = new Color(joystickBorderColor.getRed(), joystickBorderColor.getGreen(), joystickBorderColor.getBlue(), joystickBorderAlpha);
                g2d.setColor(newColor);
                
                Stroke oldStroke = g2d.getStroke();
                g2d.setStroke(new BasicStroke(3));
                
                g2d.drawOval(0, 0, borderCircleDiameter, borderCircleDiameter);
                
                g2d.setStroke(oldStroke);
            }
    
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(100, 100);
            }
        };
    
        centerCirclePanel.setBounds(x - centerCircleDiameter / 2, y - centerCircleDiameter / 2, centerCircleDiameter, centerCircleDiameter);
        centerCirclePanel.setOpaque(false);

        borderCirclePanel.setBounds(x - borderCircleDiameter / 2, y - borderCircleDiameter / 2, borderCircleDiameter, borderCircleDiameter);
        borderCirclePanel.setOpaque(false);

        panel.add(centerCirclePanel);
        panel.add(borderCirclePanel);  

        panel.revalidate();
        panel.repaint();
    }

    public void updateJoystickLocation(int x, int y){
        int distanceMax = joystickDiameter + 10;
        int deltaX = x - mouse1stClickX;
        int deltaY = y - mouse1stClickY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    
        if(distance > distanceMax){
            double ratio = distanceMax / distance;
            x = mouse1stClickX + (int)(deltaX * ratio);
            y = mouse1stClickY + (int)(deltaY * ratio);
        }

        joystick.setBounds(x - joystickDiameter / 2, y - joystickDiameter / 2, joystickDiameter, joystickDiameter);
    }
}
