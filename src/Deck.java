import java.lang.Math;
import java.util.ArrayList;
import java.awt.Dimension;

public class Deck {
	private LinkedList<Card> cards;

	private int x, y;
	private final int TILE_SIZE;

	private ImageHandler imageHandler;

	public Deck(int[] coords, int tileSize, ImageHandler imageHandler) {
		this.cards = new LinkedList<>();

		this.x = coords[0];
		this.y = coords[1];
		this.TILE_SIZE = tileSize;

		this.imageHandler = imageHandler;
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

		for (int i = 0; i < this.cards.size(); i++) {
			Card card = (Card) this.cards.get(i).data;
			card.setCoords(x, y);
		}
	}

	/**
	 * Retrieves the card at the index in deck
	 * @param index the index of the card in deck
	 * @return the card in deck
	 */
	public Card get(int index) {
		return this.cards.get(index).data;
	}

	/**
	 * @param card the card added to the bottom of the deck
	 */
	public void add(Card card) {
		this.cards.add(card);
	}

	/**
	 * @return the card at the top of the deck
	 */
	public Card draw() {
		return this.cards.isEmpty() ? null : this.cards.removeFirst();
	}

	/**
	 * replaces all current cards in deck to create a standard 52-card deck
	 */
	public void fill() {
		for (int suit = 0; suit < Card.SUITS.length; suit++) {
			for (int rank = 2; rank < Card.RANKS.length; rank++) {
				String filename = String.format("%s_%s.png", Card.SUITS[suit], Card.RANKS[rank]);
				Card card = new Card(
						new int[] { this.x, this.y },
						new Dimension(this.TILE_SIZE * 3 / 4, this.TILE_SIZE));

				card.setRank(rank);
				card.setSuit(suit);

				// retrieves images for card according to suit and rank
				card.images.add(this.imageHandler.getImage(filename));
				card.images.add(this.imageHandler.getImage("card_back.png"));

				this.cards.add(card);
			}
		}
	}

	/**
	 * @return true if the deck is empty, false otherwise
	 */
	public boolean isEmpty() {
		return this.cards.isEmpty();
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
	 * @return the size of the deck
	 */
	public int size() {
		return this.cards.size();
	}

	/**
	 * @return a deep copy of the deck
	 */
	public Deck copy() {
		Deck copy = new Deck(
				new int[] { this.x, this.y },
				TILE_SIZE,
				imageHandler);

		copy.cards = this.cards.copy();
		return copy;
	}

	@Override
	public String toString() {
		return this.cards.toString();
	}

}
