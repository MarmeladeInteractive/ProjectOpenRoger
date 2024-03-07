package com.jv01.generations.Panels.Menus;

import java.awt.Color;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jv01.generations.MainGameWindow;
import com.jv01.screens.GameWindowsSize;

public class SelectionWheel {
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
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        panel.setOpaque(true);
        panel.setBackground(new Color(0, 0, 0, 0));
        frame.add(panel);
    }

    public void openSelectionWheel(int segments) {
        if(!isOpen){
            isOpen = true;
            SelectionWheelPanel selectionWheelPanel = new SelectionWheelPanel(segments);
            selectionWheelPanel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);

            System.out.println("null");
            panel.add(selectionWheelPanel);
            panel.revalidate();
            panel.repaint();
        }
    }
    
    public void clearSelectionWheelPanel(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();  
        isOpen = false;
    }
    
    public void focusOnMainFrame(){
        mainGameWindow.frame.requestFocus();
    }
}
