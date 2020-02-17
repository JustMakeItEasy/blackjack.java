package app.models;

public class Card {
    String Name;
    String Suit;
    int Value;
    boolean Facedown = false;

    public Card(final String name, final String suit, final Integer value) {
        Name = name;
        Suit = suit;
        Value = value;
    }

    public String toString() {
        return this.Name + " of " + this.Suit + ".";
    }

}