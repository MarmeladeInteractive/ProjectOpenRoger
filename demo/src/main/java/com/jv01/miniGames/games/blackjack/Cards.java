package com.jv01.miniGames.games.blackjack;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.Collections;

import java.awt.*;

public class Cards {
    private final String rank;
    private final String suit;

    public int widthCard = 60;
    public int heightCard = 80;

    public Cards(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    public String getCardImagePath() {
        String filePath = "demo/src/main/java/com/jv01/miniGames/games/blackjack/img/cards/" + rank + "_" + suit + ".png";
        return filePath;
    }

    public ImageIcon getIcon(){
        String path = getCardImagePath();

        ImageIcon cardIcons = new ImageIcon(path);
        Image cardImage = cardIcons.getImage().getScaledInstance(widthCard, heightCard, Image.SCALE_SMOOTH);

        ImageIcon cardImageFram =new ImageIcon(cardImage);

        return cardImageFram;
    }
}
