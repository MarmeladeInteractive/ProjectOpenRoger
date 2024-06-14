package com.jv01.generations.Panels.InventoryPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    private boolean isInventoryOpen = false;
    List<String> itemPaths = new ArrayList<>();
    List<BufferedImage> currentItemsImagesList = new ArrayList<>();
    List<JLabel> currentItemsLabels = new ArrayList<>();
    JPanel infoPanel;

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
    }

    public void open(Player player) {
        this.player = player;

        if (isInventoryOpen) {
            clearInventoryPanel();
            isInventoryOpen = false;
        } else {
            clearInventoryPanel();
            player.canWalk = false;

            itemPaths.add("demo/img/inventory/inventoryItems/Apple.png");
            itemPaths.add("demo/img/inventory/inventoryItems/Waste.png");
            itemPaths.add("demo/img/inventory/inventoryItems/Chocolatine.png");
            itemPaths.add("demo/img/inventory/inventoryItems/Croissant.png");

            overlayPanel = new JPanel();
            overlayPanel.setBounds(0, 0, GWS.gameWindowWidth, GWS.gameWindowHeight);
            overlayPanel.setBackground(new Color(0, 0, 0, 128));
            overlayPanel.setLayout(null);

            BufferedImage originalImage = loadImage("demo/img/inventory/Inventory.png");

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
            addItems(itemPaths);
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

    public void addItems(List<String> itemPaths) {
        for (String path : itemPaths) {
            BufferedImage itemImage = loadImage(path);
            if (itemImage != null) {
                currentItemsImagesList.add(itemImage);
                ImageIcon itemIcon = new ImageIcon(resizeImageToScreen(itemImage));
                JLabel itemLabel = new JLabel(itemIcon);

                int x = (GWS.gameWindowWidth - itemIcon.getIconWidth()) / 2;
                int y = (GWS.gameWindowHeight - itemIcon.getIconHeight()) / 2;

                itemLabel.setBounds(x, y, itemIcon.getIconWidth(), itemIcon.getIconHeight());
                currentItemsLabels.add(itemLabel);

                overlayPanel.add(itemLabel, 0);
            }
        }

        currentItemsLabels.get(currentItemsLabels.size() - 1).addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int moveX = e.getX();
                int moveY = e.getY();
                isPixelOpaque(moveX, moveY);
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    private boolean isPixelOpaque(int x, int y) {
        disposeInfoPanel();
        for (int i = 0; i < currentItemsImagesList.size(); i++) {
            BufferedImage itemImage = currentItemsImagesList.get(i);
            int originalX = (int) ((double) x / currentItemsLabels.get(i).getWidth() * itemImage.getWidth());
            int originalY = (int) ((double) y / currentItemsLabels.get(i).getHeight() * itemImage.getHeight());

            if (originalX >= 0 && originalX < itemImage.getWidth() && originalY >= 0 && originalY < itemImage.getHeight()) {
                int pixel = itemImage.getRGB(originalX, originalY);
                int alpha = (pixel >> 24) & 0xff;

                if (alpha != 0) {
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
        infoPanel.setBounds(x + GWS.gameWindowWidth / 2 - GWS.gameWindowHeight / 2, y, 50, 20);
        infoPanel.setBackground(new Color(255, 255, 255, 200));
        infoPanel.setLayout(new FlowLayout());

        JLabel infoLabel = new JLabel("Item: " + index);
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
