package app.models;

import java.util.ArrayList;
import java.util.List;

public final class Game {
    public static Deck Deck = new Deck();
    public static Player Player = null;
    public static List<Robot> Bots = new ArrayList<Robot>();
    public static Dealer Dealer = null;
    public static int NumberOfCardsToStart = 2;

    public static void DisplayHands() {
        System.out.print("\033[H\033[2J");
        System.console().flush();

        System.out.println("+--------------+--------------+--------------+");
        System.out.format("| %-12s | %-12s | %-12s |\n", "Name", "Cards", "Score");
        System.out.println("+--------------+--------------+--------------+");

        Player.PrintInfo();

        for (final Robot bot : Bots) {
            bot.PrintInfo();
        }

        Dealer.PrintInfo();

        System.out.println("+--------------+--------------+--------------+");

    }

}