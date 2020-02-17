package app;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import app.models.*;

public class App {
    public static void main(String[] args) throws Exception {

        int number_of_players = 0;
        while (number_of_players == 0) {
            System.out.print("How many players? ");
            try {
                number_of_players = Integer.parseInt(System.console().readLine());
            } catch (Exception ex) {
                number_of_players = 0;
            }
        }

        for (int i = 0; i < number_of_players; i++) {
            String player_name = "";
            while (!player_name.matches("(.)+")) {
                System.out.format("Player %d what is your name? ", (i + 1));
                player_name = System.console().readLine().toString().toLowerCase();
            }
            String name_split = player_name.substring(0, 1);
            player_name = player_name.replaceFirst(name_split, name_split.toUpperCase());
            Game.Players.add(new Player(player_name));
        }

        Game.Dealer = new Dealer();

        Game.DisplayHands();

        for (final Player player : Game.Players) {
            while (player.State == PlayerState.Playing) {
                player.StartTurn();
            }
            Game.DisplayHands();
            System.out.println();
        }

        if (Game.Players.stream().filter((player) -> player.State == PlayerState.Stuck).count() == 0) {
            System.out.println("Dealer wins, all players were out!");
            return;
        }

        final List<Player> stuck_players = Game.Players.stream().filter((player) -> player.State == PlayerState.Stuck)
                .collect(Collectors.toList());

        stuck_players.sort((p1, p2) -> Integer.compare(p1.Hand.Score(), p2.Hand.Score()));

        final Player current_winner = stuck_players.get(0);

        while (Game.Dealer.State == PlayerState.Playing) {
            Game.Dealer.StartTurn(current_winner.Hand.Score());
        }

        if (Game.Dealer.State != PlayerState.Won) {
            System.out.println(current_winner.Name + " won with " + current_winner.Hand.Score());
        }
    }
}