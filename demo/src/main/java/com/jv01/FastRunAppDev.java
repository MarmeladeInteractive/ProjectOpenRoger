package com.jv01;

import com.jv01.generations.Game;

import javax.swing.SwingUtilities;
import java.util.Random;

public class FastRunAppDev {

    public  static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Game game = new Game();
                game.runNewGame(generateRandomGameName(20), null, true);
            }
        });
    }

    private static String generateRandomGameName(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder gameNameBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            gameNameBuilder.append(randomChar);
        }

        return gameNameBuilder.toString();
    }
}

