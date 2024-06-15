package com.jv01.generations.Panels.InventoryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;
import com.jv01.player.Player;
import com.jv01.screens.GameWindowsSize;

public class InteractiveInventory {
    public Save save = new Save();
    public MainGameWindow mainGameWindow;
    public String gameName;
    public JPanel panel;
    public JFrame frame;
    public JPanel overlayPanel;
    public Player player;
    public GameWindowsSize GWS = new GameWindowsSize(true);

    public boolean isInventoryOpen = false;
    List<String> itemPaths = new ArrayList<>();
    List<String> currentItemsKey = new ArrayList<>();
    List<BufferedImage> currentItemsImagesList = new ArrayList<>();
    List<JLabel> currentItemsLabels = new ArrayList<>();
    JPanel infoPanel;

    String hoveredItemName = "";

    public InteractiveInventory(MainGameWindow mainGameWindow) {
        this.mainGameWindow = mainGameWindow;
        this.gameName = mainGameWindow.gameName;
        this.frame = mainGameWindow.frame;
    }

    public void createInventoryPanel() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
        panel.setOpaque(false);
        panel.setBackground(new Color(0, 0, 0, 0));
        frame.add(panel);
    }

    public void clearInventoryPanel() {
        if (overlayPanel != null) {
            frame.remove(overlayPanel);
            overlayPanel = null;
        }
        itemPaths.clear();
        currentItemsImagesList.clear();
        currentItemsLabels.clear();
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        player.canWalk = true;
        hoveredItemName = "";
        isInventoryOpen = false;
    }

    public void open(Player player) {
        this.player = player;

        if (isInventoryOpen) {
            clearInventoryPanel();
        } else {
            clearInventoryPanel();
            player.canWalk = false;
            player.inventory.getInventoryValues();

            overlayPanel = new JPanel();
            overlayPanel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
            overlayPanel.setBackground(new Color(0, 0, 0, 128));
            overlayPanel.setLayout(null);

            BufferedImage originalImage = loadImage(player.inventory.backPic);

            if (originalImage != null) {
                ImageIcon inventoryIcon = new ImageIcon(resizeImageToScreen(originalImage));
                JLabel inventoryLabel = new JLabel(inventoryIcon);

                int x = (GWS.gameWindowWidth - inventoryIcon.getIconWidth()) / 2;
                int y = (GWS.gameWindowHeight - inventoryIcon.getIconHeight()) / 2;
                inventoryLabel.setBounds(x, y, inventoryIcon.getIconWidth(), inventoryIcon.getIconHeight());

                overlayPanel.add(inventoryLabel);
            }

            frame.add(overlayPanel, 0);
            frame.revalidate();
            frame.repaint();

            isInventoryOpen = true;
            addItems();
        }
    }

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Image resizeImageToScreen(BufferedImage originalImage) {
        int screenWidth = GWS.gameWindowWidth;
        int screenHeight = GWS.gameWindowHeight;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        double scaleFactor = Math.min((double) screenWidth / width, (double) screenHeight / height);
        int newWidth = (int) (width * scaleFactor);
        int newHeight = (int) (height * scaleFactor);
        return originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    public void addItems() {
        for (Map.Entry<String, String> entry : player.inventory.itemsPics.entrySet()) {   
            BufferedImage itemImage = loadImage(entry.getValue());
            if(player.inventory.itemsCount.get(entry.getKey()).equals(0))itemImage = null;
            if (itemImage != null) {
                currentItemsImagesList.add(itemImage);
                currentItemsKey.add(entry.getKey());
                ImageIcon itemIcon = new ImageIcon(resizeImageToScreen(itemImage));
                JLabel itemLabel = new JLabel(itemIcon);

                int x = (GWS.gameWindowWidth - itemIcon.getIconWidth()) / 2;
                int y = (GWS.gameWindowHeight - itemIcon.getIconHeight()) / 2;

                itemLabel.setBounds(x, y, itemIcon.getIconWidth(), itemIcon.getIconHeight());
                currentItemsLabels.add(itemLabel);

                overlayPanel.add(itemLabel, 0);
            }
        }

        if (!currentItemsLabels.isEmpty()) {
            JLabel lastLabel = currentItemsLabels.get(currentItemsLabels.size() - 1);
            
            lastLabel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    int moveX = e.getX();
                    int moveY = e.getY();
                    lastLabelMouseMoved(moveX, moveY);
                }
            });
            
            lastLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int clickX = e.getX();
                    int clickY = e.getY();
                    lastLabelMouseClicked(clickX, clickY);
                }
            });
        }

        frame.revalidate();
        frame.repaint();
    }

    private void lastLabelMouseMoved(int x, int y){
        isPixelOpaque(x, y);
    }

    private void lastLabelMouseClicked(int x, int y){
        System.out.println("click on : " + hoveredItemName);
    }

    private boolean isPixelOpaque(int x, int y) {
        disposeInfoPanel();
        for (int i = 0; i < currentItemsImagesList.size(); i++) {
            if(currentItemsImagesList.get(i).equals(null))break;
            BufferedImage itemImage = currentItemsImagesList.get(i);
            int originalX = (int) ((double) x / currentItemsLabels.get(i).getWidth() * itemImage.getWidth());
            int originalY = (int) ((double) y / currentItemsLabels.get(i).getHeight() * itemImage.getHeight());

            if (originalX >= 0 && originalX < itemImage.getWidth() && originalY >= 0 && originalY < itemImage.getHeight()) {
                int pixel = itemImage.getRGB(originalX, originalY);
                int alpha = (pixel >> 24) & 0xff;

                if (alpha != 0) {
                    hoveredItemName = currentItemsKey.get(i);
                    displayInfos(i, x, y);
                    return true;
                }
            }
        }
        return false;
    }

    private void displayInfos(int index, int x, int y) {
        disposeInfoPanel();
    
        infoPanel = new JPanel();
        infoPanel.setBounds(x + GWS.gameWindowWidth / 2 - GWS.gameWindowHeight / 2, y, 50, 40);
        infoPanel.setBackground(new Color(255, 255, 255, 200));
        infoPanel.setLayout(new FlowLayout());
        int itemCount = player.inventory.itemsCount.get(hoveredItemName);
        int itemMax = player.inventory.itemsMax.get(hoveredItemName);
        JLabel infoLabel = new JLabel("<html>"

            + hoveredItemName 
            + "<br>" 
            + itemCount + "/" + itemMax

            + "</html>"
        );
        infoPanel.add(infoLabel);
    
        overlayPanel.add(infoPanel, 0);
        overlayPanel.setComponentZOrder(infoPanel, 0);
    
        frame.revalidate();
        frame.repaint();
    }
    

    private void disposeInfoPanel() {
        if (infoPanel != null) {
            overlayPanel.remove(infoPanel);
            infoPanel = null;
        }
    }
}
