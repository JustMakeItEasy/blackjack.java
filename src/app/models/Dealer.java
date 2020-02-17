package app.models;

public class Dealer extends Person {
    public Dealer() {
        super("Dealer", 1);
    }

    public void StartTurn(final int target_value) {
        while (this.State == PlayerState.Playing) {

            if (this.Hand.Score() >= target_value) {
                this.State = PlayerState.Won;
                System.out.println("The dealer has won with: " + Hand.Score());
                return;
            }

            final Card new_card = Game.Deck.DealCard();
            Hand.Add(new_card);

            if (this.Hand.Score() > 21) {
                this.State = PlayerState.Out;
                System.out.println("The dealer has gone bust with: " + Hand.Score());
            }

        }

    }

}