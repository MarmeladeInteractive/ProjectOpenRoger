package com.jv01;

import javax.swing.*;
import java.awt.*;

public class LoadingScreen {
    private JFrame frame;

    public void showLoadingScreen() {
        frame = new JFrame("Chargement...");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        frame.add(panel);

        JLabel loadingLabel = new JLabel("Chargement en cours...");
        loadingLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        panel.add(loadingLabel, constraints);

        frame.setVisible(true);
    }

    public void closeLoadingScreen() {
        frame.dispose();
    }
}
