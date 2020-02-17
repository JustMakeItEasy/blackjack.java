package app;

import java.util.List;
import java.util.stream.Collectors;

import app.models.*;

public class App {
    public static void main(String[] args) throws Exception {

        String player_name = "";
        while (!player_name.matches("(.)+")) {
            System.out.format("Player 1 what is your name? ");
            player_name = System.console().readLine().toString().toLowerCase();
        }
        String name_split = player_name.substring(0, 1);
        player_name = player_name.replaceFirst(name_split, name_split.toUpperCase());
        Game.Player = new Player(player_name);

        int number_of_bots = 0;
        while (number_of_bots == 0) {
            System.out.print("How many bots? ");
            try {
                number_of_bots = Integer.parseInt(System.console().readLine());
            } catch (Exception ex) {
                number_of_bots = 0;
            }
        }

        for (int i = 0; i < number_of_bots; i++) {
            Game.Bots.add(new Robot());
        }

        Game.Dealer = new Dealer();

        Game.DisplayHands();

        while (Game.Player.State == PlayerState.Playing) {
            Game.Player.StartTurn();
        }
        Game.DisplayHands();

        for (final Robot bot : Game.Bots) {
            while (bot.State == PlayerState.Playing) {
                bot.StartTurn();
            }
            System.out.println();
        }

        if (Game.Player.State != PlayerState.Stuck
                && Game.Bots.stream().filter((player) -> player.State == PlayerState.Stuck).count() == 0) {
            System.out.println("Dealer wins, all players were out!");
            return;
        }

        List<Robot> stuck_bots = Game.Bots.stream().filter((bot) -> bot.State == PlayerState.Stuck)
                .collect(Collectors.toList());

        stuck_bots.sort((p1, p2) -> Integer.compare(p1.Hand.Score(), p2.Hand.Score()));

        final Person top_bot = stuck_bots.get(0);
        final Person current_winner = top_bot.Hand.Score() > Game.Player.Hand.Score() ? top_bot : Game.Player;

        while (Game.Dealer.State == PlayerState.Playing) {
            Game.Dealer.StartTurn(current_winner.Hand.Score());
        }

        if (Game.Dealer.State != PlayerState.Won) {
            System.out.println(current_winner.Name + " won with " + current_winner.Hand.Score());
        }
    }
}