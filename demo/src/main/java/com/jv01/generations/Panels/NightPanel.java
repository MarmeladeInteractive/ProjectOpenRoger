package com.jv01.generations.Panels;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jv01.screens.GameWindowsSize;

import java.awt.*;

import com.jv01.fonctionals.Time;
import com.jv01.generations.MainGameWindow;

public class NightPanel {
    public MainGameWindow mainGameWindow;
    public JFrame frame;
    public JPanel panel;

    public GameWindowsSize GWS = new GameWindowsSize(true);
    public Time date;

    public NightPanel(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.frame = mainGameWindow.frame;
    }

    public void createNightPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        panel.setOpaque(true);
        panel.setBackground(new Color(0, 0, 0, mainGameWindow.date.nightFilterOpacity));
        frame.add(panel);
    }

    public void updateNight(boolean isInsideBuilding, Time date){
        if(isInsideBuilding){
            panel.setBackground(new Color(0, 0, 0, 0));
        }else{
            panel.setBackground(new Color(0, 0, 0, date.nightFilterOpacity));
        }
    }

    public void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
}
