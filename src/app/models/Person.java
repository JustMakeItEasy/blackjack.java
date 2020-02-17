package app.models;

import java.util.List;
import java.util.stream.Collectors;

enum Type {
    Dealer, Player, Robot
}

public class Person {
    public Type PersonType = null;
    public Hand Hand = new Hand();
    public PlayerState State = PlayerState.Playing;
    public String Name = null;

    public Person(final String name, int facedown_cards, final Type type) {
        Name = name;
        PersonType = type;
        for (int i = 0; i < Game.NumberOfCardsToStart; i++) {
            final Card new_card = Game.Deck.DealCard(facedown_cards > 0);
            this.Hand.Add(new_card);
            facedown_cards--;
        }
    }

    public void PrintInfo() {

        final String card_values = String.join(", ",
                this.Hand.Cards.stream().map(card -> card.Name).collect(Collectors.toList()));

        final List<Card> visible_cards = this.Hand.Cards.stream().filter(c -> !c.Facedown).collect(Collectors.toList());
        final String score = "Score = " + visible_cards.stream().map(c -> c.Value).reduce(0, Integer::sum);
        System.out.format("| %-12s | %-12s | %-12s |\n", this.Name, card_values, score);
    }

}