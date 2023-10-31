import java.util.ArrayList;
import java.awt.Dimension;

public class Card extends Sprite {
    private int rank;
    private int suit;

    public static final String[] SUITS = { "clubs", "diamonds", "hearts",
            "spades" };

    public static final int CLUBS = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int SPADES = 3;

    public static final String[] RANKS = { null, null, "2", "3", "4", "5",
            "6", "7", "8", "9", "10", "jack", "queen", "king",
            "ace" };

    public static final int JACK = 11;
    public static final int QUEEN = 12;
    public static final int KING = 13;
    public static final int ACE = 14;

    /**
     * Constructor rank can be 2, 3, ..., 10, JACK, QUEEN, KING, ACE suit can be
     * CLUBS, DIAMONDS, HEARTS, SPADES
     */
    public Card(int[] coords, Dimension size) {
        super(coords, size);

    }

    public void setRank(int rank) {
        if (rank < 2 || rank > ACE) {
            throw new IllegalArgumentException(
                    "invalid rank: " + rank);
        }

        this.rank = rank;
    }

    public void setSuit(int suit) {
        if (suit < 0 || suit > 3) {
            throw new IllegalArgumentException(
                    "invalid suit: " + suit);
        }

        this.suit = suit;
    }

    /**
     * Returns the suit of the card: CLUBS, DIAMONDS, HEARTS, SPADES
     */
    public int getSuit() {
        return this.suit;
    }

    /**
     * Returns the rank of the card: 2, 3, ..., 10, JACK, QUEEN, KING, ACE
     */
    public int getRank() {
        return this.rank;
    }

    /**
     * Returns a string representation of the card
     */
    public String toString() {
        return RANKS[rank] + " of " + SUITS[suit];
    }

    /**
     * Returns a negative number if this object is lower in rank than c, 0 if
     * the cards are equal rank, and a positive number if this object is higher
     * in rank than c. Aces are considered 'high'.
     */
    public int compareRank(Card c) {
        return rank - c.rank;
    }

    /**
     * Returns true if the objects are equals, false otherwise
     */
    public boolean equals(Card c) {
        return rank == c.rank && suit == c.suit;
    }
}