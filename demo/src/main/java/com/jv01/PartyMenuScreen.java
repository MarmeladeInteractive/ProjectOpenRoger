package com.jv01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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

class PartyMenuScreen extends JPanel {
    public static Save save = new Save();
    public Document doc;

    public String gameName;

    public int currentParty;

    private static final int BOXE_SIZE = 800;

    public JFrame frame = new JFrame("Party menu");

    public JPanel panel;
    
    public JTextField ageTargetTextField = new JTextField(20);
    public JTextField wealthTargetTextField = new JTextField(20); 

    public String membersTargetedAge;
    public String membersTargetedWealth;

    public String conservatismScore;
    public String nationalismScore;
    public String ecologismScore;
    public String feminismScore;
    public String anarchismScore;
    public String populismScore;

    public JSlider conservatismSlider;
    public JSlider nationalismSlider;
    public JSlider ecologismSlider;
    public JSlider feminismSlider;
    public JSlider anarchismSlider;
    public JSlider populismSlider;

    public PartyMenuScreen(String gameName,Player player) {
        this.gameName = gameName;

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(BOXE_SIZE, BOXE_SIZE);

        ChangeComponents(player.partyID);

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
        
        String members = party.get("members").get(0);
        JLabel membersLabel = new JLabel("Members's number: " + members);
        panel.add(membersLabel, constraints);

        membersTargetedAge = party.get("membersTargetedAge").get(0);
        JLabel membersTargetedAgeLabel = new JLabel("Targeted Age: " + membersTargetedAge);
        panel.add(membersTargetedAgeLabel, constraints);

        membersTargetedWealth = party.get("membersTargetedWealth").get(0);
        JLabel membersTargetedWealthLabel = new JLabel("Targeted Wealth: " + membersTargetedWealth);
        panel.add(membersTargetedWealthLabel, constraints);

        String wealth = party.get("wealth").get(0);
        JLabel wealthLabel = new JLabel("Party Wealth: " + wealth);
        panel.add(wealthLabel, constraints);
        
        conservatismScore = party.get("conservatismScore").get(0);
        JLabel partyconservatismScore = new JLabel("Conservatism Score: " + conservatismScore);
        panel.add(partyconservatismScore, constraints);
        conservatismSlider = createImmutableScoreSlider("Conservatism", conservatismScore, constraints, partyconservatismScore);

        nationalismScore = party.get("nationalismScore").get(0);
        JLabel nationalismScoreLabel = new JLabel("Nationalism Score: " + nationalismScore);
        panel.add(nationalismScoreLabel, constraints);
        nationalismSlider = createImmutableScoreSlider("Nationalism", nationalismScore, constraints, nationalismScoreLabel);

        ecologismScore = party.get("ecologismScore").get(0);
        JLabel ecologismScoreLabel = new JLabel("Ecologism Score: " + ecologismScore);
        panel.add(ecologismScoreLabel, constraints);
        ecologismSlider = createImmutableScoreSlider("Ecologism", ecologismScore, constraints, ecologismScoreLabel);
        
        feminismScore = party.get("feminismScore").get(0);
        JLabel feminismScoreLabel = new JLabel("Feminism Score: " + feminismScore);
        panel.add(feminismScoreLabel, constraints);        
        feminismSlider = createImmutableScoreSlider("Feminism", feminismScore, constraints, feminismScoreLabel);

        anarchismScore = party.get("anarchismScore").get(0);
        JLabel anarchismScoreLabel = new JLabel("Anarchism Score: " + anarchismScore);
        panel.add(anarchismScoreLabel, constraints);      
        anarchismSlider = createImmutableScoreSlider("Anarchism", anarchismScore, constraints, anarchismScoreLabel);
  
        populismScore = party.get("populismScore").get(0);
        JLabel populismScoreLabel = new JLabel("Populism Score: " + populismScore);
        panel.add(populismScoreLabel, constraints);
        populismSlider = createImmutableScoreSlider("Populism", populismScore, constraints, populismScoreLabel);

        JButton nextParty = new JButton(">");

        ageTargetTextField.setText(membersTargetedAge);
        wealthTargetTextField.setText(membersTargetedWealth);

        nextParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeParty(1);

            }
        });

        JButton previewParty = new JButton("<");

        previewParty.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeParty(-1);

            }
        });

        // Créez un JPanel pour contenir les boutons Next et Previous
        JPanel buttonPanel = new JPanel();

        // Ajoutez les boutons Next et Previous au JPanel   
        buttonPanel.add(previewParty);
        buttonPanel.add(nextParty);

        // Ajoutez le JPanel au panneau principal avec les contraintes appropriées
        constraints.gridx = 1; // Définissez la colonne pour le JPanel des boutons
        constraints.gridy = GridBagConstraints.RELATIVE; // Permet aux composants suivants d'apparaître sous les boutons
        panel.add(buttonPanel, constraints);

        frame.add(panel);
        frame.setVisible(true);  
        frame.setLocationRelativeTo(null);
    }
/* 
    private JSlider createScoreSlider(final String scoreName, String scoreValue, GridBagConstraints constraints, final JLabel scoreLabelToUpdate) {
        scoreLabelToUpdate.setText(scoreName + " Score: " + scoreValue); 
        
        JSlider scoreSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, Integer.parseInt(scoreValue));
        scoreSlider.setMajorTickSpacing(10);
        scoreSlider.setMinorTickSpacing(1);
        scoreSlider.setPaintTicks(false);
        scoreSlider.setPaintLabels(false);
        scoreSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                if (!source.getValueIsAdjusting()) {
                    int newScoreValue = source.getValue();
                    scoreLabelToUpdate.setText(scoreName + " Score: " + newScoreValue); 
                    sliderChanged();
                }
            }
        });

        panel.add(scoreLabelToUpdate, constraints);
        panel.add(scoreSlider, constraints);
        return scoreSlider;
    }
*/
    private JSlider createImmutableScoreSlider(final String scoreName, String scoreValue, GridBagConstraints constraints, final JLabel scoreLabelToUpdate) {
        scoreLabelToUpdate.setText(scoreName + " Score: " + scoreValue); 
        
        JSlider scoreSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, Integer.parseInt(scoreValue));
        scoreSlider.setMajorTickSpacing(10);
        scoreSlider.setMinorTickSpacing(1);
        scoreSlider.setPaintTicks(false);
        scoreSlider.setPaintLabels(false);
        scoreSlider.setEnabled(false); // Désactivez le slider
        
        panel.add(scoreLabelToUpdate, constraints);
        panel.add(scoreSlider, constraints);
        return scoreSlider;
    } 

    public void sliderChanged(){
        conservatismScore = String.valueOf(conservatismSlider.getValue());
        nationalismScore = String.valueOf(nationalismSlider.getValue());
        ecologismScore = String.valueOf(ecologismSlider.getValue());           
        feminismScore = String.valueOf(feminismSlider.getValue());
        anarchismScore = String.valueOf(anarchismSlider.getValue());         
        populismScore = String.valueOf(populismSlider.getValue());
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
                
            }
        });
    }
}
