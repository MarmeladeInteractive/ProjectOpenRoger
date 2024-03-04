package com.jv01.generations;

import javax.swing.*;
import javax.swing.border.Border;

import com.jv01.buildings.Buildings;
import com.jv01.fonctionals.Time;
import com.jv01.generations.Panels.BackgroundPanel;
import com.jv01.generations.Panels.FrontPanel;
import com.jv01.generations.Panels.NightPanel;
import com.jv01.generations.Panels.JoystickPanel.JoystickPanel;
import com.jv01.generations.Panels.PhonePanel.PhonePanel;
import com.jv01.player.Player;

import com.jv01.screens.AlertWindow;
import com.jv01.screens.CheatCodeMenu;
import com.jv01.screens.GameWindowsSize;
import com.jv01.screens.InfoMenuScreen;
import com.jv01.screens.Menus.SelectionWheel;
import com.jv01.screens.Windows.GameMap;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MainGameWindow{
    
    public String gameName;
    public String seed;
    public boolean cheatCodesEnabled;

    public GameWindowsSize GWS = new GameWindowsSize(true);

    public Timer seedTimer;
    public String key="0";

    public JFrame frame;

    public FrontPanel frontPanel;
    public BackgroundPanel backgroundPanel;
    public NightPanel nightPanel;
    public PhonePanel phonePanel;
    public JoystickPanel joystickPanel;

    public long[] currentChunk = {0,0};
    boolean isCenterChunk = false;

    public boolean displayChunks = true;

    BufferedImage img;

    boolean[] completedCell = {false,false,false,false};

    private int updateCounter = 0;

    public Player player;
    public Chunks chunk;
    public GameMap map;
    InfoMenuScreen infoMenu;
    CheatCodeMenu cheatCodeMenu = new CheatCodeMenu();

    public SelectionWheel selectionWheel;

    private JLabel msgLabel;
    private int msgBoxSizeX = 380;
    private int msgBoxSizeY = 100;

    public boolean isInsideBuilding = false;

    public int arcadeGameId = 0;
    public String newWindowId = "";

    public String environment;

    private int alertTime = 1000;

    private AlertWindow alertWindow;

    public boolean refresh = false;
    public boolean refreshDisplay = false;

    public Time date;

    public MainGameWindow(String gameName, String seed, boolean cheatCodesEnabled){
        this.gameName = gameName;
        this.seed = seed;
        this.cheatCodesEnabled = cheatCodesEnabled;
        
        this.date = new Time(gameName);

        seedTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCounter++;
                if (updateCounter >= player.movementType) {
                    updatePlayerLocation();
                    displayAlert();
                    updateDate();
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

        frame.addKeyListener(player.inputsManager.keyListener);
        frame.addMouseMotionListener(player.inputsManager.mouseAdapter);
        frame.addMouseListener(player.inputsManager.mouseListener);

        player.positionX = (GWS.gameWindowWidth - player.playerSize) / 2;
        player.positionY = (GWS.gameWindowHeight - player.playerSize) / 2;

        player.positionX = GWS.gameWindowWidth / 2;
        player.positionY = GWS.gameWindowHeight / 2;

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.setVisible(true);
        
        this.phonePanel = new PhonePanel(this);
        this.joystickPanel = new JoystickPanel(this);
        this.frontPanel = new FrontPanel(this);
        this.backgroundPanel = new BackgroundPanel(this);
        this.nightPanel = new NightPanel(this);

        this.selectionWheel = new SelectionWheel(this);

        showMainGameWindow();
    }

    public void showMainGameWindow() {
        GWS = new GameWindowsSize(!isInsideBuilding);
        restartFrame();

        buildChunk();

        player.playerLabel.setVisible(true);
        player.canWalk = true;
    }

    public void restartFrame(){
        frame.getContentPane().removeAll();
        key = getKey();
 
        if(displayChunks){
            phonePanel.createPhonePanel();
            phonePanel.createPhonePanelPortrait();
            joystickPanel.createJoystickPanel();
            frontPanel.createFrontPanel();
            nightPanel.createNightPanel();
        }

        backgroundPanel.createBackgroundPanel(GWS);

        addMsgLabel();

        addAlertArea();

        updateLabels();
        
        backgroundPanel.panel.add(player.playerLabel);
        player.playerLabel.setVisible(false);
    }

    private void addMsgLabel(){
        msgLabel = new JLabel();
        msgLabel.setOpaque(true);
        msgLabel.setForeground(new Color(0, 0, 0));
        msgLabel.setBackground(new Color(150, 150, 150, 155));

        Border bord= BorderFactory.createLineBorder(Color.black,2);
        msgLabel.setBorder(bord);

        int labelX = (GWS.gameWindowWidth/2) - (msgBoxSizeX/2);
        int labelY = 10;

        msgLabel.setBounds(labelX, labelY , msgBoxSizeX, msgBoxSizeY);

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        msgLabel.setFont(labelFont);
        
        msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        msgLabel.setVerticalAlignment(SwingConstants.CENTER);

        backgroundPanel.panel.add(msgLabel);
        msgLabel.setVisible(false);
    }

    private void addAlertArea(){
        alertWindow = new AlertWindow(backgroundPanel.panel,GWS.gameWindowWidth);/////////////////ff
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
        boolean isArcade = false;
        boolean isNpc = false;

        String spam = "";
        Tools tool = new Tools(gameName, 0, 0);
        Items item = new Items(gameName,0);
        Dealers dealer = new Dealers(gameName, 0);
        Arcades arcade = new Arcades(gameName, 0, 0);
        Npcs npc = new Npcs(gameName);

        if(!isInsideBuilding){
            for(Buildings b : chunk.triggerableBuilding){
                int bX = (b.cell[0]+1)*GWS.cellWidth-(GWS.cellWidth/2);
                int bY = (b.cell[1]+1)*GWS.cellHeight-(GWS.cellHeight/2);

                int distance = getDistanceFromPlayer(bX, bY);

                if(distance <= (b.dimension[0]+b.dimension[1])/2){

                    //openMsgLabels("'e' Pour entrer dans la " +b.name);
                    spam = "'e' Pour entrer dans la " +b.name;
                    displaySpam = true;
                    if(player.inputsManager.interactKeyPressed)enterBuilding();
                    
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
                }else if(trigEvent[1] == "arcade" && !displaySpam){
                    arcade = (Arcades) trigEvent[2];

                    int distance = getDistanceFromPlayer(position[0], position[1]);

                    if(distance < arcade.size[0]){
                        displaySpam = true;
                        isArcade = true;
                        spam = arcade.spam;
                        break;
                    }else{
                        //isItem = false;
                        //displaySpam = false;
                    }
                }else if(trigEvent[1] == "npc" && !displaySpam){
                    npc = (Npcs) trigEvent[2];

                    int distance = getDistanceFromPlayer(position[0], position[1]);

                    if(distance < 50){
                        displaySpam = true;
                        isNpc = true;
                        spam = String.valueOf(npc.name);
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
                    tool.interact(this);
                    refresh = tool.refresh;
                    refreshDisplay = tool.refreshDisplay;
                }else if(isItem){
                    item.interact(player);
                    refreshDisplay = item.refreshDisplay;
                }else if(isDealer){
                    dealer.interact(player);
                    refreshDisplay = dealer.refreshDisplay;
                }else if(isArcade){
                    arcade.interact(this);
                    refresh = arcade.refresh;
                    refreshDisplay = arcade.refreshDisplay;
                }else if(isNpc){
                    
                }

            }else{
                coloseMsgLabels();
            }
        

        if(player.inputsManager.mapKeyPressed){
            player.inputsManager.mapKeyPressed = false;
            //map = new GameMap(gameName,player);  
            //displayNewWindow("Map");
            phonePanel.open("Map");
        }

        if(player.inputsManager.inventoryKeyPressed){
            player.inputsManager.inventoryKeyPressed = false;
            displayNewWindow("Inventory");
        }

        if(player.inputsManager.menuKeyPressed){
            player.inputsManager.menuKeyPressed = false;
            infoMenu = new InfoMenuScreen(gameName,player);
        }

        if(player.inputsManager.quitKeyPressed){
            player.inputsManager.quitKeyPressed = false;
        }

        if(player.inputsManager.cheatCodeMenuKeyPressed){
            player.inputsManager.cheatCodeMenuKeyPressed = false;
            cheatCodeMenu.open(this);
        }

        if(cheatCodeMenu.frame.isVisible()){
            refresh = cheatCodeMenu.refresh;
            refreshDisplay = cheatCodeMenu.refreshDisplay;
            cheatCodeMenu.refresh = false;
            cheatCodeMenu.refreshDisplay = false;
        }   

        if(player.positionX < 0)changeChunk("left");
        if(player.positionX > (GWS.gameWindowWidth))changeChunk("right");
        if(player.positionY < 0)changeChunk("up");
        if(player.positionY > (GWS.gameWindowHeight))changeChunk("down");

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
        if(isInsideBuilding){
            if(environment.equals("arcade")||environment.equals("newWindow")){
                environment = "extInsideBuilding";
            }else{
                environment = "insideBuilding";
            }      
        }else{
            environment = "ext";
        }
        chunk = new Chunks(this);
    }

    public void enterBuilding(){
        coloseMsgLabels();
        changeChunk("inBuilding");
    }

    public void runArcade(int idGame){
        restartFrame();
        environment = "arcade";
        arcadeGameId = idGame;
        chunk = new Chunks(this);
    }

    public void displayNewWindow(String idNewWindow){
        restartFrame();
        environment = "newWindow";
        newWindowId = idNewWindow;
        chunk = new Chunks(this);
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
                    player.positionX = (GWS.gameWindowWidth) / 2;
                    player.positionY = (GWS.gameWindowHeight);
                    break;
                case "down":
                    currentChunk[0]++;
                    player.positionX = (GWS.gameWindowWidth) / 2;
                    player.positionY = 0;
                    break;
                case "left":
                    currentChunk[1]--;
                    player.positionX = (GWS.gameWindowWidth);
                    player.positionY = (GWS.gameWindowHeight) / 2;
                    break;
                case "right":
                    currentChunk[1]++;
                    player.positionX = 0;
                    player.positionY = (GWS.gameWindowHeight) / 2;
                    break;

                case "inBuilding":
                    isInsideBuilding = true;
                    player.positionX = (GWS.boxSize + 50) / 2;
                    player.positionY = (GWS.boxSize - 100);
                    player.stopPlayer();
                    break;

                case "TP":
                    isInsideBuilding = false;
                    displayChunks = true;
                    player.positionX = (GWS.gameWindowWidth) / 2;
                    player.positionY = (GWS.gameWindowHeight) / 2;
                    break;
                
                case "chargeChunk":
                    isInsideBuilding = false;
                    displayChunks = false;
                    player.positionX = (GWS.gameWindowWidth) / 2;
                    player.positionY = (GWS.gameWindowHeight) / 2;
                    break;
            
                default:
                    break;
            }
        }else{
            isInsideBuilding = false;
            player.positionX = (GWS.gameWindowWidth) / 2;
            player.positionY = (GWS.gameWindowHeight) / 2;
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
        updatePhoneMoneyLabels();
        updatePhoneDateLabels();
    }

    public void updateDate(){
        nightPanel.updateNight(isInsideBuilding, date);
        updatePhoneDateLabels();
    }

    public void updatePositionTextLabels(){
        frontPanel.updatePositionTextLabels(currentChunk,new long[] {player.positionX,player.positionY});
    }

    public void updatePhoneMoneyLabels(){
        phonePanel.updateMoneyLabel(String.valueOf(player.money));
    }

    public void updatePhoneDateLabels(){
        phonePanel.updateDateLabel(date.getDate());
        phonePanel.updateHourLabel(date.getHour());
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
