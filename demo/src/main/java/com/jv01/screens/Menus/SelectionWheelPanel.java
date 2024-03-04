package com.jv01.screens.Menus;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.Dimension;

class SelectionWheelPanel extends JPanel {
    private int segments;

    public SelectionWheelPanel(int segments) {
        this.segments = segments;
        this.setPreferredSize(new Dimension(300, 300));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        int width = getWidth();
        int height = getHeight();
        int x = (width - 200) / 2;
        int y = (height - 200) / 2;

        double angle = 360.0 / segments;

        for (int i = 0; i < segments; i++) {
            g2d.setColor(new Color((int)(Math.random() * 0x1000000)));
            g2d.fillArc(x, y, 200, 200, (int)(i * angle), (int)angle);
        }

        g2d.dispose();
    }
}

