import java.lang.Math;
import java.util.Arrays;

public class Deck {
	private LinkedList<Card> cards;

	public Deck() {
		this.cards = new LinkedList<>();
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
		return this.cards.removeFirst();
	}

	/**
	 * replaces all current cards in deck to create a standard 52-card deck
	 */
	public void fill() {
		int[] suites = {0, 1, 2, 3};
		int[] ranks = new int[13];
		for (int i = 0; i < ranks.length; i++) {
			ranks[i] = i + 2;
		}

		for (int suite: suites) {
			for (int rank: ranks) {
				Card card = new Card(rank, suite);
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





}
