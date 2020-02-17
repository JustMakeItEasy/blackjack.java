package app;

import java.util.List;
import java.util.stream.Collectors;

import app.models.*;

public class App {
    public static void main(String[] args) throws Exception {

        // TODO: Implement prompt for player names
        final String[] names = { "Simon", "Ashley" };
        for (final String name : names) {
            Game.Players.add(new Player(name));

        }

        for (final Player player : Game.Players) {
            while (player.State == PlayerState.Playing) {
                player.StartTurn();
            }
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