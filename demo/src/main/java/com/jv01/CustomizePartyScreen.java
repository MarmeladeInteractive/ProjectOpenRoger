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

class CustomizePartyScreen extends JPanel {
    public static Save save = new Save();
    public Document doc;

    public String gameName;
    public String seed;

    public int currentParty = 1;

    private static final int BOXE_SIZE = 800;

    public JFrame frame = new JFrame("Customize party screen");

    public JPanel panel;

    public int pointBank = 60;

    public JTextField partyNameTextField = new JTextField(20);
    public JTextField ageTargetTextField = new JTextField(20);
    public JTextField wealthTargetTextField = new JTextField(20); 

    public String partyName;
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

    

    public CustomizePartyScreen(String gameName, String seed) {
        this.gameName = gameName;
        this.seed = seed;

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
        panel.add(partyNameTextField, constraints);
        
        /*String members = party.get("members").get(0);
        JLabel membersLabel = new JLabel("Members's number: " + members);
        panel.add(membersLabel, constraints);*/

        membersTargetedAge = party.get("membersTargetedAge").get(0);
        JLabel membersTargetedAgeLabel = new JLabel("Targeted Age: " + membersTargetedAge);
        panel.add(membersTargetedAgeLabel, constraints);
        panel.add(ageTargetTextField,constraints);

        membersTargetedWealth = party.get("membersTargetedWealth").get(0);
        JLabel membersTargetedWealthLabel = new JLabel("Targeted Wealth: " + membersTargetedWealth);
        panel.add(membersTargetedWealthLabel, constraints);
        panel.add(wealthTargetTextField,constraints);

        /*String wealth = party.get("wealth").get(0);
        JLabel wealthLabel = new JLabel("Party Wealth: " + wealth);
        panel.add(wealthLabel, constraints);*/
        
        conservatismScore = party.get("conservatismScore").get(0);
        JLabel partyconservatismScore = new JLabel("Conservatism Score: " + conservatismScore);
        panel.add(partyconservatismScore, constraints);
        conservatismSlider = createScoreSlider("Conservatism", conservatismScore, constraints, partyconservatismScore);

        nationalismScore = party.get("nationalismScore").get(0);
        JLabel nationalismScoreLabel = new JLabel("Nationalism Score: " + nationalismScore);
        panel.add(nationalismScoreLabel, constraints);
        nationalismSlider = createScoreSlider("Nationalism", nationalismScore, constraints, nationalismScoreLabel);

        ecologismScore = party.get("ecologismScore").get(0);
        JLabel ecologismScoreLabel = new JLabel("Ecologism Score: " + ecologismScore);
        panel.add(ecologismScoreLabel, constraints);
        ecologismSlider = createScoreSlider("Ecologism", ecologismScore, constraints, ecologismScoreLabel);
        
        feminismScore = party.get("feminismScore").get(0);
        JLabel feminismScoreLabel = new JLabel("Feminism Score: " + feminismScore);
        panel.add(feminismScoreLabel, constraints);        
        feminismSlider = createScoreSlider("Feminism", feminismScore, constraints, feminismScoreLabel);

        anarchismScore = party.get("anarchismScore").get(0);
        JLabel anarchismScoreLabel = new JLabel("Anarchism Score: " + anarchismScore);
        panel.add(anarchismScoreLabel, constraints);      
        anarchismSlider = createScoreSlider("Anarchism", anarchismScore, constraints, anarchismScoreLabel);
  
        populismScore = party.get("populismScore").get(0);
        JLabel populismScoreLabel = new JLabel("Populism Score: " + populismScore);
        panel.add(populismScoreLabel, constraints);
        populismSlider = createScoreSlider("Populism", populismScore, constraints, populismScoreLabel);

        JButton nextParty = new JButton(">");

        partyNameTextField.setText(partyName);
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

        JButton savePartyButton = new JButton("Save party change");
        panel.add(savePartyButton, constraints);

        savePartyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Modifier les champs conservatismScore, nationalismScore, ecologismScore, 
                // feminismScore, populismScore, anarchismScore dans le XML saves\[gameName]\parties.xml
                if (isIntegerInRange(wealthTargetTextField.getText(), 0, 100)) {
                    membersTargetedWealth = wealthTargetTextField.getText();
                } else {
                    // message d'erreur à l'utilisateur
                    JOptionPane.showMessageDialog(frame, "Targeted wealth must be a value between 0 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                if (isIntegerInRange(ageTargetTextField.getText(), 18, 100)) {
                    membersTargetedAge = ageTargetTextField.getText();
                } else {
                    // message d'erreur à l'utilisateur
                    JOptionPane.showMessageDialog(frame, "Targeted age must be a value between 18 and 100.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }
                String partyName = partyNameTextField.getText();
                
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "conservatismScore", String.valueOf(conservatismScore));
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "nationalismScore", String.valueOf(nationalismScore));
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "ecologismScore", String.valueOf(ecologismScore));
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "feminismScore", String.valueOf(feminismScore));
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "populismScore", String.valueOf(populismScore));
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "anarchismScore", String.valueOf(anarchismScore));
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "membersTargetedWealth", String.valueOf(membersTargetedWealth));
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "membersTargetedAge", String.valueOf(membersTargetedAge)); 
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "partyName", partyName);   
            }
        });

        JButton startButton = new JButton("Choose this party and start now");
        panel.add(startButton, constraints);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Modifier le partyID du joueur pour qu'il corresponde à celui choisi et 
                // enregistrer dans le XML saves\[gameName]\player.xml
                save.changeElementChildValue(gameName, "player", "player", "player", "partyID", String.valueOf(currentParty));
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "members", String.valueOf(1));
                save.changeElementChildValue(gameName, "parties", "party", String.valueOf(currentParty), "wealth", String.valueOf(0));    
                frame.dispose();
                Game game = new Game();
                game.startGame(gameName,seed);
            }
        });

        frame.add(panel);
        frame.setVisible(true);  
        frame.setLocationRelativeTo(null);
    }

    public static boolean isIntegerInRange(String textField, int min, int max) {
        try {         
            int number = Integer.parseInt(textField);
    
            return number >= min && number <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

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
                CustomizePartyScreen customizePartyScreen = new CustomizePartyScreen("aboieaboiechieng","eeee");
            }
        });
    }
}
