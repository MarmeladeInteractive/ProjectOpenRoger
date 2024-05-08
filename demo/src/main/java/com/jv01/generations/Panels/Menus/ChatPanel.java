package com.jv01.generations.Panels.Menus;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;
import com.jv01.screens.GameWindowsSize;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatPanel {
    
    public Save save = new Save();
    public MainGameWindow mainGameWindow;
    public JPanel panel;
    public JFrame frame;

    public GameWindowsSize GWS = new GameWindowsSize(true);

    public boolean isOpen = false;

    public ChatPanel(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.frame = mainGameWindow.frame;
    }

    public void createChatPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 0, 0, 0));
        frame.add(panel);
    }
    public void clearChatPanel(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();  
        isOpen = false;
    }

    public void openChatPanel(String playerName, String playerText, String npcName, String npcText) {
        panel.removeAll();

        int baseY = GWS.gameWindowHeight - 150;
        int labelHeight = 30;
        int labelWidth = GWS.gameWindowWidth - 40; 
        int spaceBetween = 40;
    
        JLabel playerLabel = createStyledLabel(playerName + ": " + playerText, baseY - spaceBetween - labelHeight, labelWidth, labelHeight, Color.WHITE, new Color(0, 0, 0, 180));
        
        JLabel npcLabel = createStyledLabel(npcName + ": " + npcText, baseY, labelWidth, labelHeight, Color.YELLOW, new Color(0, 0, 0, 180));
        
        panel.add(npcLabel);
        panel.add(playerLabel);

        panel.revalidate();
        panel.repaint();
    
        isOpen = true;
    }

    private JLabel createStyledLabel(String text, int y, int width, int height, Color textColor, Color backgroundColor) {
        JLabel label = new JLabel(text);
        label.setForeground(textColor);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBounds((GWS.gameWindowWidth - width) / 2, y, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(backgroundColor);
        return label;
    }
    

    public void focusOnMainFrame(){
        mainGameWindow.frame.requestFocus();
    }
    
}
