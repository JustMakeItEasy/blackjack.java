package app.models;

public class Hand extends CardCollection {

    public Hand() {
    }

    public void Add(final Card new_card) {
        Cards.add(new_card);
    }
}