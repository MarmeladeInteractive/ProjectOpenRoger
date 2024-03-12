package com.jv01.generations.Panels.Menus;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Arc2D;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jv01.generations.MainGameWindow;
import com.jv01.screens.GameWindowsSize;

public class SelectionWheel extends JPanel {
    private List<String> options = new ArrayList<>();
    private int centreX = 0;
    private int centreY = 0;
    public MainGameWindow mainGameWindow;
    public JFrame frame;
    public JPanel panel;
    public JPanel backPanel;
    public JLabel selectionWheelLabel;

    public boolean isOpen = false;

    public GameWindowsSize GWS = new GameWindowsSize(true);

    public SelectionWheel(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.frame = mainGameWindow.frame;
    }

    public void createSelectionWheelPanel(){
        setLayout(null);
        setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        frame.add(this);
    }

    public void openSelectionWheel(int x, int y, List<String> options) {
        final int rayon = 200; 

        int ajustedX = x;
        int ajustedY = y;
    
        if (x + rayon > GWS.gameWindowWidth) {
            ajustedX = GWS.gameWindowWidth - rayon;
        }
        if (y + rayon > GWS.gameWindowHeight) {
            ajustedY = GWS.gameWindowHeight - rayon;
        }
    
        if (x - rayon < 0) {
            ajustedX = rayon;
        }
        if (y - rayon < 0) {
            ajustedY = rayon;
        }

        this.centreX = ajustedX;
        this.centreY = ajustedY;
        this.options.clear();
        this.options.addAll(options);
        this.isOpen = true;
        repaint();
    }
    
    
    public void clearSelectionWheel() {
        this.isOpen = false;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isOpen || options.isEmpty()) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g.create();
        final int rayon = 200;
        final int diametre = rayon * 2;
        final double angleSection = 360.0 / options.size();

        Area wheelArea = new Area();

        for (int i = 0; i < options.size(); i++) {
            double startAngle = i * angleSection;
            Arc2D.Double segment = new Arc2D.Double(centreX - rayon, centreY - rayon, diametre, diametre, startAngle, angleSection, Arc2D.PIE);
            wheelArea.add(new Area(segment));
        }

        final int rayonVideCentral = 100;
        Ellipse2D.Double centralHole = new Ellipse2D.Double(centreX - rayonVideCentral, centreY - rayonVideCentral, rayonVideCentral * 2, rayonVideCentral * 2);
        wheelArea.subtract(new Area(centralHole));

        g2d.setClip(wheelArea);
        g2d.setColor(new Color(255,255,255,122));
        g2d.fill(wheelArea);

        for (int i = 0; i < options.size(); i++) {
            String text = options.get(i);
            FontMetrics metrics = g.getFontMetrics();
            double textAngle = Math.toRadians((i * angleSection) + angleSection / 2);
            int textRadius = rayon / 2 + (rayon / 4); 
            int textX = (int) (centreX + Math.cos(textAngle) * textRadius) - metrics.stringWidth(text) / 2;
            int textY = (int) (centreY + Math.sin(textAngle) * textRadius) + (metrics.getAscent() - metrics.getDescent()) / 2;

            g2d.setColor(Color.BLACK);
            g2d.drawString(text, textX, textY);
        }

        g2d.dispose();
    }
}