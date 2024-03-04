package com.jv01.screens.Menus;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jv01.generations.MainGameWindow;

public class SelectionWheel {
    public JPanel backPanel;
    public JLabel selectionWheelLabel;

    public SelectionWheel(MainGameWindow mainGameWindow){
        this.backPanel = mainGameWindow.backgroundPanel.panel;
    }

    public void openSelectionWheel(int segments){
        SelectionWheelPanel selectionWheelPanel = new SelectionWheelPanel(segments);

        this.backPanel.setLayout(new java.awt.BorderLayout());
        this.backPanel.add(selectionWheelPanel, java.awt.BorderLayout.CENTER);
        this.backPanel.revalidate();
        this.backPanel.repaint();

    }

}
