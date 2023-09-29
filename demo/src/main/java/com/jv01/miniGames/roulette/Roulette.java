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
    private volatile boolean isWheelRotating = false;
    private volatile boolean isBallRotating = false;

    private JPanel wheelPanel;
    private JPanel ballPanel;
    private JPanel backPanel;

    private volatile double totalWheelRotationAngle;
    private volatile double rotationWheelPerIteration;
    private volatile double currentWhellRotationAngleInRadians;

    private volatile double totalBallRotationAngle;
    private volatile double rotationBallPerIteration;
    private volatile double currentBallRotationAngleInRadians;

    private int maxRound = 10;
    private int minRound = 2;

    public int finalValue;
    public String finalColor;

    public JLabel scoreLabel = new JLabel("...");

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
        createBall();
        createBack();       
        
        rotateButton = new JButton("Tourner la roue");
        rotateButton.setBounds((boxSize)-200-100, (boxSize)-50-100, 200, 50);
 
        rotateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(!isWheelRotating){
                    runGame();
                }
            }
        });

        scoreLabel.setText("0 - Vert");
        scoreLabel.setBounds((boxSize/2), (boxSize/2), 200, 50);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gamePanel.add(ballPanel); 
        gamePanel.add(wheelPanel); 
        gamePanel.add(backPanel);  
        gamePanel.add(rotateButton);
        gamePanel.add(scoreLabel);
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
                
                g2d.rotate(currentWhellRotationAngleInRadians, centerX, centerY);
                
                g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        wheelPanel.setOpaque(false);
        wheelPanel.setBounds(50, (boxSize/2)-200, 400, 400);       
    }
    
    private void createBall(){
        ballPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
    
                ImageIcon backgroundImage = new ImageIcon("demo/src/main/java/com/jv01/miniGames/roulette/img/ball.png");
                Image img = backgroundImage.getImage();
    
                Graphics2D g2d = (Graphics2D) g;
    
                int centerX = this.getWidth() / 2;
                int centerY = this.getHeight() / 2;
    
                g2d.rotate(currentBallRotationAngleInRadians, centerX, centerY);
    
                g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        ballPanel.setOpaque(false);
        ballPanel.setBounds(50, (boxSize/2)-200, 400, 400);
    }
    private void createBack(){
        backPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
    
                ImageIcon backgroundImage = new ImageIcon("demo/src/main/java/com/jv01/miniGames/roulette/img/back.png");
                Image img = backgroundImage.getImage();
                
                Graphics2D g2d = (Graphics2D) g;
    
                int centerX = this.getWidth() / 2;
                int centerY = this.getHeight() / 2;
                
                g2d.rotate(currentWhellRotationAngleInRadians, centerX, centerY);
                
                g2d.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        backPanel.setOpaque(false);
        backPanel.setBounds(50, (boxSize/2)-200, 400, 400);       
    }

    private void runGame(){
        rotateWheel();
        rotateBall();
    }
    
    

    private void rotateWheel(){
        isWheelRotating = true;
        double rotation = Math.toRadians(random.nextInt(360*3+1));
        int nTours = random.nextInt(maxRound-minRound+1) + minRound;
        totalWheelRotationAngle = rotation + Math.toRadians(360*nTours);

        rotationWheelPerIteration = 0.2;
        int refreshInterval = 10;
    
        Timer animationTimer = new Timer(refreshInterval, new ActionListener(){
    
            @Override
            public void actionPerformed(ActionEvent e){
                if(currentWhellRotationAngleInRadians <= totalWheelRotationAngle){

                    currentWhellRotationAngleInRadians += rotationWheelPerIteration;
                    if(!isBallRotating){
                        currentBallRotationAngleInRadians += rotationWheelPerIteration;
                        ballPanel.repaint();
                    }

                    double stay = totalWheelRotationAngle - currentWhellRotationAngleInRadians;

                    if (stay >= 0 && stay < 17) {
                        rotationWheelPerIteration = 0.2 - (17-stay) * 0.01172;
                        if (rotationWheelPerIteration < 0.001) {
                            rotationWheelPerIteration = 0.001;
                        }
                    }
                    wheelPanel.repaint();

                }else{
                    ((Timer) e.getSource()).stop();
                    currentWhellRotationAngleInRadians = currentWhellRotationAngleInRadians%(Math.PI*2);
                    wheelPanel.repaint();
                    isWheelRotating = false;
                    if(!isBallRotating && !isWheelRotating){
                        getValue();   
                    }
                }
            }
        });

        animationTimer.start();
    }

    private void rotateBall(){
        isBallRotating = true;
        double rotation = Math.toRadians(random.nextInt(360+1));
        totalBallRotationAngle = totalWheelRotationAngle-(Math.PI*2*3) +  rotation ;

        totalBallRotationAngle = totalBallRotationAngle*(-1.0);

        rotationBallPerIteration = 0.3;
        int refreshInterval = 10;
    
        Timer animationBallTimer = new Timer(refreshInterval, new ActionListener(){
    
            @Override
            public void actionPerformed(ActionEvent e){
                if(currentBallRotationAngleInRadians >= totalBallRotationAngle){

                    currentBallRotationAngleInRadians -= rotationBallPerIteration;

                    double stay = totalBallRotationAngle - currentBallRotationAngleInRadians;
                    stay = stay*(-1.0);

                    if (stay >= 0 && stay < 10) {
                        rotationBallPerIteration = 0.3 - (10-stay) * 0.0305;
                        if (rotationBallPerIteration < 0.001) {
                            rotationBallPerIteration = 0.001;
                        }
                    }
                    ballPanel.repaint();
                }else{
                    ((Timer) e.getSource()).stop();
                    currentBallRotationAngleInRadians = currentBallRotationAngleInRadians%(Math.PI*2);
                    ballPanel.repaint();
                    isBallRotating = false;
                    if(!isBallRotating && !isWheelRotating){
                        getValue();   
                    }
                }
            }
        });

        animationBallTimer.start();
    }
    

    private void refreshGamePanel(){
        gamePanel.removeAll();
 
        gamePanel.revalidate();
        gamePanel.repaint();
        //addArcadeBorder(gamePanel);///////////////////////////////////////////////////////////////////////////
    }

    private double getWheelValue(){
        currentWhellRotationAngleInRadians = currentWhellRotationAngleInRadians%(Math.PI*2);
        return currentWhellRotationAngleInRadians;
    }

    private double getBallValue(){
        currentBallRotationAngleInRadians = Math.PI*2+currentBallRotationAngleInRadians;
        currentBallRotationAngleInRadians = currentBallRotationAngleInRadians%(Math.PI*2);
        return currentBallRotationAngleInRadians;
    }

    private void getValue(){
        double wheelAngle = getWheelValue();
        double whellBall = getBallValue();
        double newVal;

        if(whellBall>=wheelAngle){
            newVal = Math.abs(wheelAngle - whellBall);
        }else{
            newVal = Math.PI*2 - Math.abs(wheelAngle - whellBall);
        }

        int[] numbers = {0,32,15,19,4,21,2,25,17,34,6,27,13,36,11,30,8,23,10,5,24,16,33,1,20,14,31,9,22,18,29,7,28,12,35,3,26};
        String[] colors = {"vert","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire","rouge","noire"};
        double anglePerCase = (Math.PI*2)/37;

        newVal = newVal + anglePerCase/2;

        int index = (int)Math.floor(Math.toDegrees(newVal) / Math.toDegrees(anglePerCase));
        changeResultValues(String.valueOf(numbers[index]),colors[index]);
    }

    private void changeResultValues(String number, String colors){
        scoreLabel.setText(number + " - " + colors);
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
