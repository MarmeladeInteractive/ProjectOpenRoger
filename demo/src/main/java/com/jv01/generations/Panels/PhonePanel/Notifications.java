package com.jv01.generations.Panels.PhonePanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.jv01.screens.GameWindowsSize;
import com.jv01.screens.Windows.GameMap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;

public class Notifications {
    public Save save = new Save();
    public PhonePanel phonePanel;
    private MouseAdapter scrollAdapter;

    private String gameName;

    public Document doc;
    public Element[] elements;

    public List<String> notSeenNotificationTitles = new ArrayList<>();
    public List<String> notSeenNotificationDescriptions = new ArrayList<>();
    public List<String> notSeenNotificationIds = new ArrayList<>();

    public List<String> seenNotificationTitles = new ArrayList<>();
    public List<String> seenNotificationDescriptions = new ArrayList<>();
    public List<String> seenNotificationIds = new ArrayList<>();

    public Notifications(String gameName){
        this.gameName = gameName;
    }

    public Notifications(PhonePanel phonePanel, MouseAdapter scrollAdapter){
        this.phonePanel = phonePanel;
        this.scrollAdapter = scrollAdapter;
        this.gameName = phonePanel.gameName;

        getNotifications();
    }

    public void clearAllArrays(){
        notSeenNotificationTitles.clear();
        notSeenNotificationDescriptions.clear();
        notSeenNotificationIds.clear();

        seenNotificationTitles.clear();
        seenNotificationDescriptions.clear();
        seenNotificationIds.clear();
    }

    public void getNotifications(){
        this.doc = save.getDocumentXml(gameName,"functional/phone/notifications");
        this.elements = save.getElementsByTagName(doc, "notification");

        clearAllArrays();

        for (Element element : elements) {
            Map<String, List<String>> allElements = save.getAllChildsFromElement(element);
            if(save.getChildFromMapElements(allElements, "seen").equals("false")){
                notSeenNotificationIds.add(element.getAttribute("id"));
                notSeenNotificationTitles.add(save.getChildFromMapElements(allElements, "title"));
                notSeenNotificationDescriptions.add(save.getChildFromMapElements(allElements, "description"));
            }else{
                seenNotificationIds.add(element.getAttribute("id"));
                seenNotificationTitles.add(save.getChildFromMapElements(allElements, "title"));
                seenNotificationDescriptions.add(save.getChildFromMapElements(allElements, "description"));
            }
        }
    }

    public Map<String, String> getNotificationElementsMapById(String id){
        Map<String, String> elementsMap = new HashMap<String, String>();
        this.doc = save.getDocumentXml(gameName,"functional/phone/notifications");
        Element element = save.getElementById(doc, "notification", id);

        elementsMap.put("title",save.getChildFromElement(element, "title"));
        String content = save.getChildFromElement(element, "content");
        if(content.isEmpty()){
            elementsMap.put("description","");
            elementsMap.put("content",save.getChildFromElement(element, "description"));
        }else{
            elementsMap.put("description",save.getChildFromElement(element, "description"));
            elementsMap.put("content",save.getChildFromElement(element, "content"));
        }
        
        save.changeElementChildValue(gameName,"functional/phone/notifications","notification",id,"seen","true");

        return elementsMap;
    }

    public ArrayList<JPanel> getNotSeenNotificationsPanel(){
        ArrayList<JPanel> list = new ArrayList<>();

        for (int i = 0; i < notSeenNotificationTitles.size(); i++) {
            list.add(createNotificationPanel(notSeenNotificationIds.get(i), notSeenNotificationTitles.get(i), notSeenNotificationDescriptions.get(i)));
        }

        return list;
    }

    public JPanel createNotificationPanel(String id, String title, String description) {
        final String newId = id;
        JPanel notificationPanel = new RoundedPanel();
        notificationPanel.setLayout(new BorderLayout());
        notificationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        notificationPanel.setBackground(new Color(255, 255, 255, 200));
        notificationPanel.setOpaque(false); 
        notificationPanel.setMaximumSize(new Dimension(phonePanel.phoneWidth, 70));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setLineWrap(true);
        descriptionArea.setEditable(false);
        descriptionArea.setFocusable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        descriptionArea.setMaximumSize(new Dimension(phonePanel.phoneWidth, 50));

        MouseAdapter listener = new MouseAdapter() {    
            @Override
            public void mouseClicked(MouseEvent e){
                phonePanel.openNewPage("msg", "portrait", newId);
            }
        };

        // Add the scroll adapter to the description area
        descriptionArea.addMouseListener(scrollAdapter);
        descriptionArea.addMouseMotionListener(scrollAdapter);
        descriptionArea.addMouseListener(listener);

        titleLabel.addMouseListener(scrollAdapter);
        titleLabel.addMouseMotionListener(scrollAdapter);
        titleLabel.addMouseListener(listener);

        notificationPanel.add(titleLabel, BorderLayout.NORTH);
        notificationPanel.add(descriptionArea, BorderLayout.CENTER);
        notificationPanel.addMouseListener(listener);

        return notificationPanel;
    }

    public JPanel getNotificationDetailsPanel(String id) {
        JPanel notificationDetailPanel = new JPanel();
        notificationDetailPanel.setLayout(new BorderLayout());
        notificationDetailPanel.setBounds(5, 0, phonePanel.phoneWidth - 20, phonePanel.phoneHeight - 70 - 50 - 5);
        notificationDetailPanel.setOpaque(false);
    
        Map<String, String> elementsMap = getNotificationElementsMapById(id);
        String title = elementsMap.get("title"); 
        String description = elementsMap.get("description"); 
        String content = elementsMap.get("content"); 

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
    
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
        JLabel descriptionLabel = new JLabel(description);
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    
        headerPanel.add(titleLabel);
        headerPanel.add(descriptionLabel);
    
        JTextArea contentArea = new JTextArea(content);
        contentArea.setFont(new Font("Arial", Font.PLAIN, 14));
        contentArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setEditable(false);
    
        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setOpaque(false);

        notificationDetailPanel.add(headerPanel, BorderLayout.NORTH);

        notificationDetailPanel.add(scrollPane, BorderLayout.CENTER);
    
        return notificationDetailPanel;
    }

    public void createNewNotification(String title, String description, String content){
        this.doc = save.getDocumentXml(gameName,"functional/phone/notifications");
        Element notificationElement = doc.createElement("notification");

        notificationElement.setAttribute("id", save.generateRandomString(10));

        save.createXmlElement(notificationElement,doc,"title",title);
        save.createXmlElement(notificationElement,doc,"description",description);
        save.createXmlElement(notificationElement,doc,"content",content);

        save.createXmlElement(notificationElement,doc,"seen","false");

        doc.getDocumentElement().appendChild(notificationElement);
        save.saveXmlFile(doc, gameName, "functional/phone/notifications");
    }
}

class RoundedPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private int cornerRadius = 15;

    public RoundedPanel() {
        super();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draws the rounded panel with borders.
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }
}
