package com.jv01.generations.Panels.PhonePanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
   
    public Applications(PhonePanel phonePanel, MouseAdapter scrollAdapter){
        this.phonePanel = phonePanel;
        this.scrollAdapter = scrollAdapter;

        this.gameName = phonePanel.gameName;

        getApplications();
    }

    public void clearAllArrays(){
        
    }

    public void getApplications(){
        this.doc = save.getDocumentXml(gameName,"functional/phone/applications");
        this.elements = save.getElementsByTagName(doc, "application");

        clearAllArrays();

        for (Element element : elements) {
            Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        }
    }

}
