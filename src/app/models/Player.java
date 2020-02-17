package app.models;

public class Player extends Person {

    public String Name = null;

    public Player(final String name) {
        super(2);
        Name = name == "" ? "Unknown Player" : name;
    }

    public void StartTurn() {
        Character choice = null;
        while (choice == null) {

            System.out.print(Name + " You're on " + Hand.Score() + " " + "Please choose an option [S]tand or [H]it: ");
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