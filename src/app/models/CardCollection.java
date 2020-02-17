package app.models;

import java.util.ArrayList;
import java.util.List;

public class CardCollection {
    List<Card> Cards = new ArrayList<Card>();

    public int Count() {
        return Cards.size();
    }

    public int Score() {
        return Cards.stream().map((card) -> card.Value).reduce(0, Integer::sum);
    }
}