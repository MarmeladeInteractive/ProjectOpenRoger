package com.jv01.generations.Panels.InteractiveListPanel;

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

public class InteractiveListPanel {
    public Save save = new Save();
    public MainGameWindow mainGameWindow;
    public JPanel panel;
    public JFrame frame;

    public GameWindowsSize GWS = new GameWindowsSize(true);

    private JList<List<String>> interactiveJList;

    public boolean isOpen = false;
    public boolean isSelectedValue = false;
    public List<String> selectedValue;

    public InteractiveListPanel(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.frame = mainGameWindow.frame;
    }

    public void createInteractiveListPanel(){
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 0, 0, 0));
        frame.add(panel);
    }
    public void clearInteractiveListPanel(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();  
        isOpen = false;
    }

    public void openInteractiveList(DefaultListModel<List<String>> listModel) {
        if(listModel.size()>0){
            isSelectedValue = false;
            isOpen = true;
            interactiveJList = new JList<>(listModel);
            interactiveJList.setCellRenderer(new My2DListCellRenderer());
            interactiveJList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedIndex = interactiveJList.getSelectedIndex();
                        if (selectedIndex != -1) {
                            selectedValue = interactiveJList.getModel().getElementAt(selectedIndex);
                            isSelectedValue = true;
                            focusOnMainFrame();
                        }
                    }
                }
            });
            

            JScrollPane scrollPane = new JScrollPane(interactiveJList);
            scrollPane.setBounds(GWS.gameWindowWidth/2 - 100,10, 250, listModel.size()*50+10);
            scrollPane.setOpaque(false);
            scrollPane.getViewport().setOpaque(false);

            panel.add(scrollPane);
            panel.revalidate();
            panel.repaint();
        }
    }

    public void focusOnMainFrame(){
        mainGameWindow.frame.requestFocus();
    }

    class My2DListCellRenderer extends DefaultListCellRenderer {
        private Border BORDER = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        private int CELL_HEIGHT = 50;
    
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            @SuppressWarnings("unchecked")
            List<String> row = (List<String>) value;
            String text = row.get(0);
            JLabel renderer = (JLabel) super.getListCellRendererComponent(list, text, index, isSelected, cellHasFocus);
            renderer.setBorder(BorderFactory.createCompoundBorder(BORDER, BorderFactory.createLineBorder(Color.BLACK)));
            renderer.setPreferredSize(new Dimension(renderer.getPreferredSize().width, CELL_HEIGHT));
            return renderer;
        }
    }
    
}
