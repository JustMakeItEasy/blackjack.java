package app.models;

public class Person {
    public Hand Hand = new Hand();
    public PlayerState State = PlayerState.Playing;

    public Person(int facedown_cards) {
        for (int i = 0; i < Game.NumberOfCardsToStart; i++) {
            final Card new_card = Game.Deck.DealCard(facedown_cards > 0);
            this.Hand.Add(new_card);
            facedown_cards--;
        }
    }
}