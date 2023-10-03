package com.jv01;

import javax.swing.SwingUtilities;

import com.jv01.screens.MainMenuScreen;
import com.jv01.screens.loadScreen.LoadScreen;

public class App{

    public static LoadScreen loadScreen;


    public  static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loadScreen = new LoadScreen();
            }
        });
    }
}
