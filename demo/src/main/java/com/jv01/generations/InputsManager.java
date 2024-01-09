package com.jv01.generations;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.format.SignStyle;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.text.Style;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.jv01.fonctionals.Save;
import com.jv01.fonctionals.Tempo;
import com.jv01.generations.Panels.JoystickPanel.JoystickPanel;

public class InputsManager{
    public Save save = new Save();
    public MainGameWindow mainGameWindow;

    public KeyListener keyListener;
    public MouseAdapter mouseAdapter;
    public MouseListener mouseListener;

    public int leftKey = 81; //
    public int rightKey = 68; //
    public int upKey = 90; //
    public int downKey = 83; //
    public int runKey = 16; //

    public int intercatKey = 69; //
    public int upgradeKey = 85; // u

    public int mapKey = 77; // m

    public int inventoryKey = 73; // i

    public int quitKey = 27; // esc
    public int menuKey = 80; // p
    public int cheatCodeMenuKey = 113;

    public boolean leftKeyPressed = false;
    public boolean rightKeyPressed = false;
    public boolean upKeyPressed = false;
    public boolean downKeyPressed = false;
    public boolean runKeyPressed = false;

    public boolean interactKeyPressed = false;
    public Tempo tempoInteractKey = new Tempo();

    public boolean upgradeKeyPressed = false;
    public Tempo tempoUpgradeKey = new Tempo();

    public boolean mapKeyPressed = false;
    public Tempo tempoMapKey = new Tempo();

    public boolean inventoryKeyPressed = false;
    public Tempo tempoInventoryKey = new Tempo();

    public boolean quitKeyPressed = false;

    public boolean menuKeyPressed = false;
    public Tempo tempoMenuKey = new Tempo();

    public boolean cheatCodeMenuKeyPressed = false;
    public Tempo tempoCheatCodeMenuKey = new Tempo();
    
    public String gameName;

    public boolean joystickIsVisible = false;

    public int diagonalSensitivity = 20;
    public int mouse1stClickX = 0;
    public int mouse1stClickY = 0;
    public int currentMouseLocationX = 0;
    public int currentMouseLocationY = 0;

    
    public InputsManager(MainGameWindow mainGameWindow){
        this.mainGameWindow = mainGameWindow;
        this.gameName = mainGameWindow.gameName;

        getKeyBoardValues();  

        initializeKeyListener();
        initializeMouseListener();
    }

    public void getKeyBoardValues(){
        Document doc = save.getDocumentXml(gameName,"functional/keyBoard");
        Element element = save.getElementById(doc, "keyBoard", "keyBoard");

        Map<String, List<String>> allElements = save.getAllChildsFromElement(element);

        this.leftKey = Integer.parseInt(save.getChildFromMapElements(allElements,"leftKey"));
        this.rightKey = Integer.parseInt(save.getChildFromMapElements(allElements,"rightKey"));
        this.upKey = Integer.parseInt(save.getChildFromMapElements(allElements,"upKey"));
        this.downKey = Integer.parseInt(save.getChildFromMapElements(allElements,"downKey"));
        this.runKey = Integer.parseInt(save.getChildFromMapElements(allElements,"runKey"));

        this.mapKey = Integer.parseInt(save.getChildFromMapElements(allElements,"mapKey"));
        this.inventoryKey = Integer.parseInt(save.getChildFromMapElements(allElements,"inventoryKey"));

        this.intercatKey = Integer.parseInt(save.getChildFromMapElements(allElements,"intercatKey"));
        this.upgradeKey = Integer.parseInt(save.getChildFromMapElements(allElements,"upgradeKey"));

        this.quitKey = Integer.parseInt(save.getChildFromMapElements(allElements,"quitKey"));
        this.menuKey = Integer.parseInt(save.getChildFromMapElements(allElements,"menuKey"));

        this.cheatCodeMenuKey = Integer.parseInt(save.getChildFromMapElements(allElements,"cheatCodeMenuKey"));
    }

    public void initializeKeyListener(){
        keyListener = new KeyListener() {
             @Override
             public void keyTyped(KeyEvent e) {
             }
 
             @Override
             public void keyPressed(KeyEvent e) {
                 int keyCode = e.getKeyCode();
 
                 if (keyCode == KeyEvent.VK_LEFT || keyCode == leftKey) {
                     leftKeyPressed = true;
                 } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == rightKey) {
                     rightKeyPressed = true;
                 } else if (keyCode == KeyEvent.VK_UP || keyCode == upKey) {
                     upKeyPressed = true;
                 } else if (keyCode == KeyEvent.VK_DOWN || keyCode == downKey) {
                     downKeyPressed = true;
                 } else if (keyCode == runKey) {
                     runKeyPressed = true;
                 }
 
                 else if (keyCode == intercatKey && !tempoInteractKey.isRunning()) {
                     interactKeyPressed = true;
                     tempoInteractKey.start(100);
                 }else if (keyCode == upgradeKey && !tempoUpgradeKey.isRunning()) {
                     upgradeKeyPressed = true;
                     tempoUpgradeKey.start(100);
                 }
 
                 else if (keyCode == mapKey  && !tempoMapKey.isRunning()) {
                     mapKeyPressed = true;
                     tempoMapKey.start(100);
                 } else if (keyCode == inventoryKey  && !tempoInventoryKey.isRunning()) {
                     inventoryKeyPressed = true;
                     tempoInventoryKey.start(100);
                 }

                 else if (keyCode == quitKey) {
                     quitKeyPressed = true;
                     int result = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to quit the game?",
                        "Quit Game Confirmation",
                        JOptionPane.YES_NO_OPTION
                    );

                    if (result == JOptionPane.YES_OPTION) {
                        // User clicked "Yes," so exit the game.
                        System.exit(0);
                    }
                 }
                 else if (keyCode == menuKey && !tempoMenuKey.isRunning()) {
                    menuKeyPressed = true;
                    tempoMenuKey.start(100);
                 }
                 
                 else if (keyCode == cheatCodeMenuKey && !tempoCheatCodeMenuKey.isRunning()) {
                    cheatCodeMenuKeyPressed = true;
                    tempoCheatCodeMenuKey.start(100);
                 } 
 
                 else{
                     System.out.println(keyCode);
                 }
             }
 
             @Override
             public void keyReleased(KeyEvent e) {
                 int keyCode = e.getKeyCode();
 
                 if (keyCode == KeyEvent.VK_LEFT || keyCode == leftKey) {
                     leftKeyPressed = false;
                 } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == rightKey) {
                     rightKeyPressed = false;
                 } else if (keyCode == KeyEvent.VK_UP || keyCode == upKey) {
                     upKeyPressed = false;
                 } else if (keyCode == KeyEvent.VK_DOWN || keyCode == downKey) {
                     downKeyPressed = false;
                 } else if (keyCode == runKey) {
                     runKeyPressed = false;
                 }
 
                 else if (keyCode == intercatKey) {
                     interactKeyPressed = false;
                 }else if (keyCode == upgradeKey) {
                     upgradeKeyPressed = false;
                 }
 
                 else if (keyCode == mapKey) {
                     mapKeyPressed = false;
                 } else if (keyCode == inventoryKey) {
                     interactKeyPressed = false;
                 }

                 else if (keyCode == quitKey) {
                     quitKeyPressed = false;;
                 }
                 else if (keyCode == menuKey) {
                    menuKeyPressed = false;;
                 }
                 else if (keyCode == cheatCodeMenuKey) {
                    cheatCodeMenuKeyPressed = false;
                 }
             }
         };
     }

     public void initializeMouseListener(){
        mouseAdapter = new MouseAdapter() {           
            @Override
            public void mouseDragged(MouseEvent e) {
                mainGameWindow.joystickPanel.updateJoystickLocation(e.getX(), e.getY());
                currentMouseLocationX = e.getX();
                currentMouseLocationY = e.getY();

                int xStep = mouse1stClickX-e.getX();
                int yStep = mouse1stClickY-e.getY();

                int joystickDiameter = mainGameWindow.joystickPanel.joystickDiameter;
                if (Math.sqrt(xStep * xStep + yStep * yStep) < joystickDiameter) {
                    runKeyPressed = false;
                } else {
                    runKeyPressed = true;
                }

                if (Math.sqrt(xStep * xStep + yStep * yStep) > diagonalSensitivity) {
                    if (Math.abs(xStep) > diagonalSensitivity) {
                        if (xStep > 0) {
                            rightKeyPressed = false;
                            leftKeyPressed = true;
                        } else {
                            leftKeyPressed = false;
                            rightKeyPressed = true;
                        }
                    }else{
                        rightKeyPressed = false;
                        leftKeyPressed = false;
                    }

                    if (Math.abs(yStep) > diagonalSensitivity) {
                        if (yStep > 0) {
                            downKeyPressed = false;
                            upKeyPressed = true;
                        } else {
                            upKeyPressed = false;
                            downKeyPressed = true;
                        }
                    }else{
                        downKeyPressed = false;
                        upKeyPressed = false;
                    }
                }else{
                    clearKeys();
                }
            }
    
            @Override
            public void mouseMoved(MouseEvent e) {
                mouse1stClickX = e.getX();
                mouse1stClickY = e.getY();
            }
        };

        mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
    
            @Override
            public void mousePressed(MouseEvent e) {
                mainGameWindow.joystickPanel.addJoystick(mouse1stClickX, mouse1stClickY,diagonalSensitivity);
                joystickIsVisible=true;
            }
    
            @Override
            public void mouseReleased(MouseEvent e) {
                clearKeys();
                mainGameWindow.joystickPanel.clearJoystickPanel();
                joystickIsVisible=false;
            }
    
            @Override
            public void mouseEntered(MouseEvent e) {
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                clearKeys();
                mainGameWindow.joystickPanel.clearJoystickPanel();
                joystickIsVisible=false;
            }
        };
    }


    public void clearKeys(){
        rightKeyPressed = false;
        leftKeyPressed = false;
        upKeyPressed = false;
        downKeyPressed = false;
    }
    

     
}
