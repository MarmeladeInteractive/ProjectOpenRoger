package com.jv01.screens;

import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;
import com.jv01.screens.Windows.GameMap;
import com.jv01.screens.Windows.Inventory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalTime;

public class ShowNewWindow {
    public Save save = new Save();
    public GameWindowsSize GWS = new GameWindowsSize(true);

    public int boxSize;

    private JPanel panel;
    public JPanel newWindowPanel = new JPanel();

    private String idWindow = "";

    public MainGameWindow mainGameWindow;

    private JButton closeWindowButton;

    public JTextField setFocus = new JTextField(1);

    public ShowNewWindow(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;

        this.boxSize = GWS.boxSize;
        mainGameWindow.frontPanel.clearPanel();
        this.panel = mainGameWindow.frontPanel.panel;
        
        this.idWindow = mainGameWindow.newWindowId;

        focusOnNewWindow();

        addNewWindow();

        dispayNewWindow();

        panel.add(newWindowPanel);
    }

    private void addNewWindow() {
        panel.setLayout(null);

        closeWindowButton = new JButton("X");
        closeWindowButton.setBounds(boxSize - 50 - 10, 10, 50, 50);

        for (ActionListener al : closeWindowButton.getActionListeners()) {
            closeWindowButton.removeActionListener(al);
        }   
        closeWindowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainGameWindow.showMainGameWindow();
            }
        });

        panel.add(closeWindowButton);
        panel.setComponentZOrder(closeWindowButton, 0);
    
        newWindowPanel.setLayout(null);
        newWindowPanel.setBounds(0, 0, boxSize, boxSize);
    }

    private void dispayNewWindow(){
        mainGameWindow.player.canWalk = false;
        switch (idWindow) {
            case "Inventory":
                new Inventory(this);
                break;
            case "Map":
                //new GameMap(this);
                break;
        
            default:
                
                break;
        }
    }

    public void setCloseButtonVisibility(boolean v){
        closeWindowButton.setVisible(v);
    }

    private void focusOnNewWindow(){
        setFocus.setFocusTraversalKeysEnabled(false);
        setFocus.setMinimumSize(new Dimension(1, 1));
        setFocus.setPreferredSize(new Dimension(1, 1));

        newWindowPanel.add(setFocus);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setFocus.requestFocusInWindow();
            }
        });
    }
}
