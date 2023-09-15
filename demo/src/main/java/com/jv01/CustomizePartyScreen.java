package com.jv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.awt.event.KeyListener;

class CustomizePartyScreen extends JPanel {
    public static Save save = new Save();
    public Document doc;

    public String gameName;

    public int currentParty = 1;

    private static final int BOXE_SIZE = 800;

    public JFrame frame = new JFrame("Customize party screen");

    public JPanel panel;

    public JTextField nameTextField = new JTextField(20);

    public CustomizePartyScreen(String gameName) {
        this.gameName = gameName;

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(BOXE_SIZE, BOXE_SIZE);
        
        ChangeComponents(currentParty);

        frame.add(this);
        frame.setVisible(true);
    }

    public void ChangeComponents(int idParty){
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(BOXE_SIZE, BOXE_SIZE);

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        doc = save.getDocumentXml(gameName,"parties");
        Element element = save.getElementById(doc, "party", String.valueOf(idParty));
        

        Map<String, List<String>> party = save.getAllChildsFromElement(element);

        String partyName = party.get("partyName").get(0);

        JLabel partyNameLabel = new JLabel(partyName);
        panel.add(partyNameLabel, constraints);

        String conservatismScore = party.get("conservatismScore").get(0);

        JLabel partyconservatismScore = new JLabel("conservatismScore: " + conservatismScore);
        panel.add(partyconservatismScore, constraints);


        JButton nextParty = new JButton(">");
        panel.add(nextParty, constraints);

        nextParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               changeParty(1);
            }
        });

        JButton previewParty = new JButton("<");
        panel.add(previewParty, constraints);

        previewParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               changeParty(-1);
            }
        });


        /*JLabel titleLabel = new JLabel("Entrez le nom de la nouvelle partie:");
        panel.add(titleLabel, constraints);

        panel.add(nameTextField, constraints);

        JButton chooseSeedButton = new JButton("Choisir une seed");
        panel.add(chooseSeedButton, constraints);

        chooseSeedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });*/


        frame.add(panel);
        frame.setVisible(true);  
        frame.setLocationRelativeTo(null);
    }

    public void changeParty(int direction){
        if(direction > 0){
            if(currentParty < 5){
                currentParty++;
            }else{
                currentParty = 1;
            }
        }else{
            if(currentParty > 1){
                currentParty--;
            }else{
                currentParty = 5;
            }
        }

        ChangeComponents(currentParty);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CustomizePartyScreen customizePartyScreen = new CustomizePartyScreen("testParty!dontDelete");
            }
        });
    }
}
