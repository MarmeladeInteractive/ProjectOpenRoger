package com.jv01.miniGames.games.blackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private final List<Cards> cards = new ArrayList<>();

    public void addCard(Cards card) {
        cards.add(card);
    }

    public int getHandValue() {
        int value = 0;
        int aceCount = 0;

        for (Cards card : cards) {
            String rank = card.toString().split(" ")[0];
            if (rank.equals("as")) {
                value += 11;
                aceCount++;
            } else if (rank.equals("r") || rank.equals("d") || rank.equals("v")) {
                value += 10;
            } else {
                value += Integer.parseInt(rank);
            }
        }

        while (value > 21 && aceCount > 0) {
            value -= 10;
            aceCount--;
        }

        return value;
    }

    public List<Cards> getCards() {
        return cards;
    }
}

