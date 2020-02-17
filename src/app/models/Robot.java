package app.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

final class RobotNames {

    final static String[] names = { "Deb", "Jarret", "Udall", "Arlen", "Brady", "Tomlin", "Cullan", "Orazio", "Elsbeth",
            "Berkie", "Curcio", "Yank", "Nariko", "Estrellita", "Boigie", "Aggi", "Tildy", "Laurence", "Sabrina",
            "Cleo", "Pip", "Shirl", "Linn", "Hester", "Ailee", "Bird", "Sonya", "Trisha", "Brady", "Lolly", "Free",
            "Rosmunda", "Lydon", "Nissy", "Tabbie", "Kermie", "Dannel", "Jasun", "Maynord", "Lynea", "Barnett", "Hill",
            "Shaylyn", "Linea", "Steve", "Cherianne", "Reid", "Colline", "Leslie", "Pietro" };

    public static String GetRandomName() throws IOException {
        final Random rand = new Random();
        return names[rand.nextInt(names.length - 1)];
    }

}

public class Robot extends Person {

    public Robot() throws IOException {
        super(RobotNames.GetRandomName(), 2, Type.Robot);
    }

    public void StartTurn() {

        Character choice = null;
        while (choice == null) {

            final int remainder = 21 - Hand.Score();
            if (remainder > 10) {
                choice = 'H';

            } else {

                // Add all visible cards to a 'known taken pile' and working out the percentage
                // chance of going bust. This is similiar to counting cards in poker.
                final List<Card> known_taken_cards = new ArrayList<Card>();
                known_taken_cards.addAll(Game.Player.Hand.Cards);
                known_taken_cards.addAll(Game.Dealer.Hand.VisibleCards());
                for (final Robot bot : Game.Bots) {
                    known_taken_cards.addAll(bot.Hand.VisibleCards());
                }

                final Deck _deck = new Deck();
                for (final Card card : known_taken_cards) {
                    _deck.Cards.remove(card);
                }

                final long potential_cards = _deck.Cards.stream().filter(card -> card.Value <= remainder).count();

                final float probabilty = potential_cards / _deck.Cards.size();

                choice = probabilty > 0.30 ? 'H' : 'S';
            }

            switch (choice) {
            case 'H':
                final Card dealt_card = Game.Deck.DealCard();
                Hand.Add(dealt_card);

                System.out.format("%s chose hit and got got %s. This puts their total at: %d\n", this.Name,
                        dealt_card.toString(), Hand.Score());

                if (Hand.Score() > 21) {
                    System.out.println(this.Name + " has lost with " + this.Hand.Score() + "!");
                    State = PlayerState.Out;
                }

                break;

            case 'S':
                System.out.println(this.Name + " chose to stick on: " + this.Hand.Score());
                State = PlayerState.Stuck;
                break;

            default:
                choice = null;
                break;
            }
        }

    }

    public void PrintInfo() {

        final String card_values = String.join(", ",
                this.Hand.Cards.stream().map(card -> card.Facedown ? "?" : card.Name).collect(Collectors.toList()));

        final List<Card> visible_cards = this.Hand.Cards.stream().filter(c -> !c.Facedown).collect(Collectors.toList());
        final String score = "Score = " + visible_cards.stream().map(c -> c.Value).reduce(0, Integer::sum);
        System.out.format("| %-12s | %-12s | %-12s |\n", this.Name, card_values, score);
    }

}
