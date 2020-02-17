package app.models;

public class Player extends Person {

    public Player(final String name) {
        super(name == "" ? "Unknown Player" : name, 2);
    }

    public void StartTurn() {
        Character choice = null;
        while (choice == null) {

            System.out.format("%s, you're on %d. Please choose an option [S]tand or [H]it: ", Name, Hand.Score());
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