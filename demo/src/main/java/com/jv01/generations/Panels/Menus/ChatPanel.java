package com.jv01.generations.Panels.Menus;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;
import com.jv01.screens.GameWindowsSize;

import java.awt.*;
import java.util.AbstractMap.SimpleEntry;
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

    public List<SimpleEntry<String, Color>> historyChat = new ArrayList<>();

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
        historyChat.clear();
        isOpen = false;
    }

    public void openChatPanel(String playerName, String playerText, String npcName, String npcText) {
        panel.removeAll();

        int baseY = GWS.gameWindowHeight - 50;
        int labelHeight = 36;
        int labelWidth = Math.max(GWS.gameWindowWidth/2, 400);
    
        historyChat.add(0, new SimpleEntry<>(playerName + ": " + playerText, Color.WHITE));
        historyChat.add(0, new SimpleEntry<>(npcName + ": " + npcText, Color.YELLOW));
        
        JTextArea TextAreaMsg;
        int numberOfMessagesToShow = Math.min(historyChat.size(), 6);
        for  (int i = 0; i < numberOfMessagesToShow ; i++) {
            SimpleEntry<String, Color> entry = historyChat.get(i);
            TextAreaMsg = createStyledLabel(entry.getKey(), baseY - labelHeight*i, labelWidth, labelHeight, entry.getValue(), new Color(0, 0, 0, 180));
            panel.add(TextAreaMsg);
        }

        panel.revalidate();
        panel.repaint();
    
        isOpen = true;
    }

    private JTextArea createStyledLabel(String text, int y, int width, int height, Color textColor, Color backgroundColor) {
        JTextArea textArea = new JTextArea(text);
        textArea.setForeground(textColor);
        textArea.setFont(new Font("Arial", Font.BOLD, 14));
        textArea.setBounds((GWS.gameWindowWidth - width) / 2, y, width, height);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setOpaque(true);
        textArea.setBackground(backgroundColor);

        textArea.setMargin(new Insets(1, 10, 0, 10));

        return textArea;
    }
    
    

    public void focusOnMainFrame(){
        mainGameWindow.frame.requestFocus();
    }
    
}
