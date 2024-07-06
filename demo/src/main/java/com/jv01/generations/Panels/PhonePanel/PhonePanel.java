package com.jv01.generations.Panels.PhonePanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.jv01.screens.GameWindowsSize;
import com.jv01.screens.Windows.GameMap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;

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
    public JPanel screenPanel;

    public JLabel dateLabel;
    public JLabel hourLabel;
    public JLabel moneyLabel;

    public JButton togglePhoneButton;
    public JButton disablePhoneButton;

    public int phoneWidth;
    public int phoneHeight;
    public double phoneScale = 1.0;

    public int percentPhoneDisplayed = 0;
    public int basePercentPhoneDisplayed = 11;
    public int phoneNewHeight;
    public int phoneNewWidth;

    public Timer timerTogglePhone = new Timer();
    public Timer timerDisablePhone = new Timer();

    public String backPic;
    public String backPicLandscape;

    public String togglePhoneLogo;
    public String disablePhoneLogo;

    public String mode = "Portrait";

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

        this.backPicLandscape = save.stringToStringArray(save.getChildFromElement(element, "backPicLandscape"))[0];
        this.backPicLandscape = save.dropSpaceFromString(this.backPicLandscape);

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
    }
    public void clearPhonePanel(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();  
    }

    public void createPhonePanelPortrait() {
        clearPhonePanel();
        backPhonePanel = new JPanel();
        backPhonePanel.setLayout(null);

        screenPanel = new JPanel();
        screenPanel.setLayout(null);

        mode = "Portrait";
        phoneScale = 1;

        addDateLabel();
        addHourLabel();
        addMoneyLabel();

        addBackPhonePortait();

        addTogglePhoneButton();
        addDisablePhoneButton();

        percentPhoneDisplayed = basePercentPhoneDisplayed;
        updatePanelPortrait();
    }

    public void createPhonePanelLandscape(){
        clearPhonePanel();
        backPhonePanel = new JPanel();
        backPhonePanel.setLayout(null);

        screenPanel = new JPanel();
        screenPanel.setLayout(null);

        mode = "Landscape";
        //createPhonePanel();
        phoneScale = 2.8;
    
        addBackPhoneLandscape();
    }
    
    public void addBackPhoneLandscape() {
        phoneNewHeight = (int)(GWS.gameWindowHeight / 2) - (int)((phoneWidth*phoneScale)/2);
        phoneNewWidth =  (int)(GWS.gameWindowWidth / 2) - (int)((phoneHeight*phoneScale)/2);

        backPhonePanel.setBounds(phoneNewWidth, phoneNewHeight,(int)(phoneHeight*phoneScale) , (int)(phoneWidth*phoneScale));
        backPhonePanel.setOpaque(false);

        screenPanel.setBounds(phoneNewWidth, phoneNewHeight,(int)(phoneHeight*phoneScale) , (int)(phoneWidth*phoneScale));
        screenPanel.setOpaque(false);
    
        ImageIcon backgroundImageIcon = new ImageIcon(backPicLandscape);
        Image backgroundImage = backgroundImageIcon.getImage();
    
        Image scaledBackgroundImage = backgroundImage.getScaledInstance((int)(phoneHeight * phoneScale), (int)(phoneWidth * phoneScale), Image.SCALE_SMOOTH);
    
        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledBackgroundImage);
    
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, (int)(phoneHeight * phoneScale), (int)(phoneWidth * phoneScale));

        backPhonePanel.add(backgroundLabel);
    
        panel.add(screenPanel);
        panel.add(backPhonePanel);
    }

    public void addBackPhonePortait() {
        phoneNewHeight = GWS.gameWindowHeight - (int) (phoneHeight * phoneScale * ((double) percentPhoneDisplayed / 100)) - 10;
        backPhonePanel.setBounds((int)(GWS.gameWindowWidth - phoneWidth*phoneScale - 10), phoneNewHeight, (int)(phoneWidth*phoneScale+10), (int)(phoneHeight*phoneScale+10));
        backPhonePanel.setOpaque(false);

        screenPanel.setBounds((int)(GWS.gameWindowWidth - phoneWidth*phoneScale - 10), phoneNewHeight, (int)(phoneWidth*phoneScale+10), (int)(phoneHeight*phoneScale+10));
        screenPanel.setOpaque(false);

        ImageIcon backgroundImageIcon = new ImageIcon(backPic);
        Image backgroundImage = backgroundImageIcon.getImage();

        Image scaledBackgroundImage = backgroundImage.getScaledInstance((int)(phoneWidth*phoneScale), (int)(phoneHeight*phoneScale), Image.SCALE_SMOOTH);

        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledBackgroundImage);

        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, (int)(phoneWidth*phoneScale), (int)(phoneHeight*phoneScale));

        backPhonePanel.add(backgroundLabel);

        panel.add(screenPanel);
        panel.add(backPhonePanel);
    }

    public void addDateLabel(){
        dateLabel = new JLabel("22/12/2022");
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 24));
        dateLabel.setBounds((int)(phoneWidth*phoneScale) - 120 - 20, 15, 150, 20);

        screenPanel.add(dateLabel);
    }
    public void updateDateLabel(String value){
        if(mode == "Portrait")dateLabel.setText(value);
    }

    public void addHourLabel(){
        hourLabel = new JLabel("16h30");
        hourLabel.setForeground(Color.WHITE);
        hourLabel.setFont(new Font("Arial", Font.BOLD, 25));
        hourLabel.setBounds(20, 15, 100, 20);

        screenPanel.add(hourLabel);
    }
    public void updateHourLabel(String value){
        if(mode == "Portrait")hourLabel.setText(value);
    }

    public void addMoneyLabel(){
        moneyLabel = new JLabel(mainGameWindow.player.money + "€");
        moneyLabel.setForeground(Color.WHITE);
        moneyLabel.setFont(new Font("Arial", Font.BOLD, 25));
        moneyLabel.setBounds(20, 45, 100, 20);

        screenPanel.add(moneyLabel);
    }
    public void updateMoneyLabel(String value){
        if(mode == "Portrait")moneyLabel.setText(value+"€");
    }

    public void addTogglePhoneButton(){
        togglePhoneButton = new JButton(""){
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
        togglePhoneButton.setBounds(GWS.gameWindowWidth - 110, h, 90, 30);
        togglePhoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                togglePhone();
            }
        });

        panel.add(togglePhoneButton);
    }

    public void addDisablePhoneButton(){
        disablePhoneButton = new JButton(""){
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
        disablePhoneButton.setBounds(GWS.gameWindowWidth - 110, h, 90, 30);
        disablePhoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                disablePhone();
            }
        });

        panel.add(disablePhoneButton);
    }

    public void updatePanelPortrait(){
        phoneNewHeight = GWS.gameWindowHeight - (int) (phoneHeight * phoneScale * ((double) percentPhoneDisplayed / 100)) - 10;
        screenPanel.setBounds(GWS.gameWindowWidth - (int)(phoneWidth*phoneScale) - 10, phoneNewHeight, (int)(phoneWidth*phoneScale)+10, (int)(phoneHeight*phoneScale)+10);
        backPhonePanel.setBounds(GWS.gameWindowWidth - (int)(phoneWidth*phoneScale) - 10, phoneNewHeight, (int)(phoneWidth*phoneScale)+10, (int)(phoneHeight*phoneScale)+10);

        if(percentPhoneDisplayed>basePercentPhoneDisplayed){
            togglePhoneButton.setBounds(GWS.gameWindowWidth - 110, GWS.gameWindowHeight + 100, 90, 30);
            disablePhoneButton.setBounds(GWS.gameWindowWidth - 110, phoneNewHeight - 30, 90, 30);
        }else{
            togglePhoneButton.setBounds(GWS.gameWindowWidth - 110, phoneNewHeight - 30, 90, 30);
            disablePhoneButton.setBounds(GWS.gameWindowWidth - 110, GWS.gameWindowHeight + 100, 90, 30);
        }

        focusOnMainFrame();
    }
    public void focusOnMainFrame(){
        mainGameWindow.frame.requestFocus();
    }

    public void togglePhone(){
        if(mode == "Portrait"){
            timerTogglePhone = new Timer();
            if(percentPhoneDisplayed<100){   
                timerTogglePhone.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (percentPhoneDisplayed < 100) {
                            percentPhoneDisplayed++;
                            updatePanelPortrait();
                        } else {
                            timerTogglePhone.cancel();
                        }
                    }
                }, 0, 3);
            } 
        } 
    }
    
    public void disablePhone(){
        if(mode == "Portrait"){
            timerDisablePhone = new Timer();
            if(percentPhoneDisplayed>basePercentPhoneDisplayed){   
                timerDisablePhone.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (percentPhoneDisplayed > basePercentPhoneDisplayed) {
                            percentPhoneDisplayed--;
                            updatePanelPortrait();
                        } else {
                            timerDisablePhone.cancel();
                        }
                    }
                }, 0, 3);
            }  
        }
    }

    public void open(String idScreen){
        clearPhonePanel();
        if(mode == "Portrait"){
            createPhonePanelLandscape();
            switch (idScreen) {
                case "Map":
                    mainGameWindow.map = new GameMap(mainGameWindow);
                    break;
            
                default:
                    clearPhonePanel();
                    createPhonePanelPortrait();
                    break;
            }
        }else{
            createPhonePanelPortrait();
        }     
    }
    
}
