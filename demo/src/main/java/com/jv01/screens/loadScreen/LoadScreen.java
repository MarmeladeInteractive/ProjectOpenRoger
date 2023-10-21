package com.jv01.screens.loadScreen;

import javax.swing.*;

import com.jv01.miniGames.games.horsesRace.HorsesRace;
import com.jv01.miniGames.games.roulette.Roulette;
import com.jv01.miniGames.games.NoGame.NoGame;
import com.jv01.screens.MainMenuScreen;
import com.jv01.generations.MainGameWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class LoadScreen {
    public static MainMenuScreen mainMenu = new MainMenuScreen();
    public int boxSize = 800;

    public JFrame frame = new JFrame("Load Menu");

    private JLabel label = new JLabel();

    private JLabel imageLabel1 = new JLabel();
    private JLabel imageLabel1_2 = new JLabel();

    private JLabel imageLabel2 = new JLabel();

    private float alpha1 = 0.0f;
    private float alpha1_2 = 0.0f;

    private float alpha2 = 0.0f;

    private Timer timer1;
    private Timer timer1_2;

    private Timer timer2;

    private ImageIcon imageIcon1;
    private ImageIcon imageIcon1_2;

    private ImageIcon imageIcon2;

    public LoadScreen() {  
        frame.getContentPane().removeAll();
        frame.repaint();

        frame.setUndecorated(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(boxSize, boxSize);

        try {
            imageIcon1 = new ImageIcon("demo/src/main/java/com/jv01/screens/loadScreen/img/MarmeladeStudioLogo01_0.png");
            Image image1 = imageIcon1.getImage().getScaledInstance(boxSize, boxSize, Image.SCALE_SMOOTH);
            imageIcon1 = new ImageIcon(image1);
            imageLabel1.setIcon(new ImageIcon(makeImageWithAlpha(imageIcon1.getImage(), 0.0f)));

            imageLabel1.setBounds(0, 0, boxSize, boxSize);

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            imageIcon1_2 = new ImageIcon("demo/src/main/java/com/jv01/screens/loadScreen/img/MarmeladeStudioLogo01_2.png");
            Image image1_2 = imageIcon1_2.getImage().getScaledInstance(boxSize, boxSize, Image.SCALE_SMOOTH);
            imageIcon1_2 = new ImageIcon(image1_2);
            imageLabel1_2.setIcon(new ImageIcon(makeImageWithAlpha(imageIcon1_2.getImage(), 0.0f)));

            imageLabel1_2.setBounds(0, 0, boxSize, boxSize);

        } catch (Exception e) {
            e.printStackTrace();
        }

        label.setLayout(null);

        timer1 = new Timer(80, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alpha1 < 1.0f) {
                    alpha1 += 0.08f;
                    if (alpha1 > 1.0f) alpha1 = 1.0f;
                    imageLabel1.setIcon(new ImageIcon(makeImageWithAlpha(imageIcon1.getImage(), alpha1)));
                    frame.revalidate();
                    frame.repaint();
                } else {
                    timer1.stop();
                    timer1_2.start();
                    //label.remove(imageLabel1);
                    //startSecondLogoFadeIn();
                }
            }
        });
        timer1.setRepeats(true);

        timer1_2 = new Timer(80, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alpha1_2 < 1.0f) {
                    alpha1_2 += 0.05f;
                    if (alpha1_2 > 1.0f) alpha1_2 = 1.0f;
                    imageLabel1_2.setIcon(new ImageIcon(makeImageWithAlpha(imageIcon1_2.getImage(), alpha1_2)));
                    frame.revalidate();
                    frame.repaint();
                } else {
                    timer1_2.stop();
                    label.remove(imageLabel1);
                    label.remove(imageLabel1_2);
                    startSecondLogoFadeIn();
                }
            }
        });
        timer1_2.setRepeats(true);

        label.add(imageLabel1_2);
        label.add(imageLabel1);
        
        
        frame.add(label);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        timer1.start();
        
    }

    private void startSecondLogoFadeIn() {

        
        try {
            imageIcon2 = new ImageIcon("demo/src/main/java/com/jv01/screens/loadScreen/img/mscompany.png");
            Image image2 = imageIcon2.getImage().getScaledInstance(boxSize, boxSize, Image.SCALE_SMOOTH);
            imageIcon2 = new ImageIcon(image2);
            imageLabel2.setIcon(new ImageIcon(makeImageWithAlpha(imageIcon2.getImage(), 0.0f)));

            imageLabel2.setBounds(0, 0, boxSize, boxSize);

            label.add(imageLabel2);
            frame.revalidate();
            frame.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }

        timer2 = new Timer(80, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (alpha2 < 1.0f) {
                    alpha2 += 0.05f;
                    if (alpha2 > 1.0f) alpha2 = 1.0f;
                    imageLabel2.setIcon(new ImageIcon(makeImageWithAlpha(imageIcon2.getImage(), alpha2)));
                    frame.revalidate();
                    frame.repaint();
                } else {
                    timer2.stop();
                    startMainMenu();
                }
            }
        });
        timer2.setRepeats(true);

        timer2.start();
    }

    private Image makeImageWithAlpha(Image image, float alpha) {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return bufferedImage;
    }

    private void startMainMenu() {
        frame.dispose();
        mainMenu.showMainMenu();
    }
}
