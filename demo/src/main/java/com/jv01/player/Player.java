package com.jv01.player;

import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.SoundManager;
import com.jv01.generations.InputsManager;
import com.jv01.generations.MainGameWindow;
import com.jv01.generations.Items;

import java.awt.*;

import java.util.List;
import java.util.Map;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.lang.invoke.StringConcatException;

public class Player{
    public Save save = new Save();
    private SoundManager soundManager;
    public MainGameWindow mainGameWindow;

    public PlayerInventory inventory;
    public InputsManager inputsManager;

    public String gameName;

    public long money = 1000;
    public String playerName = "Roger";

    public int playerSize = 100;

    public int step = 10;
    private int indexStep = 0;
    public int movementType = 2;
    public int speed = 1;
    public boolean canWalk = true;

    public int currentFrameIndex = 0;
    public int currentFramePicIndex = 0;

    public long[] chunk = {0,0};
    public boolean isInsideBuilding = false;

    public int positionX = 0;
    public int positionY = 0;

    public ImageIcon[][] walkingFrames;
    public JLabel playerLabel;
    public ImageIcon playerIcon;

    public boolean displayAlert = false;
    public String alertMessage = "";

    public long wasteCollected = 0;

    public int partyID = 1;

    public String ideologicalCode = "";
    // player needs
    public int hunger = 0;
    public int thirst = 0;
    public int tiredness = 0;
    public int hygiene = 100;

    public Player(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.gameName = mainGameWindow.gameName; 

        soundManager = new SoundManager(gameName);
        
        getPlayerValues();

        initializePlayer();   
    }

    public void getPlayerValues(){
        Document doc = save.getDocumentXml(gameName,"player");
        Element element = save.getElementById(doc, "player", "player");

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        this.playerName = save.getChildFromMapElements(allElements,"playerName");
        this.money = Integer.parseInt(save.getChildFromMapElements(allElements,"money"));
        this.speed = Integer.parseInt(save.getChildFromMapElements(allElements,"speed"));
        this.step = Integer.parseInt(save.getChildFromMapElements(allElements,"step"));
        this.playerSize = Integer.parseInt(save.getChildFromMapElements(allElements,"playerSize"));

        this.isInsideBuilding = false;
        int isInsideBuildingInt = Integer.parseInt(save.getChildFromMapElements(allElements,"isInsideBuildingInt"));
        if(isInsideBuildingInt == 1)isInsideBuilding = true;

        int[] c = save.stringToIntArray(save.getChildFromMapElements(allElements,"chunk"));
        this.chunk[0] = c[0];
        this.chunk[1] = c[1];

        this.wasteCollected = Integer.parseInt(save.getChildFromMapElements(allElements,"wasteCollected"));
        this.partyID = Integer.parseInt(save.getChildFromMapElements(allElements,"partyID"));

        this.hunger = Integer.parseInt(save.getChildFromMapElements(allElements,"hunger"));
        this.thirst = Integer.parseInt(save.getChildFromMapElements(allElements,"thirst"));
        this.tiredness = Integer.parseInt(save.getChildFromMapElements(allElements,"tiredness"));
        this.hygiene = Integer.parseInt(save.getChildFromMapElements(allElements,"hygiene"));

        doc = save.getDocumentXml(gameName,"parties");
        element = save.getElementById(doc, "party", String.valueOf(this.partyID));

        allElements = save.getAllChildsFromElement(element);

        this.ideologicalCode = save.getChildFromMapElements(allElements,"ideologicalCode");   
    }

    public void initializePlayer(){

        initializeWalkingFrames();

        playerIcon = new ImageIcon("demo/img/characters/player/down/playerDown01.png");
        Image playerImage = playerIcon.getImage().getScaledInstance(playerSize, playerSize, Image.SCALE_SMOOTH);
        playerIcon = new ImageIcon(playerImage);

        if (playerIcon.getImageLoadStatus() == MediaTracker.COMPLETE) {
            // System.out.println("Image chargée avec succès.");
        }else{
             System.out.println("Erreur lors du chargement de l'image.");
        }
 
        playerLabel = new JLabel(playerIcon);
        playerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        inventory = new PlayerInventory(gameName);
        inputsManager = new InputsManager(mainGameWindow);
    }

    public void initializeWalkingFrames() {
        ImageIcon[] rightFrames = new ImageIcon[]{
                new ImageIcon("demo/img/characters/player/right/playerRight01.png"),
                new ImageIcon("demo/img/characters/player/right/playerRight02.png"),
                new ImageIcon("demo/img/characters/player/right/playerRight03.png"),
                new ImageIcon("demo/img/characters/player/right/playerRight04.png"),
        };

        ImageIcon[] leftFrames = new ImageIcon[rightFrames.length];
        for (int i = 0; i < rightFrames.length; i++) {
            Image image = rightFrames[i].getImage();
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-bufferedImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bufferedImage = op.filter(bufferedImage, null);

            leftFrames[i] = new ImageIcon(bufferedImage);
        }

        ImageIcon[] upFrames = new ImageIcon[]{
                new ImageIcon("demo/img/characters/player/up/playerUp01.png"),
                new ImageIcon("demo/img/characters/player/up/playerUp02.png"),
                new ImageIcon("demo/img/characters/player/up/playerUp03.png"),
                new ImageIcon("demo/img/characters/player/up/playerUp04.png"),
        };

        ImageIcon[] upRightFrames = new ImageIcon[]{
                new ImageIcon("demo/img/characters/player/upRight/playerUpRight01.png"),
                new ImageIcon("demo/img/characters/player/upRight/playerUpRight02.png"),
                new ImageIcon("demo/img/characters/player/upRight/playerUpRight03.png"),
                new ImageIcon("demo/img/characters/player/upRight/playerUpRight04.png"),
        };

        ImageIcon[] upLeftFrames = new ImageIcon[upRightFrames.length];
        for (int i = 0; i < upRightFrames.length; i++) {
            Image image = upRightFrames[i].getImage();
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-bufferedImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bufferedImage = op.filter(bufferedImage, null);

            upLeftFrames[i] = new ImageIcon(bufferedImage);
        }

        ImageIcon[] downFrames = new ImageIcon[]{
                new ImageIcon("demo/img/characters/player/down/playerDown01.png"),
                new ImageIcon("demo/img/characters/player/down/playerDown02.png"),
                new ImageIcon("demo/img/characters/player/down/playerDown03.png"),
                new ImageIcon("demo/img/characters/player/down/playerDown04.png"),
        };

        ImageIcon[] downRightFrames = rightFrames;

        ImageIcon[] downLeftFrames = new ImageIcon[downRightFrames.length];
        for (int i = 0; i < downRightFrames.length; i++) {
            Image image = downRightFrames[i].getImage();
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();

            AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
            tx.translate(-bufferedImage.getWidth(null), 0);
            AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            bufferedImage = op.filter(bufferedImage, null);

            downLeftFrames[i] = new ImageIcon(bufferedImage);
        }

        walkingFrames = new ImageIcon[][]{
                leftFrames,
                rightFrames,
                upFrames,
                downFrames,
                upRightFrames,
                upLeftFrames,
                downRightFrames,
                downLeftFrames,
        };
    }

    public void updatePlayerLocation() {
        if(canWalk){
            if (inputsManager.runKeyPressed){
                movementType = 1;
            } else {
                movementType = 2;
            }

            int lastPositionX = positionX;
            int lastPositionY = positionY;

            int lastFramePicIndex = currentFramePicIndex;

            if (inputsManager.leftKeyPressed && inputsManager.upKeyPressed) {
                positionX -= step*speed;
                positionY -= step*speed;
                currentFrameIndex = 5;
                currentFramePicIndex++;
            } else if (inputsManager.leftKeyPressed && inputsManager.downKeyPressed) {
                positionX -= step*speed;
                positionY += step*speed;
                currentFrameIndex = 7;
                currentFramePicIndex++;
            } else if (inputsManager.rightKeyPressed && inputsManager.upKeyPressed) {
                positionX += step*speed;
                positionY -= step*speed;
                currentFrameIndex = 4;
                currentFramePicIndex++;
            } else if (inputsManager.rightKeyPressed && inputsManager.downKeyPressed) {
                positionX += step*speed;
                positionY += step*speed;
                currentFrameIndex = 6;
                currentFramePicIndex++;
            } else if (inputsManager.leftKeyPressed) {
                positionX -= step*speed;
                currentFrameIndex = 0;
                currentFramePicIndex++;
            } else if (inputsManager.rightKeyPressed) {
                positionX += step*speed;
                currentFrameIndex = 1;
                currentFramePicIndex++;
            } else if (inputsManager.upKeyPressed) {
                positionY -= step*speed;
                currentFrameIndex = 2;
                currentFramePicIndex++;
            } else if (inputsManager.downKeyPressed) {
                positionY += step*speed;
                currentFrameIndex = 3;
                currentFramePicIndex++;
            }

            if(inputsManager.joystickIsVisible){
                positionX = lastPositionX - (int)((double)(step*speed)*inputsManager.perX);
                positionY = lastPositionY - (int)((double)(step*speed)*inputsManager.perY);
            }

            currentFramePicIndex = (currentFramePicIndex) % walkingFrames[currentFrameIndex].length;
            if(lastFramePicIndex != currentFramePicIndex){
                if(indexStep < 1){
                    indexStep++;
                }else{
                    indexStep = 0;
                    soundManager.playSFX("footprint_default");
                }
            }
            Image scaledImage = walkingFrames[currentFrameIndex][currentFramePicIndex].getImage().getScaledInstance(playerSize, playerSize, Image.SCALE_SMOOTH);
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
            playerLabel.setIcon(scaledImageIcon);
        }     
    }

    public void stopPlayer(){
        inputsManager.rightKeyPressed = false;
        inputsManager.leftKeyPressed = false;
        inputsManager.upKeyPressed = false;
        inputsManager.downKeyPressed = false;
        inputsManager.runKeyPressed = false;
    }

    public void Consume(Items consummed)
    {
        this.hunger=this.hunger+consummed.hungerValue;
        this.thirst=this.thirst+consummed.thirstValue;
        this.tiredness=this.tiredness+consummed.tiringValue;
        this.hygiene=this.hygiene+consummed.cleanlynessValue;
    }

        public void Sleep()
    {
        this.tiredness=0;
    }

    public void Wash()
    {
        this.hygiene=100;
    }

    public boolean isEnoughMoney(int price, boolean takeOff){
        boolean isEnought = false;
        if(money>=price){
            isEnought = true;
            if(takeOff){
                money-=price;
                save();       
            }      
        }else{
            if(takeOff)displayAlert("Pas assez d'argent");
        }
        return isEnought;
    }

    public void savePlayerValue( String childName, String newValue){
        save.changeElementChildValue(gameName, "player", "player", "player", childName, newValue);
        mainGameWindow.date.saveDate();
    }

    public void saveMoney(){
        savePlayerValue("money",String.valueOf(money));
    }

    public void saveChunk(){        
        savePlayerValue("chunk",'{'+String.valueOf(chunk[0])+','+String.valueOf(chunk[1])+'}');
    }

    public void saveWasteCollected(){        
        savePlayerValue("wasteCollected",String.valueOf(wasteCollected));
    }

    public void savePartyID(){        
        savePlayerValue("partyID",String.valueOf(partyID));
    }

    public void saveStep(){        
        savePlayerValue("step",String.valueOf(step));
    }

    public void saveSpeed(){
        savePlayerValue("speed",String.valueOf(speed));
    }

    public void saveHunger(){        
        savePlayerValue("hunger",String.valueOf(hunger));
    }

    public void saveThirst(){
        savePlayerValue("thirst",String.valueOf(thirst));
    }

    public void saveTiredness(){
        savePlayerValue("tiredness",String.valueOf(tiredness));
    }

    public void saveHygiene(){
        savePlayerValue("hygiene",String.valueOf(hygiene));
    }

    public void save(){
        saveMoney();
        saveChunk();
        saveWasteCollected();
        savePartyID();
        saveStep();
        saveSpeed();
        saveHunger();
        saveThirst();
        saveTiredness();
        saveHygiene();
    }

    private void displayAlert(String msg){
        alertMessage = msg;
        displayAlert = true;
    }

    public void addMoney(long add){
        getPlayerValues();
        money += add;
        saveMoney();
    }
    public void removeMoney(long remove){
        if(remove>0)remove = remove * (-1);
        addMoney(remove);
    }
}