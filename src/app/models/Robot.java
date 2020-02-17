package app.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

final class RobotNames {

    public static String GetRandomName() throws IOException {
        final List<String> names = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("names.csv"))) {
            String name;
            while ((name = br.readLine()) != null) {
                names.add(name);
            }
        }

        final Random rand = new Random();
        return names.get(rand.nextInt(names.size()));
    }

}

public class Robot extends Person {

    public Robot() throws IOException {
        super(RobotNames.GetRandomName(), 2);
    }

    public void StartTurn() {
        Character choice = null;
        while (choice == null) {

            System.out.print(Name + ", you're on " + Hand.Score() + " " + "Please choose an option [S]tand or [H]it: ");
            final String input = System.console().readLine().toUpperCase();
            if (input.length() == 0) {
                continue;
            }
            choice = input.charAt(0);

            switch (choice) {
            case 'H':
                final Card dealt_card = Game.Deck.DealCard();
                Hand.Add(dealt_card);

                System.out.println("You chose hit and got got " + dealt_card.toString() + ". This puts your total at: "
                        + Hand.Score());

                if (Hand.Score() > 21) {
                    System.out.println(Name + " has lost with " + Hand.Score() + "!");
                    State = PlayerState.Out;
                }

                break;

            case 'S':
                System.out.println("You chose stick");
                State = PlayerState.Stuck;
                break;

            default:
                choice = null;
                break;
            }
        }

    }

}
