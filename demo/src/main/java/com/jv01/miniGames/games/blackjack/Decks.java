package com.jv01.miniGames.games.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Decks {
    private final List<Cards> cards = new ArrayList<>();

    public Decks() {
        String[] suits = {"c", "ca", "t", "p"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "v", "d", "r", "as"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Cards(rank, suit));
            }
        }
    }

    public void shuffle() {
        Random random = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Cards temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    public Cards drawCard() {
        if (cards.isEmpty()) {
            System.out.println("Deck is empty.");
            return null;
        }
        return cards.remove(0);
    }
}

