package com.jv01;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;


public class Objects {
    public int[] position = {0,0};
    public String imagePath;
    public int[] size;
    public int blocked;
    public int[][] restrictedAreas;
    public JPanel backgroundPanel;

    public JLabel objectLabel;

    public Objects(int x, int y, int[] size, String imagePath, int blocked, JPanel backgroundPanel){
        this.position[0] = x;
        this.position[1] = y;
        this.size = size;
        this.imagePath = imagePath;
        this.blocked = blocked;
        this.backgroundPanel = backgroundPanel;

        addCustomObject();     
    }


    public JLabel addCustomObject() {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image resizedImage = originalImage.getScaledInstance(size[0], size[1], Image.SCALE_SMOOTH);
    
            objectLabel = new JLabel(new ImageIcon(resizedImage));
            objectLabel.setBounds(position[0] - (size[0]/2), position[1] - (size[1]/2), size[0], size[1]);
            backgroundPanel.add(objectLabel);
    
            backgroundPanel.revalidate();
            backgroundPanel.repaint();

            if(blocked == 1){
                restrictedAreas = (new int[][] { 
                    {
                        position[0] - (size[0]/2), 
                        position[1] - (size[1]/2)
                    }, 
                    {
                        position[0] + (size[0]/2), 
                        position[1] + (size[1]/2)
                    } 
                });
            }

            return objectLabel;
        } catch (IOException e) {
            System.err.println("Error loading object image: " + e.getMessage());
            return null;
        }
    }
}
