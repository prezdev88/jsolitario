package game;

import cards.Card;
import cards.Deck;
import cards.PlayOrder;
import cards.Quadrant;
import java.util.ArrayList;
import java.util.List;

public class SolitaireGame {

    private static final int SPACE_COUNT = 8;
    private static final int INITIAL_INDEX = 102;
    private static final int NO_PLAY_AVAILABLE = -1;
    private static final int FIRST_VISIBLE_INDEX = 0;
    private static final int LAST_VISIBLE_OFFSET = 2;

    private Deck deck;
    private int currentIndex;
    private final List<List<Card>> spaces = new ArrayList<>();

    public SolitaireGame(boolean orderedDeck) {
        for (int i = 0; i < SPACE_COUNT; i++) {
            spaces.add(new ArrayList<Card>());
        }
        restart(orderedDeck);
    }

    public void restart(boolean orderedDeck) {
        for (List<Card> space : spaces) {
            space.clear();
        }
        deck = new Deck(orderedDeck);
        currentIndex = INITIAL_INDEX;
        normalizeIndex();
    }

    public Card getCurrentCard() {
        normalizeIndex();
        if (currentIndex >= deck.getCards().size()) {
            return null;
        }
        return deck.getCard(currentIndex);
    }

    public Card getVisibleLowerCard() {
        normalizeIndex();
        int nextIndex = currentIndex + 1;
        if (nextIndex < deck.getCards().size()) {
            return deck.getCard(nextIndex);
        }
        return null;
    }

    public int getRemainingCards() {
        return deck.getCards().size();
    }

    public int getVisibleIndex() {
        normalizeIndex();
        return currentIndex;
    }

    public boolean isDeckEmpty() {
        return deck.getCards().isEmpty();
    }

    public Card getTopCardAtSpace(int space) {
        List<Card> cardsAtSpace = getSpace(space);
        if (cardsAtSpace.isEmpty()) {
            return null;
        }
        return cardsAtSpace.get(cardsAtSpace.size() - 1);
    }

    public boolean playCardInSpace(int space) {
        Card currentCard = getCurrentCard();
        if (currentCard == null) {
            return false;
        }

        List<Card> cardsAtSpace = getSpace(space);
        int playOrder = getPlayOrder(space);
        if (!canPlayInSpace(currentCard, cardsAtSpace, playOrder)) {
            return false;
        }
        if (!canUseSuitInGroup(space, currentCard)) {
            return false;
        }

        cardsAtSpace.add(currentCard);
        deck.getCards().remove(currentIndex);
        normalizeIndex();
        return true;
    }

    public void flipCard() {
        currentIndex -= 2;
        normalizeIndex();
    }

    public int getPlayableSpace(Card card) {
        if (card == null) {
            return NO_PLAY_AVAILABLE;
        }

        for (int space = Quadrant.SPACE_1; space <= Quadrant.SPACE_8; space++) {
            List<Card> cardsAtSpace = getSpace(space);
            int playOrder = getPlayOrder(space);
            if (canPlayInSpace(card, cardsAtSpace, playOrder) && canUseSuitInGroup(space, card)) {
                return space;
            }
        }

        return NO_PLAY_AVAILABLE;
    }

    private boolean canPlayInSpace(Card card, List<Card> cardsAtSpace, int playOrder) {
        if (cardsAtSpace.isEmpty()) {
            return card.getNumber() == (playOrder == PlayOrder.ASCENDING ? 1 : 13);
        }

        Card topCard = cardsAtSpace.get(cardsAtSpace.size() - 1);
        int expectedNumber = playOrder == PlayOrder.ASCENDING
            ? topCard.getNumber() + 1
            : topCard.getNumber() - 1;

        return topCard.getSuit().equalsIgnoreCase(card.getSuit())
            && card.getNumber() == expectedNumber;
    }

    private boolean canUseSuitInGroup(int targetSpace, Card card) {
        int[] groupedSpaces = getGroupedSpaces(targetSpace);
        for (int groupedSpace : groupedSpaces) {
            if (groupedSpace == targetSpace) {
                continue;
            }
            if (containsSuit(groupedSpace, card)) {
                return false;
            }
        }
        return true;
    }

    private boolean containsSuit(int space, Card card) {
        List<Card> cardsAtSpace = getSpace(space);
        if (cardsAtSpace.isEmpty()) {
            return false;
        }
        return cardsAtSpace.get(0).getSuit().equalsIgnoreCase(card.getSuit());
    }

    private int[] getGroupedSpaces(int space) {
        if (space >= Quadrant.SPACE_1 && space <= Quadrant.SPACE_4) {
            return new int[]{Quadrant.SPACE_1, Quadrant.SPACE_2, Quadrant.SPACE_3, Quadrant.SPACE_4};
        }
        return new int[]{Quadrant.SPACE_5, Quadrant.SPACE_6, Quadrant.SPACE_7, Quadrant.SPACE_8};
    }

    private int getPlayOrder(int space) {
        return space >= Quadrant.SPACE_5 ? PlayOrder.ASCENDING : PlayOrder.DESCENDING;
    }

    private List<Card> getSpace(int space) {
        return spaces.get(space - 1);
    }

    private void normalizeIndex() {
        if (deck.getCards().isEmpty()) {
            currentIndex = FIRST_VISIBLE_INDEX;
            return;
        }

        if (currentIndex == -1) {
            currentIndex = FIRST_VISIBLE_INDEX;
        } else if (currentIndex <= -2) {
            currentIndex = deck.getCards().size() - LAST_VISIBLE_OFFSET;
        } else if (currentIndex > deck.getCards().size()) {
            currentIndex = deck.getCards().size();
        }
    }
}
