package com.jv01.generations.Panels;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.jv01.screens.GameWindowsSize;
import com.jv01.generations.MainGameWindow;

public class BackgroundPanel {
    public MainGameWindow mainGameWindow;
    public JFrame frame;
    public JPanel panel;

    public GameWindowsSize GWS;

    public BackgroundPanel(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.frame = mainGameWindow.frame;
    }

    public void createBackgroundPanel(GameWindowsSize GWS){
        this.GWS = GWS;
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage backgroundImage = loadImage(mainGameWindow.chunk.backPic); // Charger l'image depuis le chemin du fichier
                Graphics2D g2d = (Graphics2D) g.create();
                TexturePaint paint = new TexturePaint(backgroundImage, new Rectangle(0, 0, backgroundImage.getWidth(), backgroundImage.getHeight()));
                g2d.setPaint(paint);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight); 

        frame.add(panel);
    }

    public void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
    
    // Méthode pour charger une image depuis un fichier
    public BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
