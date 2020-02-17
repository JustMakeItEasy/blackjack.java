package app.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CardCollection {
    List<Card> Cards = new ArrayList<Card>();

    public int Count() {
        return Cards.size();
    }

    public int Score() {
        return Cards.stream().map((card) -> card.Value).reduce(0, Integer::sum);
    }

    public List<Card> VisibleCards() {
        return Cards.stream().filter(card -> !card.Facedown).collect(Collectors.toList());
    }
}