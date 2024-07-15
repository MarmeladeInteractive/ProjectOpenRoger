package com.jv01.generations.Panels.PhonePanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;

public class Applications {
    public Save save = new Save();
    public PhonePanel phonePanel;
    public MouseAdapter scrollAdapter;
    public String gameName;

    public Document doc;
    public Element[] elements;

    public List<String> applicationTitles = new ArrayList<>();
    public List<String> applicationDescriptions = new ArrayList<>();
    public List<String> applicationIds = new ArrayList<>();
    public List<String> applicationPicUrl = new ArrayList<>();
    public List<String> applicationClassesName = new ArrayList<>();
    public List<String> applicationOrientation = new ArrayList<>();
   
    public Applications(PhonePanel phonePanel, MouseAdapter scrollAdapter){
        this.phonePanel = phonePanel;
        this.scrollAdapter = scrollAdapter;

        this.gameName = phonePanel.gameName;

        getApplications();
    }

    public void clearAllArrays(){
        applicationTitles.clear();
        applicationDescriptions.clear();
        applicationIds.clear();
        applicationPicUrl.clear();
        applicationClassesName.clear();
        applicationOrientation.clear();
    }

    public void getApplications(){
        this.doc = save.getDocumentXml(gameName,"functional/phone/applications");
        this.elements = save.getElementsByTagName(doc, "application");

        clearAllArrays();

        for (Element element : elements) {
            Map<String, List<String>> allElements = save.getAllChildsFromElement(element);
            applicationIds.add(element.getAttribute("id"));
            applicationTitles.add(save.getChildFromMapElements(allElements, "name"));

            applicationPicUrl.add(save.dropSpaceFromString(save.getChildFromMapElements(allElements, "picUrl")));
            applicationDescriptions.add(save.getChildFromMapElements(allElements, "description"));

            applicationClassesName.add(save.dropSpaceFromString(save.getChildFromMapElements(allElements, "classesName")));

            applicationOrientation.add(save.getChildFromMapElements(allElements, "orientation"));
        }
    }

    public ArrayList<JPanel> getApplicationsPanel(){
        ArrayList<JPanel> list = new ArrayList<>();

        for (int i = 0; i < applicationTitles.size(); i++) {
            list.add(createApplicationPanel(applicationClassesName.get(i), applicationTitles.get(i), applicationDescriptions.get(i), applicationPicUrl.get(i), applicationOrientation.get(i)));
        }

        return list;
    }

    public JPanel createApplicationPanel(String className, String title, String description, String picUrl, String orientation) {
        final String NewClassName = className;
        final String NewOrientation = orientation;
        int nAppByRows = 4;
        int appIconSize = (((int) (phonePanel.phoneWidth * phonePanel.phoneScale) - 10) / nAppByRows) - 10;
    
        JPanel applicationPanel = new RoundedPanel();
        applicationPanel.setLayout(new BorderLayout());
        applicationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        applicationPanel.setBackground(new Color(255, 255, 255, 200));
        applicationPanel.setOpaque(false);
        applicationPanel.setPreferredSize(new Dimension(appIconSize, appIconSize));
        applicationPanel.setMaximumSize(new Dimension(appIconSize, appIconSize));
        applicationPanel.setMinimumSize(new Dimension(appIconSize, appIconSize));
    
        ImageIcon backgroundImageIcon = new ImageIcon(picUrl);
        Image backgroundImage = backgroundImageIcon.getImage();
        Image scaledBackgroundImage = backgroundImage.getScaledInstance(appIconSize, appIconSize, Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundIcon = new ImageIcon(scaledBackgroundImage);
        JLabel backgroundLabel = new JLabel(scaledBackgroundIcon);
        backgroundLabel.setBounds(0, 0, appIconSize, appIconSize);
    
        applicationPanel.add(backgroundLabel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);

        JPanel parentPanel = new JPanel();
        parentPanel.setLayout(new BorderLayout());
        parentPanel.setOpaque(false);
        parentPanel.setPreferredSize(new Dimension(appIconSize, appIconSize + 15)); 
        parentPanel.add(applicationPanel, BorderLayout.CENTER);
        parentPanel.add(titleLabel, BorderLayout.SOUTH);

        MouseAdapter listener = new MouseAdapter() {    
            @Override
            public void mouseClicked(MouseEvent e){
                phonePanel.openNewPage("app", NewOrientation, NewClassName);
            }
        };

        parentPanel.addMouseListener(scrollAdapter);
        parentPanel.addMouseMotionListener(scrollAdapter);
        parentPanel.addMouseListener(listener);

    
        return parentPanel;
    }
}
