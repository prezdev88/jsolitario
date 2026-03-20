/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author pato
 */
public class Deck implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int DECK_SIZE = 104;
    private static final int COLOR_COUNT = 2;
    private static final int FIRST_SUIT = 1;
    private static final int LAST_SUIT = 4;
    private static final int FIRST_NUMBER = 1;
    private static final int LAST_NUMBER = 13;

    private final Random random = new Random();
    private List<Card> cards = new ArrayList<Card>();

    public Deck(boolean orderedDeck) {
        Card[] deckCards = new Card[DECK_SIZE];
        loadCards(deckCards, orderedDeck);
        cards = toList(deckCards);
        printCards();
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public List<Card> getCards() {
        return cards;
    }

    private void loadCards(Card[] deckCards, boolean orderedDeck) {
        if (orderedDeck) {
            loadOrderedCards(deckCards);
            return;
        }
        loadRandomCards(deckCards);
    }

    private void loadRandomCards(Card[] deckCards) {
        for (int color = 0; color < COLOR_COUNT; color++) {
            for (int suit = FIRST_SUIT; suit <= LAST_SUIT; suit++) {
                for (int number = FIRST_NUMBER; number <= LAST_NUMBER; number++) {
                    int index = getAvailableIndex(deckCards);
                    deckCards[index] = new Card(suit, number, color);
                }
            }
        }
    }

    private void loadOrderedCards(Card[] deckCards) {
        int index = 0;
        for (int color = 0; color < COLOR_COUNT; color++) {
            for (int suit = FIRST_SUIT; suit <= LAST_SUIT; suit++) {
                for (int number = FIRST_NUMBER; number <= LAST_NUMBER; number++) {
                    deckCards[index] = new Card(suit, number, color);
                    index++;
                }
            }
        }
    }

    private int getAvailableIndex(Card[] deckCards) {
        int index;
        do {
            index = random.nextInt(DECK_SIZE);
        } while (deckCards[index] != null);
        return index;
    }

    private void printCards() {
        int blueCards = 0;
        int redCards = 0;
        int jokerCards = 0;

        for (int i = 0; i < getCards().size(); i++) {
            Card card = getCard(i);
            System.out.println("Card color: " + card.getCardColor());
            System.out.println("Suit: " + card.getSuit());
            System.out.println("Number: " + card.getNumber());
            System.out.println("---------------------------");
            if (card.getCardColor().equalsIgnoreCase("blue")) {
                blueCards++;
            } else {
                redCards++;
            }
            if (card.isJoker()) {
                jokerCards++;
            }
        }

        System.out.println("CARD COUNT: " + getCards().size());
        System.out.println("RED CARD COUNT: " + redCards);
        System.out.println("BLUE CARD COUNT: " + blueCards);
        System.out.println("JOKER COUNT: " + jokerCards);
    }

    private List<Card> toList(Card[] deckCards) {
        List<Card> list = new ArrayList<Card>();
        list.addAll(Arrays.asList(deckCards));
        return list;
    }
}
