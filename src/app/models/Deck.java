package app.models;

import java.util.Random;

public class Deck extends CardCollection {

    public Deck() {
        for (final String card_name : new String[] { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q",
                "K" }) {

            int number = 0;
            try {
                number = Integer.parseInt(card_name);
            } catch (final Exception e) {
                number = card_name == "A" ? 1 : 10;
            }

            for (final String suit : new String[] { "Clubs", "Diamonds", "Hearts", "Spades" }) {
                final Card _card = new Card(card_name, suit, number);
                Cards.add(_card);
            }
        }
    }

    public Card DealCard() {
        return DealCard(false);
    }

    public Card DealCard(final boolean facedown) {
        final Random random = new Random();
        final int random_select = random.nextInt(Cards.size());
        final Card given_card = Cards.get(random_select);
        given_card.Facedown = facedown;
        Cards.remove(random_select);
        return given_card;
    }
}