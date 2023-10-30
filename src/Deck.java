import java.lang.Math;
import java.util.ArrayList;
import java.awt.Dimension;

public class Deck {
	private LinkedList<Card> cards;

	public int x, y;
	private final int TILE_SIZE;

	private ImageHandler imageHandler;
	private ArrayList<Sprite> sprites;

	public Deck(int[] coords, int tileSize, ImageHandler imageHandler, ArrayList<Sprite> sprites) {
		this.cards = new LinkedList<>();

		this.x = coords[0];
		this.y = coords[1];
		this.TILE_SIZE = tileSize;

		this.imageHandler = imageHandler;
		this.sprites = sprites;
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
		for (String suit : Card.SUITS) {
			for (String rank : Card.RANKS) {
				if (rank == null) {
					continue;
				}

				String filename = String.format("%s_%s.png", suit, rank);
				Card card = new Card(
						new int[] { 0, 0 },
						new Dimension(this.TILE_SIZE * 3 / 4, this.TILE_SIZE),
						this.sprites);

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

	@Override
	public String toString() {
		return this.cards.toString();
	}

}
