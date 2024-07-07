package com.jv01.generations.Panels.PhonePanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

import java.util.ArrayList;
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

    public List<String> notificationTitles = new ArrayList<>();
    public List<String> notificationDescriptions = new ArrayList<>();

    public List<String> notSeenNotificationTitles = new ArrayList<>();
    public List<String> notSeenNotificationDescriptions = new ArrayList<>();

    public List<String> seenNotificationTitles = new ArrayList<>();
    public List<String> seenNotificationDescriptions = new ArrayList<>();

    public List<String> defaultNotificationTitles = new ArrayList<>();
    public List<String> defaultNotificationDescriptions = new ArrayList<>();

    public Notifications(String gameName){
        this.gameName = gameName;
    }

    public Notifications(PhonePanel phonePanel, MouseAdapter scrollAdapter){
        this.phonePanel = phonePanel;
        this.scrollAdapter = scrollAdapter;
        this.gameName = phonePanel.gameName;

        getNotifications();
    }

    public void getNotifications(){
        notificationTitles.clear();
        notificationDescriptions.clear();

        this.doc = save.getDocumentXml(gameName,"functional/phone/notifications");
        this.elements = save.getElementsByTagName(doc, "notification");

        for (Element element : elements) {
            Map<String, List<String>> allElements = save.getAllChildsFromElement(element);
            if(save.getChildFromMapElements(allElements, "seen").equals("false")){
                notSeenNotificationTitles.add(save.getChildFromMapElements(allElements, "title"));
                notSeenNotificationDescriptions.add(save.getChildFromMapElements(allElements, "description"));
            }else{
                seenNotificationTitles.add(save.getChildFromMapElements(allElements, "title"));
                seenNotificationDescriptions.add(save.getChildFromMapElements(allElements, "description"));
            }
        }

        Element element = save.getElementById(doc, "default", "default");

        defaultNotificationTitles.add(save.getChildFromElement(element, "title"));
        defaultNotificationDescriptions.add(save.getChildFromElement(element, "description"));

        notificationTitles.addAll(seenNotificationTitles);
        notificationTitles.addAll(notSeenNotificationTitles);

        notificationDescriptions.addAll(seenNotificationDescriptions);
        notificationDescriptions.addAll(notSeenNotificationDescriptions);
    }

    public ArrayList<JPanel> getDefaultNotificationsPanel(){
        ArrayList<JPanel> list = new ArrayList<>();

        for (int i = 0; i < defaultNotificationTitles.size(); i++) {
            list.add(createNotificationPanel(defaultNotificationTitles.get(i), defaultNotificationDescriptions.get(i)));
        }

        return list;
    }

    public ArrayList<JPanel> getNotSeenNotificationsPanel(){
        ArrayList<JPanel> list = new ArrayList<>();

        for (int i = 0; i < notSeenNotificationTitles.size(); i++) {
            list.add(createNotificationPanel(notSeenNotificationTitles.get(i), notSeenNotificationDescriptions.get(i)));
        }

        return list;
    }

    public JPanel createNotificationPanel(String title, String description) {
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

        // Add the scroll adapter to the description area
        descriptionArea.addMouseListener(scrollAdapter);
        descriptionArea.addMouseMotionListener(scrollAdapter);

        notificationPanel.add(titleLabel, BorderLayout.NORTH);
        notificationPanel.add(descriptionArea, BorderLayout.CENTER);

        return notificationPanel;
    }

    public void createNewNotification(String title, String description){
        this.doc = save.getDocumentXml(gameName,"functional/phone/notifications");
        Element notificationElement = doc.createElement("notification");

        save.createXmlElement(notificationElement,doc,"title",title);
        save.createXmlElement(notificationElement,doc,"description",description);
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
