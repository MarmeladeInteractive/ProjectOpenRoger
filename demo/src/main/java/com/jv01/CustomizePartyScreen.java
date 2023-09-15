package com.jv01;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomizePartyScreen {

    public Game game;

    public String gameTitle = "Jeux";
    private JFrame frame = new JFrame("Party Customization");

    public JTextField partyIDTextField = new JTextField(20);
    public JTextField seedTextField = new JTextField(20);

    public JButton chooseSeedButton;

    public JPanel panel;
    public JList<String> gameNamesJList;
    public GridBagConstraints constraints;

    public String gameName = "";

    public Save save;

    public String choosenParty = "1";

    public CustomizePartyScreen(Game runningGame){
        this.game = runningGame;

        this.gameTitle = "Jeux";
        this.frame = new JFrame("Party Customization");

        this.partyIDTextField = new JTextField(20);
        this.seedTextField = new JTextField(20);
        this.gameName = "";

        this.choosenParty = "1";
    }

    public void openCustomizePartyScreen() {
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        panel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        JLabel titleLabel = new JLabel("Choose and customize a party :");
        panel.add(titleLabel, constraints);

        panel.add(partyIDTextField, constraints);

        //JLabel titleLabelPartyId = new JLabel("Type the party ID you want : ");
        //panel.add(titleLabelPartyId, constraints);

        //panel.add(seedTextField, constraints);
        
        JButton partyIDButton = new JButton("Select chose ID");
        panel.add(partyIDButton, constraints);

        partyIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosenParty = partyIDTextField.getText();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        panel.add(cancelButton, constraints);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JButton savePartyButton = new JButton("Save Changes");
        panel.add(savePartyButton, constraints);

        savePartyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save.changeElementChildValue(game.name,"saves/" + gameName + "/" + "parties" + ".xml","party",choosenParty,"partyID",choosenParty+'1');
            }
        });
    }
}
