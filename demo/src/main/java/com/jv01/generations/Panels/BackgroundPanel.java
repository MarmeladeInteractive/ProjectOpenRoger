package com.jv01.generations.Panels;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jv01.screens.GameWindowsSize;

import java.awt.*;

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
                ImageIcon backgroundImage = new ImageIcon(mainGameWindow.chunk.backPic);
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight); 

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        panel.setLocation((screenWidth - GWS.gameWindowWidth)/2,(screenHeight - GWS.gameWindowHeight)/2);

        frame.add(panel);
    }
}
