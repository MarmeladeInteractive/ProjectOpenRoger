package com.jv01.miniGames.games.blackjack;

import javax.swing.ImageIcon;

public class Cards {
    private final String rank;
    private final String suit;

    public Cards(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    public String getCardImagePath() {
        String filePath = "D://java//com//cards//" + rank + "_" + suit + ".png";
        return filePath;
    }

    public ImageIcon getIcon(){
        ImageIcon horsesIcons = new ImageIcon(getCardImagePath());

        return horsesIcons;
    }
}
