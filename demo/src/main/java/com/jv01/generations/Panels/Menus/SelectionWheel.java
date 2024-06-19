package com.jv01.generations.Panels.Menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Arc2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.screens.GameWindowsSize;

public class SelectionWheel extends JPanel {
    public Save save = new Save();
    private List<String> options = new ArrayList<>();
    private int centreX = 0;
    private int centreY = 0;
    public SelectionWheelIcons selectionWheelIcons;
    public JFrame frame;
    public JPanel panel;
    public JPanel backPanel;
    public JLabel selectionWheelLabel;

    public boolean isOpen = false;

    public boolean isIconSelected = false;
    public String iconSelectedId = null;
    public String interactType = null;

    public GameWindowsSize GWS = new GameWindowsSize(true);

    public int extWheelRadius = 200;
    public int intWheelRadius = 100;
    public int iconSize = 40;
    public Color wheelColor = new Color(226,233,255,86);

    public SelectionWheel(String gameName, JFrame mainFrame){
        this.frame = mainFrame;
        this.selectionWheelIcons = new SelectionWheelIcons(gameName);

        getSelectionWheelValues(gameName);
    }

    public void getSelectionWheelValues(String gameName){
        Document doc = save.getDocumentXml(gameName,"functional/selectionWheel/selectionWheel");
        Element element = save.getElementById(doc, "options", "options");

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        this.extWheelRadius = Integer.parseInt(save.getChildFromMapElements(allElements,"extWheelRadius"));
        this.intWheelRadius = Integer.parseInt(save.getChildFromMapElements(allElements,"intWheelRadius"));
        this.iconSize = Integer.parseInt(save.getChildFromMapElements(allElements,"iconSize"));
    }

    public String getSelectedInteraction()
    {
        return iconSelectedId;
    }

    public void createSelectionWheelPanel(){
        setLayout(null);
        setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
        frame.add(this);
    }

    public void openSelectionWheel(int x, int y, String type, List<String> options) {
        this.interactType = type;
        int ajustedX = x;
        int ajustedY = y;
    
        if (x + extWheelRadius > GWS.gameWindowWidth) {
            ajustedX = GWS.gameWindowWidth - extWheelRadius;
        }
        if (y + extWheelRadius > GWS.gameWindowHeight) {
            ajustedY = GWS.gameWindowHeight - extWheelRadius;
        }
    
        if (x - extWheelRadius < 0) {
            ajustedX = extWheelRadius;
        }
        if (y - extWheelRadius < 0) {
            ajustedY = extWheelRadius;
        }

        this.centreX = ajustedX;
        this.centreY = ajustedY;
        this.options.clear();
        this.options.addAll(options);
        this.isOpen = true;
        this.isIconSelected = false;
        this.iconSelectedId = null;
        repaint();
    }
    
    
    public void clearSelectionWheel() {
        repaint();
    }

    public void resetSelectionWheel(){
        isOpen = false;
        isIconSelected = false;
        iconSelectedId = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!isOpen || options.isEmpty()) {
            return;
        }
    
        Graphics2D g2d = (Graphics2D) g.create();
        final int diametre = extWheelRadius * 2;
        final double angleSection = 360.0 / options.size();
    
        Area wheelArea = new Area();
    
        for (int i = 0; i < options.size(); i++) {
            double startAngle = i * angleSection;
            Arc2D.Double segment = new Arc2D.Double(centreX - extWheelRadius, centreY - extWheelRadius, diametre, diametre, startAngle, angleSection, Arc2D.PIE);
            wheelArea.add(new Area(segment));
        }
    
        Ellipse2D.Double centralHole = new Ellipse2D.Double(centreX - intWheelRadius, centreY - intWheelRadius, intWheelRadius * 2, intWheelRadius * 2);
        wheelArea.subtract(new Area(centralHole));
    
        g2d.setClip(wheelArea);
        g2d.setColor(wheelColor);
        g2d.fill(wheelArea);
    
        for (int i = 0; i < options.size(); i++) {
            ImageIcon icon = selectionWheelIcons.getIconById(options.get(i));
            Image image = icon.getImage();
        
            double iconAngle;
            if (options.size() == 1) {
                iconAngle = Math.toRadians(-90);
            } else {
                iconAngle = Math.toRadians((i * angleSection) - angleSection);
            }
        
            int iconRadius = (intWheelRadius + extWheelRadius) / 2;
            int iconX = (int) (centreX + Math.cos(iconAngle) * iconRadius) - (iconSize / 2);
            int iconY = (int) (centreY + Math.sin(iconAngle) * iconRadius) - (iconSize / 2);
        
            g2d.drawImage(image, iconX, iconY, iconSize, iconSize, null);
        }
    
        g2d.dispose();
    }

    public void checkClickOnIcon(int x, int y) {
        double dx = x - centreX;
        double dy = y - centreY;
        double distance = Point2D.distance(centreX, centreY, x, y);

        if (distance >= intWheelRadius && distance <= extWheelRadius) {
            double angle = Math.toDegrees(Math.atan2(dy, dx)) + 180;
            int iconIndex = (int) Math.floor((angle) / (360.0 / options.size()));

            switch (options.size()) {
                case 2:
                    iconIndex = (int) Math.floor((angle + 90 +180) / (360.0 / options.size()));
                    iconIndex = (iconIndex + (options.size()-1)) % options.size();
                    break;
                case 4:
                    iconIndex = (int) Math.floor((angle + 45) / (360.0 / options.size()));
                    iconIndex = (iconIndex + (options.size()-1)) % options.size();
                    break;
                case 5:
                    iconIndex = (iconIndex + (options.size()-1)) % options.size();
                    break;
                case 6:
                    iconIndex = (int) Math.floor((angle - 30) / (360.0 / options.size()));
                    iconIndex = (iconIndex + (options.size()-1)) % options.size();
                    break;
                case 7:
                    iconIndex = (iconIndex + (options.size()-1) - 1) % options.size();
                    break;
                default: //ok pour 1, 3
                    break;
            }
            
            iconClicked(iconIndex);
        }
    }

    private void iconClicked(int iconIndex) {
        isIconSelected = true;
        iconSelectedId = options.get(iconIndex);
        clearSelectionWheel();
    }
}