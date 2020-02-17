package app.models;

import java.util.ArrayList;
import java.util.List;

public final class Game {
    public static Deck Deck = new Deck();
    public static List<Player> Players = new ArrayList<Player>();
    public static Dealer Dealer = new Dealer();
    public static int NumberOfCardsToStart = 2;
}