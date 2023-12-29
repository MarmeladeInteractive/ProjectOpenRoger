package com.jv01.generations.Panels.PhonePanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.jv01.screens.GameWindowsSize;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;
import com.jv01.miniGames.games.horsesRace.HorsesRace;

public class PhonePanel {
    public Save save = new Save();
    public MainGameWindow mainGameWindow;
    public String gameName;
    public JPanel panel;
    public JFrame frame;

    public Document doc;
    public Element element;

    public GameWindowsSize GWS = new GameWindowsSize(true);

    public JPanel backPhonePanel;
    public JButton togglePhoneButton;
    public JButton disablePhoneButton;

    public int phoneWidth;
    public int phoneHeight;
    public int phoneScale = 1;

    public int percentPhoneDisplayed = 0;
    public int basePercentPhoneDisplayed = 11;
    public int phoneNewHeight;

    public String backPic;

    public String togglePhoneLogo;
    public String disablePhoneLogo;

    public PhonePanel(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.gameName = mainGameWindow.gameName;
        this.frame = mainGameWindow.frame;

        getPhoneValues();
    }

    public void getPhoneValues(){
        this.doc = save.getDocumentXml(gameName,"functional/phone/phone");
        this.element = save.getElementById(doc, "phone", "phone");

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        this.phoneWidth = Integer.parseInt(save.getChildFromMapElements(allElements,"phoneWidth"));
        this.phoneHeight = Integer.parseInt(save.getChildFromMapElements(allElements,"phoneHeight"));

        this.backPic = save.stringToStringArray(save.getChildFromElement(element, "backPic"))[0];
        this.backPic = save.dropSpaceFromString(this.backPic);

        this.togglePhoneLogo = "demo/img/phone/logos/togglePhone.png";
        this.disablePhoneLogo = "demo/img/phone/logos/disablePhone.png";
    }

    public void createPhonePanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 0, 0, 0));
        frame.add(panel);

        addBackPhone();
        addTogglePhoneButton();
        addDisablePhoneButton();

        percentPhoneDisplayed = basePercentPhoneDisplayed;
        updatePanel();
    }

    public void addBackPhone() {
        backPhonePanel = new JPanel();
        phoneNewHeight = GWS.gameWindowHeight - (int) (phoneHeight * phoneScale * ((double) percentPhoneDisplayed / 100)) - 10;

        backPhonePanel.setBounds(GWS.gameWindowWidth - phoneWidth*phoneScale - 10, phoneNewHeight, phoneWidth*phoneScale, phoneHeight*phoneScale);
        backPhonePanel.setOpaque(false);

        ImageIcon backgroundImageIcon = new ImageIcon(backPic);
        Image backgroundImage = backgroundImageIcon.getImage();

        Image scaledBackgroundImage = backgroundImage.getScaledInstance(phoneWidth*phoneScale, phoneHeight*phoneScale, Image.SCALE_SMOOTH);

        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledBackgroundImage);

        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, phoneWidth*phoneScale, phoneHeight*phoneScale);

        backPhonePanel.add(backgroundLabel);

        panel.add(backPhonePanel);
    }

    public void addTogglePhoneButton(){
        togglePhoneButton = new JButton("Toggle Phone"){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon buttonBackground = new ImageIcon(togglePhoneLogo);
                if (buttonBackground != null) {
                    g.drawImage(buttonBackground.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        togglePhoneButton.setContentAreaFilled(false);
        togglePhoneButton.setBorderPainted(false);
        togglePhoneButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        int h = phoneNewHeight - 30;
        if(percentPhoneDisplayed > basePercentPhoneDisplayed)h = GWS.gameWindowHeight + 100;
        togglePhoneButton.setBounds(GWS.gameWindowWidth - phoneWidth/2 - 45 - 10, h, 90, 30);
        togglePhoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePhone();
            }
        });

        panel.add(togglePhoneButton);
    }

    public void addDisablePhoneButton(){
        disablePhoneButton = new JButton("Disable Phone"){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon buttonBackground = new ImageIcon(disablePhoneLogo);
                if (buttonBackground != null) {
                    g.drawImage(buttonBackground.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        disablePhoneButton.setContentAreaFilled(false);
        disablePhoneButton.setBorderPainted(false);
        disablePhoneButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        int h = phoneNewHeight - 30;
        if(percentPhoneDisplayed <= basePercentPhoneDisplayed)h = GWS.gameWindowHeight + 100;
        disablePhoneButton.setBounds(GWS.gameWindowWidth - phoneWidth/2 - 45 - 10, h, 90, 30);
        disablePhoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disablePhone();
            }
        });

        panel.add(disablePhoneButton);
    }

    public void updatePanel(){
        phoneNewHeight = GWS.gameWindowHeight - (int) (phoneHeight * phoneScale * ((double) percentPhoneDisplayed / 100)) - 10;
        backPhonePanel.setBounds(GWS.gameWindowWidth - phoneWidth*phoneScale - 10, phoneNewHeight, phoneWidth*phoneScale, phoneHeight*phoneScale);
        
        if(percentPhoneDisplayed>basePercentPhoneDisplayed){
            togglePhoneButton.setBounds(GWS.gameWindowWidth - phoneWidth/2 - 45 - 10, GWS.gameWindowHeight + 100, 90, 30);
            disablePhoneButton.setBounds(GWS.gameWindowWidth - phoneWidth/2 - 45 - 10, phoneNewHeight - 30, 90, 30);
        }else{
            togglePhoneButton.setBounds(GWS.gameWindowWidth - phoneWidth/2 - 45 - 10, phoneNewHeight - 30, 90, 30);
            disablePhoneButton.setBounds(GWS.gameWindowWidth - phoneWidth/2 - 45 - 10, GWS.gameWindowHeight + 100, 90, 30);
        }

        mainGameWindow.frame.requestFocus();
        
    }

    public void togglePhone(){
        percentPhoneDisplayed = 100;
        updatePanel();
    }

    public void disablePhone(){
        percentPhoneDisplayed = basePercentPhoneDisplayed;
        updatePanel();
    }
    
    public void clearPanel() {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
    }
}
