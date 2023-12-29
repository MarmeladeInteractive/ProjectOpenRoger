package com.jv01.screens;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GameWindowsSize {
    public boolean isFullScreen = true;

    public int gameWindowHeight;
    public int gameWindowWidth;

    public int FooterHeight = 50;

    public int cellSize;
    public int cellHeight;
    public int cellWidth;

    public int boxSize = 800;

    private int boxSizeBase = 800;
    public int boxScale;

    public GameWindowsSize(boolean isFullScreen){
        this.isFullScreen = isFullScreen;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenSizeHeight = (int) screenSize.getHeight();
        int screenSizeWidth = (int) screenSize.getWidth();

        int minScreenDimension = Math.min(screenSizeHeight, screenSizeWidth);

        boxSize = minScreenDimension;
        boxScale = (boxSize*1)/boxSizeBase;
        
        cellSize = minScreenDimension/3;

        cellHeight = screenSizeHeight/3;
        cellWidth = screenSizeWidth/3;
        
        if(isFullScreen){
            gameWindowWidth = screenSizeWidth;
            gameWindowHeight = screenSizeHeight;
        }else{        
            gameWindowWidth = minScreenDimension;
            gameWindowHeight = minScreenDimension;
        }
        
    }
}
