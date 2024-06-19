package com.jv01;

import com.jv01.screens.loadScreen.LoadScreen;

import javax.swing.SwingUtilities;

public class App {

    public static LoadScreen loadScreen;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                loadScreen = new LoadScreen();
            }
        });
    }
}