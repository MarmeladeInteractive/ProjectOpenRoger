package com.jv01.generations.Panels.PhonePanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
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
    }

    public void getApplications(){
        this.doc = save.getDocumentXml(gameName,"functional/phone/applications");
        this.elements = save.getElementsByTagName(doc, "application");

        clearAllArrays();

        for (Element element : elements) {
            Map<String, List<String>> allElements = save.getAllChildsFromElement(element);
            applicationIds.add(element.getAttribute("id"));
            applicationTitles.add(save.getChildFromMapElements(allElements, "name"));
            applicationPicUrl.add(save.getChildFromMapElements(allElements, "picUrl"));
            applicationDescriptions.add(save.getChildFromMapElements(allElements, "description"));
        }
    }

    public ArrayList<JPanel> getApplicationsPanel(){
        ArrayList<JPanel> list = new ArrayList<>();

        for (int i = 0; i < applicationTitles.size(); i++) {
            list.add(createApplicationPanel(applicationIds.get(i), applicationTitles.get(i), applicationDescriptions.get(i)));
        }

        return list;
    }

    public JPanel createApplicationPanel(String id, String title, String description) {
        final String newId = id;
        int nAppByRows = 4;
        int appIconSize = ((phonePanel.phoneNewWidth - 10) / nAppByRows) - 4 ;
        appIconSize = 70;
        JPanel applicationPanel = new RoundedPanel();
        applicationPanel.setLayout(new BorderLayout());
        applicationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        applicationPanel.setBackground(new Color(255, 255, 255, 200));
        applicationPanel.setOpaque(false); 
        applicationPanel.setMaximumSize(new Dimension(appIconSize, appIconSize));
        
        return applicationPanel;
    }

}
