package com.jv01.miniGames.roulette;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.Random;

public class Roulette{
    private JPanel gamePanel;
    private int boxSize;
    private Random random = new Random();

    private JButton startGameButton;

    private JButton rotateButton;
    private volatile boolean isRotating = false;

    private JPanel wheelPanel;

    private volatile double totalRotationAngle;
    private volatile double rotationPerIteration;
    private volatile double currentRotationAngleInRadians;

    private int maxRound = 10;
    private int minRound = 2;

    public Roulette(JPanel parentPanel, int boxSize){
        this.gamePanel = parentPanel; 
        this.gamePanel.setLayout(null);

        this.boxSize = boxSize;

        showMenu();
    }

    public void showMenu(){
        JLabel titleLabel = new JLabel("Roulette");
        titleLabel.setBounds((boxSize/2)-100, (boxSize/2)-25, 200, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = GridBagConstraints.RELATIVE;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        startGameButton = new JButton("Lancer le jeux");
        startGameButton.setBounds((boxSize/2)-100, (boxSize/2)+25, 200, 50);
  
        startGameButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                startGame();
            }
        });

        gamePanel.add(titleLabel);
        gamePanel.add(startGameButton, constraints);
    }

    private void startGame(){
        refreshGamePanel();
        createWheel();
        
        rotateButton = new JButton("Tourner la roue");
        rotateButton.setBounds((boxSize)-200-100, (boxSize)-50-100, 200, 50);
 
        rotateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!isRotating)rotateWheel();
            }
        });

        gamePanel.add(rotateButton);
    }

    private void createWheel(){
        wheelPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);

                ImageIcon backgroundImage = new ImageIcon("demo/src/main/java/com/jv01/miniGames/roulette/img/wheel.png");
                Image img = backgroundImage.getImage();
                
                Graphics2D g2d = (Graphics2D) g;

                int centerX = this.getWidth() / 2;
                int centerY = this.getHeight() / 2;
                
                g2d.rotate(currentRotationAngleInRadians, centerX, centerY);
                
                g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
            
        };
        wheelPanel.setBounds(50, (boxSize/2)-200, 400, 400);

        gamePanel.add(wheelPanel);
    }

    private void rotateWheel(){
        isRotating = true;
        double rotation = Math.toRadians(random.nextInt(360*3+1));
        int nTours = random.nextInt(maxRound-minRound+1) + minRound;
        totalRotationAngle = rotation + Math.toRadians(360*nTours);

        rotationPerIteration = 0.2;
        int refreshInterval = 10;
    
        Timer animationTimer = new Timer(refreshInterval, new ActionListener(){
    
            @Override
            public void actionPerformed(ActionEvent e){
                if(currentRotationAngleInRadians <= totalRotationAngle){

                    currentRotationAngleInRadians += rotationPerIteration;

                    double stay = totalRotationAngle - currentRotationAngleInRadians;

                    if (stay >= 0 && stay < 17) {
                        rotationPerIteration = 0.2 - (17-stay) * 0.01172;
                        if (rotationPerIteration < 0.001) {
                            rotationPerIteration = 0.001;
                        }
                    }
                    wheelPanel.repaint();
                }else{
                    ((Timer) e.getSource()).stop();
                    currentRotationAngleInRadians = currentRotationAngleInRadians%(Math.PI*2);
                    wheelPanel.repaint();
                    isRotating = false;
                }
            }
        });

        animationTimer.start();
    }
    

    private void refreshGamePanel(){
        gamePanel.removeAll();
 
        gamePanel.revalidate();
        gamePanel.repaint();
        addArcadeBorder(gamePanel);///////////////////////////////////////////////////////////////////////////
    }












private static void addArcadeBorder(JPanel panel){
        JLabel imageLabel = new JLabel();  
    
        try {
            ImageIcon imageIcon = new ImageIcon("demo/img/arcades/arcadeInside01.png");
            Image image = imageIcon.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(image);
            imageLabel.setIcon(imageIcon);
    
            imageLabel.setBounds(0, 0, 800, 800);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        panel.setLayout(null);
        panel.add(imageLabel);
        panel.setComponentZOrder(imageLabel, 0);
}

    public static void main(String[] args){
        JFrame frame;
        frame = new JFrame("Main Game Window");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        
        frame.getContentPane().setBackground(new java.awt.Color(90, 90, 90));

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 800, 800);

        addArcadeBorder(panel);

        frame.add(panel);

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);

        new Roulette(panel,800);
    }
}
