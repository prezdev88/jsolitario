/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cards;

import java.io.Serializable;

/**
 *
 * @author pato
 */
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    private int suit;
    private int number;
    private boolean joker;
    private int cardColor;

    public Card(int suit, int number, int cardColor) {
        this.suit = suit;
        this.number = number;
        this.cardColor = cardColor;
        this.joker = false;
    }

    public Card(int color) {
        this.joker = true;
        this.cardColor = color;
    }

    public String getSuit() {
        switch (suit) {
            case Suit.HEART:
                return "Hearts";
            case Suit.DIAMOND:
                return "Diamonds";
            case Suit.SPADE:
                return "Spades";
            case Suit.CLUB:
                return "Clubs";
            default:
                return null;
        }
    }

    public void setSuit(int suit) {
        this.suit = suit;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isJoker() {
        return joker;
    }

    public void setJoker(boolean joker) {
        this.joker = joker;
    }

    public String getCardColor() {
        switch (cardColor) {
            case CardColor.BLUE:
                return "blue";
            case CardColor.RED:
                return "red";
            default:
                return null;
        }
    }

    public void setCardColor(int cardColor) {
        this.cardColor = cardColor;
    }

    public String getFullCardName() {
        return getNumber() + " of " + getSuit();
    }
}
