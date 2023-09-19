package com.jv01;

import javax.swing.SwingUtilities;

import com.jv01.screens.MainMenuScreen;

public class App{
    public static MainMenuScreen mainMenu = new MainMenuScreen();


    public  static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                mainMenu.showMainMenu();
            }
        });
    }
}
