import java.lang.Math;
import java.awt.Dimension;
import java.util.LinkedList;

public class Deck {
	public LinkedList<Card> cards;
	public LinkedList<Card> discardPile;

	private int x, y;
	private final int TILE_SIZE;

	private ImageHandler imageHandler;

	public Deck(int[] coords, int tileSize, ImageHandler imageHandler) {
		this.cards = new LinkedList<>();
		this.discardPile = new LinkedList<>();

		this.x = coords[0];
		this.y = coords[1];
		this.TILE_SIZE = tileSize;

		this.imageHandler = imageHandler;
	}

	/**
	 * Retrieves the topleft coordinate of the deck
	 * 
	 * @return a coordinate pair as (x, y)
	 */
	public int[] getCoords() {
		return new int[] { this.x, this.y };
	}

	/**
	 * Sets the coordinates of the deck
	 * 
	 * @param x the new x coordinate of the deck
	 * @param y the new y coordinate of the deck
	 */
	public void setCoords(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Retrieves the first card from the discard pile
	 * @return the first card of the discard pile
	 */
	public Card getTopDiscard() {
		return this.discardPile.getFirst();
	}

	/**
	 * Draws a card from the deck and places it into a discard pile
	 * 
	 * @return the card discarded by the deck
	 */
	public Card draw() {
		if (this.cards.isEmpty()) {
			return null;
		}

		Card drawnCard = this.cards.removeFirst();
		drawnCard.frame = 1; // reveals card

		this.discardPile.addFirst(drawnCard);
		return drawnCard;
	}

	/**
	 * Adds all cards to end of deck
	 * Adds all cards in discard pile back into the deck
	 * 
	 * @param cards cards to be added to end of deck
	 */
	public void winCards(LinkedList<Card> wonCards) {
		while (!this.discardPile.isEmpty()) {
			Card wonCard = this.discardPile.removeLast();
			wonCard.frame = 0; // unflips card
			this.cards.add(wonCard);
		}

		while (!wonCards.isEmpty()) {
			Card wonCard = wonCards.removeLast();
			wonCard.frame = 0; // unflips card
			this.cards.add(wonCard);
		}
	}

	/**
	 * replaces all current cards in deck to create a standard 52-card deck
	 */
	public void fill() {
		for (int suit = 0; suit < Card.SUITS.length; suit++) {
			for (int rank = 2; rank < Card.RANKS.length; rank++) {
				String filename = String.format("%s_%s.png", Card.SUITS[suit], Card.RANKS[rank]);

				// randomizes card coordinates in both x and y directions by -10 to 10 pixels
				int[] cardCoords = {
						(int) (Math.random() * (20 + 1)) - 10,
						(int) (Math.random() * (20 + 1)) - 10
				};

				Card card = new Card(
						cardCoords,
						new Dimension(this.TILE_SIZE * 3 / 4, this.TILE_SIZE));

				card.setRank(rank);
				card.setSuit(suit);

				// retrieves images for card according to suit and rank
				card.images.add(this.imageHandler.getImage("card_back.png"));
				card.images.add(this.imageHandler.getImage(filename));

				this.cards.add(card);
			}
		}
	}

	/**
	 * Splits half of the cards in this deck into the other deck
	 * 
	 * @param deck
	 */
	public void splitDeck(Deck deck) {
		int deckSize = this.cards.size();
		for (int i = 0; i < deckSize / 2; i++) {
			deck.cards.add(this.cards.removeFirst());
		}
	}

	/**
	 * Randomizes the order of cards in the deck
	 */
	public void shuffle() {
		for (int i = 0; i < this.cards.size(); i++) {
			int index = (int) (Math.random() * this.cards.size());

			Card card = this.cards.remove(index);
			this.cards.add(card);
		}
	}

	/**
	 * @return a deep copy of the deck
	 */
	public Deck copy() {
		Deck copy = new Deck(
				new int[] { this.x, this.y },
				TILE_SIZE,
				imageHandler);

		copy.cards = (LinkedList<Card>) this.cards.clone();
		return copy;
	}

	@Override
	public String toString() {
		return this.cards.toString();
	}

}
