package com.jv01.generations;

import javax.swing.*;
import javax.swing.border.Border;

import com.jv01.buildings.Buildings;
import com.jv01.fonctionals.Time;
import com.jv01.player.Player;

import com.jv01.screens.AlertWindow;
import com.jv01.screens.CheatCodeMenu;
import com.jv01.screens.GameMap;
import com.jv01.screens.InfoMenuScreen;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainGameWindow {
    
    public String gameName;
    public String seed;

    public Timer seedTimer;
    public String key="0";

    private JFrame frame;

    private JPanel backgroundPanel; 

    public long[] currentChunk = {0,0};
    boolean isCenterChunk = false;

    public boolean displayChunks = true;

    private int boxSize = 800;
    int cellSize = boxSize / 3;
    BufferedImage img;

    boolean[] completedCell = {false,false,false,false};

    private int updateCounter = 0;

    public Player player;
    Chunks chunk;
    GameMap map;
    InfoMenuScreen infoMenu;
    CheatCodeMenu cheatCodeMenu = new CheatCodeMenu();

    private JLabel coordinatesLabel;
    private JLabel moneyLabel;
    private JLabel dateLabel;

    private JLabel msgLabel;
    private int msgBoxSizeX = 380;
    private int msgBoxSizeY = 100;

    private boolean isInsideBuilding = false;

    private int alertTime = 1000;

    private AlertWindow alertWindow;

    public boolean refresh = false;
    public boolean refreshDisplay = false;

    public Time date;

    public MainGameWindow(String gameName, String seed){
        this.gameName = gameName;
        this.seed = seed;
        this.date = new Time(gameName);

        seedTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCounter++;
                if (updateCounter >= player.movementType) {
                    updatePlayerLocation();
                    displayAlert();
                    updateDateTextLabels();
                    updateCounter = 0;
                }
            }
        });
        seedTimer.setRepeats(true);
        seedTimer.start();

        frame = new JFrame("Main Game Window");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(true);
        
        frame.getContentPane().setBackground(new java.awt.Color(90, 90, 90));

        player = new Player(this);
        currentChunk = player.chunk;
        isInsideBuilding = player.isInsideBuilding;

        frame.addKeyListener(player.keyBord.keyListener);

        player.positionX = (boxSize - player.playerSize) / 2;
        player.positionY = (boxSize - player.playerSize) / 2;

        player.positionX = boxSize / 2;
        player.positionY = boxSize / 2;
        
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);

        showMainGameWindow();
    }

    public void showMainGameWindow() {
        frame.getContentPane().removeAll();

        key = getKey();

        createBackgroundPanel();
        player.inventory.createInventoryPanel(frame);
        //currentChunk = player.chunk;

        addCoordinatesLabel();
        addMoneyLabel();
        addDateLabel();

        addMsgLabel();

        addAlertArea();

        backgroundPanel.add(player.playerLabel);

        buildChunk();
        
        updateLabels();
    }

    private void addCoordinatesLabel(){
        coordinatesLabel = new JLabel();
        coordinatesLabel.setForeground(Color.BLACK);

        int labelX = backgroundPanel.getX();
        int labelY = backgroundPanel.getY() - 45;

        coordinatesLabel.setBounds(labelX, labelY, 200, 40);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        coordinatesLabel.setFont(labelFont);

        frame.add(coordinatesLabel);
    }

    private void addMoneyLabel(){
        moneyLabel = new JLabel();
        moneyLabel.setForeground(Color.BLACK);

        int labelX = backgroundPanel.getX() + 300;
        int labelY = backgroundPanel.getY() - 45;

        moneyLabel.setBounds(labelX, labelY, 200, 40);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        moneyLabel.setFont(labelFont);

        frame.add(moneyLabel);
    }

    private void addDateLabel(){
        dateLabel = new JLabel();
        dateLabel.setForeground(Color.BLACK);

        int labelX = backgroundPanel.getX() + 625;
        int labelY = backgroundPanel.getY() - 35;

        dateLabel.setBounds(labelX, labelY, 200, 40);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        dateLabel.setFont(labelFont);

        frame.add(dateLabel);
    }

    private void addMsgLabel(){
        msgLabel = new JLabel();
        msgLabel.setOpaque(true);
        msgLabel.setForeground(new Color(0, 0, 0));
        msgLabel.setBackground(new Color(150, 150, 150, 155));

        Border bord= BorderFactory.createLineBorder(Color.black,2);
        msgLabel.setBorder(bord);

        int labelX = (boxSize/2) - (msgBoxSizeX/2);
        int labelY = 10;

        msgLabel.setBounds(labelX, labelY , msgBoxSizeX, msgBoxSizeY);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        msgLabel.setFont(labelFont);
        
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        msgLabel.setVerticalAlignment(SwingConstants.CENTER);

        backgroundPanel.add(msgLabel);
        msgLabel.setVisible(false);
    }

    private void addAlertArea(){
        alertWindow = new AlertWindow(backgroundPanel,boxSize);
    }

    private void createBackgroundPanel(){
        backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon(chunk.backPic);
                Image img = backgroundImage.getImage();
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        
        backgroundPanel.setLayout(null);
        backgroundPanel.setBounds(0, 0, boxSize, boxSize); 

        Toolkit toolkit = Toolkit.getDefaultToolkit();

        Dimension screenSize = toolkit.getScreenSize();

        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        backgroundPanel.setLocation((screenWidth - boxSize)/2,(screenHeight - boxSize)/2);

        frame.add(backgroundPanel);
    }

    private void updatePlayerLocation() {

        int lastX = player.positionX;
        int lastY = player.positionY;

        player.updatePlayerLocation();

        if (isPointInRestrictedArea(player.positionX,player.positionY)) {
            player.positionX = lastX;
            player.positionY = lastY;
        }

        boolean displaySpam = false;
        boolean isTool = false;
        boolean isItem = false;
        boolean isDealer = false;

        String spam = "";
        Tools tool = new Tools(gameName, 0, 0);
        Items item = new Items(gameName,0);
        Dealers dealer = new Dealers(gameName, 0);

        if(!isInsideBuilding){
            for(Buildings b : chunk.triggerableBuilding){
                int bX = (b.cell[0]+1)*cellSize-(cellSize/2);
                int bY = (b.cell[1]+1)*cellSize-(cellSize/2);

                int distance = getDistanceFromPlayer(bX, bY);

                if(distance <= cellSize/2){

                    //openMsgLabels("'e' Pour entrer dans la " +b.name);
                    spam = "'e' Pour entrer dans la " +b.name;
                    displaySpam = true;
                    if(player.keyBord.interactKeyPressed)enterBuilding();
                    
                }else{
                    //coloseMsgLabels();
                    //displaySpam = false;
                }            
            }
        }
            
    
            for(Object[] trigEvent: chunk.trigerEvents){

                int[] position = (int[]) trigEvent[0];

                if(trigEvent[1] == "tool" && !displaySpam){
                    tool = (Tools) trigEvent[2];

                    int distance = getDistanceFromPlayer(position[0], position[1]);

                    if(distance < (int) trigEvent[3]){
                        displaySpam = true;
                        isTool = true;
                        spam = tool.spam;
                        break;
                    }else{
                        //isTool = false;
                        //displaySpam = false;
                    }
                }else if(trigEvent[1] == "item" && !displaySpam){
                    item = (Items) trigEvent[2];

                    int distance = getDistanceFromPlayer(position[0], position[1]);

                    if(distance < item.size[0] && item.isExist){
                        displaySpam = true;
                        isItem = true;
                        spam = item.spam;
                        break;
                    }else{
                        //isItem = false;
                        //displaySpam = false;
                    }
                }else if(trigEvent[1] == "dealer" && !displaySpam){
                    dealer = (Dealers) trigEvent[2];

                    int distance = getDistanceFromPlayer(position[0], position[1]);

                    if(distance < dealer.size[0]){
                        displaySpam = true;
                        isDealer = true;
                        spam = dealer.spam;
                        break;
                    }else{
                        //isItem = false;
                        //displaySpam = false;
                    }
                }  
                //System.out.println("spam");
            }

            if(displaySpam){
                openMsgLabels(spam);

                if(isTool){
                    tool.interact(player);
                    refresh = tool.refresh;
                    refreshDisplay = tool.refreshDisplay;
                }else if(isItem){
                    item.interact(player);
                    refreshDisplay = item.refreshDisplay;
                }else if(isDealer){
                    dealer.interact(player);
                    refreshDisplay = dealer.refreshDisplay;
                }

            }else{
                coloseMsgLabels();
            }
        

        if(player.keyBord.mapKeyPressed){
            player.keyBord.mapKeyPressed = false;
            map = new GameMap(gameName,player);  
        }

        if(player.keyBord.menuKeyPressed){
            player.keyBord.menuKeyPressed = false;
            infoMenu = new InfoMenuScreen(gameName,player);
        }

        if(player.keyBord.quitKeyPressed){
            player.keyBord.quitKeyPressed = false;
        }

        if(player.keyBord.cheatCodeMenuKeyPressed){
            player.keyBord.cheatCodeMenuKeyPressed = false;
            cheatCodeMenu.open(this);
        }

        if(cheatCodeMenu.frame.isVisible()){
            refresh = cheatCodeMenu.refresh;
            refreshDisplay = cheatCodeMenu.refreshDisplay;
            cheatCodeMenu.refresh = false;
            cheatCodeMenu.refreshDisplay = false;
        }   

        if(player.positionX < 0)changeChunk("left");
        if(player.positionX > (boxSize))changeChunk("right");
        if(player.positionY < 0)changeChunk("up");
        if(player.positionY > (boxSize))changeChunk("down");

        player.playerLabel.setBounds(player.positionX - (player.playerSize/2), player.positionY - (player.playerSize/2), player.playerSize, player.playerSize);

        updatePositionTextLabels();

        if(refreshDisplay){
            refreshDisplay = false;
            updateLabels();
            cheatCodeMenu.close();
        }
        
        if(refresh){
            refresh = false;
            showMainGameWindow();
            updateLabels();
            cheatCodeMenu.close();
        }

        frame.revalidate();
        frame.repaint();
    }

    private boolean isPointInRestrictedArea(int x, int y) {
        for (int[][] area : chunk.restrictedAreas) {
            int x1 = area[0][0];
            int y1 = area[0][1];
            int x2 = area[1][0];
            int y2 = area[1][1];

            if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
                return true;
            }
        }
        return false;
    }

    public int getDistanceFromPlayer(int x, int y){
        int distance = 0;

        distance = (int)Math.sqrt(((x-player.positionX)*(x-player.positionX))+((y-player.positionY)*(y-player.positionY)));

        return distance;
    }

    public void buildChunk(){
        chunk = null;
        chunk = new Chunks(currentChunk, seed, gameName, boxSize, backgroundPanel,isInsideBuilding,displayChunks);
    }

    public void enterBuilding(){
        coloseMsgLabels();
        changeChunk("inBuilding");
    }

     
    public String hash(String seed, long value1, long value2) {
        try {
            String input = seed + value1 + value2;
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public void changeChunk(String direction){
        if(!isInsideBuilding){
            switch (direction) {
                case "up":
                    currentChunk[0]--;
                    player.positionX = (boxSize - player.playerSize) / 2;
                    player.positionY = (boxSize - player.playerSize);
                    break;
                case "down":
                    currentChunk[0]++;
                    player.positionX = (boxSize - player.playerSize) / 2;
                    player.positionY = 0;
                    break;
                case "left":
                    currentChunk[1]--;
                    player.positionX = (boxSize - player.playerSize);
                    player.positionY = (boxSize - player.playerSize) / 2;
                    break;
                case "right":
                    currentChunk[1]++;
                    player.positionX = 0;
                    player.positionY = (boxSize - player.playerSize) / 2;
                    break;

                case "inBuilding":
                    isInsideBuilding = true;
                    player.positionX = (boxSize) / 2;
                    player.positionY = (boxSize) / 2;
                    break;

                case "TP":
                    isInsideBuilding = false;
                    displayChunks = true;
                    player.positionX = (boxSize) / 2;
                    player.positionY = (boxSize) / 2;
                    break;
                
                case "chargeChunk":
                    isInsideBuilding = false;
                    displayChunks = false;
                    player.positionX = (boxSize) / 2;
                    player.positionY = (boxSize) / 2;
                    break;
            
                default:
                    break;
            }
        }else{
            isInsideBuilding = false;
            player.positionX = (boxSize) / 2;
            player.positionY = (boxSize) / 2;
        }
        showMainGameWindow();
        updateLabels();
    }


    public String getKey(){
        String key = hash(seed,currentChunk[0],currentChunk[1]);
        return(key);
    };

    public void updateLabels(){
        updatePositionTextLabels();
        updateMoneyTextLabels();
        updateDateTextLabels();
    }

    public void updatePositionTextLabels(){
        coordinatesLabel.setText(
            "<html>"+
                "Chunk: (" + currentChunk[0] + ", " + currentChunk[1] + ")<br>"+
                "Position: ("+(player.positionX)+", "+(player.positionY)+")"+
            "</html>"
        );
    }

    public void updateMoneyTextLabels(){
        moneyLabel.setText(
            "<html>"+
                "Argent: " + player.money + '€'+
            "</html>"
        );
    }

    public void updateDateTextLabels(){
        dateLabel.setText(
            "<html>"+
                "Date: " + date.getDate() + " " + date.getHour()+
            "</html>"
        );
    }

    public void openMsgLabels(String msg){
        msgLabel.setText(
            "<html>"+
                msg+
            "</html>"
        );
        msgLabel.setVisible(true);
    }
    public void coloseMsgLabels(){
        msgLabel.setText(
            ""
        );
        msgLabel.setVisible(false);
    }

    public void displayAlert(){
        if(player.displayAlert){
            alertWindow.showTimedAlert(player.alertMessage, alertTime);
            player.displayAlert = false;
        }
    }
}
