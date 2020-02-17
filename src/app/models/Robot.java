package app.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

final class RobotNames {

    public static String GetRandomName() throws IOException {
        final List<String> names = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("names.csv"))) {
            String name;
            while ((name = br.readLine()) != null) {
                names.add(name);
            }
        }

        final Random rand = new Random();
        return names.get(rand.nextInt(names.size()));
    }

}

public class Robot extends Person {

    public Robot() throws IOException {
        super(RobotNames.GetRandomName(), 2);
    }

    public void StartTurn() {

        Character choice = null;
        while (choice == null) {

            int remainder = 21 - Hand.Score();
            if (remainder > 10) {
                choice = 'H';

            } else {

                // Add all visible cards to a 'known taken pile' and working out the percentage
                // chance of going bust. This is similiar to counting cards in poker.
                List<Card> known_taken_cards = new ArrayList<Card>();
                known_taken_cards.addAll(this.Hand.Cards);
                known_taken_cards.addAll(Game.Dealer.Hand.VisibleCards());
                for (Player player : Game.Players) {
                    known_taken_cards.addAll(player.Hand.VisibleCards());
                }

                Deck _deck = new Deck();
                for (Card card : known_taken_cards) {
                    _deck.Cards.remove(card);
                }

                long potential_cards = _deck.Cards.stream().filter(card -> card.Value <= remainder).count();

                float probabilty = potential_cards / _deck.Cards.size();

                choice = probabilty > 0.60 ? 'H' : 'S';
            }

            switch (choice) {
            case 'H':
                final Card dealt_card = Game.Deck.DealCard();
                Hand.Add(dealt_card);

                System.out.println("You chose hit and got got " + dealt_card.toString() + ". This puts your total at: "
                        + Hand.Score());

                if (Hand.Score() > 21) {
                    System.out.println(Name + " has lost with " + Hand.Score() + "!");
                    State = PlayerState.Out;
                }

                break;

            case 'S':
                System.out.println("You chose stick");
                State = PlayerState.Stuck;
                break;

            default:
                choice = null;
                break;
            }
        }

    }

}
