package com.jv01.screens.Windows;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.jv01.fonctionals.Save;
import com.jv01.generations.MainGameWindow;
import com.jv01.generations.Panels.PhonePanel.PhonePanel;
import com.jv01.player.Player;
import com.jv01.screens.GameWindowsSize;
import com.jv01.screens.ShowNewWindow;

import org.w3c.dom.Node;

import javax.swing.*;

import java.awt.*;

import java.util.HashMap;
import java.util.Map;

import java.awt.event.KeyListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Chunk01 {
    public int[] cellCoords;
    public int biome;
    public ImageIcon imageIcon = null;
   

    public Chunk01(int[] cellCoords, int biome, ImageIcon imageIcon) {
        this.cellCoords = cellCoords;
        this.biome = biome;

        this.imageIcon = imageIcon;
    }
}

public class GameMap extends JPanel {
    public static Save save = new Save();
    public Document doc;
    public Player player;

    MainGameWindow mainGameWindow;
    PhonePanel phonePanel;

    public GameWindowsSize GWS = new GameWindowsSize(true);

    private JPanel newWindowPanel;

    private JButton zoomInButton;
    private JButton zoomOutButton;
    private int zoomMax = 50;
    private int zoomMin = 10;
    
    public String[] buildingsIcons = {
        "demo\\img\\buildings\\partyHouse01.png",
        "demo\\img\\buildings\\emptyProperty01.png",
        "demo\\img\\buildings\\cityHall01.png",
        "demo\\img\\buildings\\printingPress01.png",
        "demo\\img\\buildings\\bakery01.png",
        "demo\\img\\buildings\\store01.png",
        "demo\\img\\buildings\\abandonedHouse01.png",
        "demo\\img\\buildings\\house01.png",
        "demo\\img\\buildings\\corpo01.png",
        "demo\\img\\buildings\\bar01.png",
        "demo\\img\\buildings\\farm01.png",
        "demo\\img\\buildings\\farm01.png",
    };

    public KeyListener keyListener;
    public boolean mapKeyPressed;

    private static int grideSize = 50;

    public int boxSize = 800;
    public int cellSize = 0;

    public int phoneWidth;
    public int phoneHeight;

    private Map<int[], Chunk01> chunks;

    public GameMap(MainGameWindow mainGameWindow) {
        this.mainGameWindow = mainGameWindow;
        this.player = mainGameWindow.player;
        //this.newWindowPanel = showNewWindow.newWindowPanel; 
        //this.newWindowPanel.setLayout(null);
        this.phonePanel = mainGameWindow.phonePanel;
        this.phoneWidth = (int)(this.phonePanel.phoneWidth*this.phonePanel.phoneScale);
        this.phoneHeight = (int)(this.phonePanel.phoneHeight*this.phonePanel.phoneScale);;

        this.newWindowPanel = mainGameWindow.phonePanel.screenPanel;
        this.boxSize = GWS.boxSize;
        this.cellSize = boxSize/grideSize;

        doc = save.getDocumentXml(mainGameWindow.gameName, "chunks");
        chunks = new HashMap<>();
        
        loadChunksFromDocument(doc, player.chunk);

        zoomInButton = new JButton("Zoom In (+)");
        zoomInButton.setBounds(10, 10, 120, 30);
        zoomInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomIn();
            }
        });
        newWindowPanel.add(zoomInButton);

        zoomOutButton = new JButton("Zoom Out (-)");
        zoomOutButton.setBounds(140, 10, 120, 30);
        zoomOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                zoomOut();
            }
        });
        newWindowPanel.add(zoomOutButton);

        this.setOpaque(false);
        this.setSize(phoneHeight, phoneWidth);
        newWindowPanel.add(this);
    }

    private void loadChunksFromDocument(Document doc, long[] currentChunk) {
        NodeList chunkNodes = doc.getElementsByTagName("chunk");
        grideSize=zoomMax;
        for (int i = 0; i < chunkNodes.getLength(); i++) {
            if (chunkNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element chunkElement = (Element) chunkNodes.item(i);

                int[] cellCoords = save.stringToIntArray(save.getChildFromElement(chunkElement, "cell"));
                boolean displayOnMap = Boolean.parseBoolean(save.getChildFromElement(chunkElement, "displayOnMap"));
                int biome01 = Integer.parseInt(save.getChildFromElement(chunkElement, "biome"));
                int number = Integer.parseInt(save.getChildFromElement(chunkElement, "number"));
                int buildingType = -1;
                if(number > 0){
                    int[] buildingsType = save.stringToIntArray(save.getChildFromElement(chunkElement, "buildingsTypes"));
                    buildingType = buildingsType[0];
                }
                
                int x = cellCoords[0];
                int y = cellCoords[1];

                if(displayOnMap && ((x>(currentChunk[0]-grideSize/2))&&(x<(currentChunk[0]+grideSize/2)))&&((y>(currentChunk[1]-grideSize/2))&&(y<(currentChunk[1]+grideSize/2)))){
                    x = cellCoords[0]- (int)currentChunk[0];
                    y = cellCoords[1]- (int)currentChunk[1];

                    ImageIcon icon = getIcon(buildingType);
                    
                    Chunk01 chunk = new Chunk01(cellCoords,biome01, icon);
                    addChunk(new int[]{x, y}, chunk);
                }            
            }
        }
        grideSize = (int)Math.sqrt(chunkNodes.getLength());
        if(grideSize>=zoomMax)grideSize=zoomMax;
        if(grideSize<=zoomMin)grideSize=zoomMin;
        zoomIn();
    }

    public void addChunk(int[] cellCoords, Chunk01 chunk) {
        chunks.put(cellCoords, chunk);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    
        for (Map.Entry<int[], Chunk01> entry : chunks.entrySet()) {
            int[] cellCoords = entry.getKey();
            Chunk01 chunk = entry.getValue();
    
            int x = cellCoords[0] * cellSize + phoneWidth/2 - cellSize/2;
            int y = cellCoords[1] * cellSize + phoneHeight/2 - cellSize/2;
    
            g.setColor(getBiomeColor(chunk.biome));
            g.fillRect(y, x, cellSize, cellSize);
    
            if (chunk.imageIcon != null) {
                Image scaledImage = chunk.imageIcon.getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_AREA_AVERAGING);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                scaledIcon.paintIcon(this, g, y, x);
            }
    
            if (cellCoords[0] == cellCoords[1] && cellCoords[0] == 0) {
                Image scaledImage = player.playerIcon.getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_AREA_AVERAGING);
                new ImageIcon(scaledImage).paintIcon(this, g, y, x);
            }
        }
    }
    
    

    private Color getBiomeColor(int biome) {
        Color color;
        switch (biome) {
            case 0:
                color = new Color(3,70,0);
                break;
            case 1:
                color = new Color(8,128,3);
                break;

            case 2:
                color = new Color(11,180,3);
                break;
            case 3:
                color = new Color(15,247,3);
                break;
            case 4:
                color = new Color(203,255,200);
                break;

            case 5:
                color = new Color(166,166,166);
                break;
            case 6:
                color = new Color(100,100,100);
                break;
            case 7:
                color = new Color(61,61,61);
                break;
        
            default:
                color = new Color(0,0,0);
                break;
        }
        return color;
    }

    private ImageIcon getIcon(int buildingType) {
        ImageIcon imageIcon01 = null;
        ImageIcon imageIcon = null;
        cellSize = 50;
        if(buildingType>=0 && buildingType!=7){
            imageIcon01 = new ImageIcon(buildingsIcons[buildingType]);
            Image scaledImage = imageIcon01.getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(scaledImage);
        }
        return imageIcon;
    }

    private void zoomIn() {
        if (grideSize > zoomMin) {
            grideSize -= 10;
            if(grideSize<=zoomMin)grideSize=zoomMin;
            cellSize = boxSize / grideSize;
            repaint();
        }
        mainGameWindow.phonePanel.focusOnMainFrame();
    }

    private void zoomOut() {
        if (grideSize < zoomMax) {
            grideSize += 10;
            if(grideSize>=zoomMax)grideSize=zoomMax;
            cellSize = boxSize / grideSize;
            repaint();
        }
        mainGameWindow.phonePanel.focusOnMainFrame();
    }
}
