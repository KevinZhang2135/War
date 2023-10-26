import java.util.Scanner;

class WarGame {
	public static Scanner sc = new Scanner(System.in);
	public static Deck playerDeck, computerDeck, contestedCards;

	public static void main(String[] args) {
		// creates decks
		playerDeck = new Deck();
		computerDeck = new Deck();
		contestedCards = new Deck();
		setupDecks();

		// main game loop
		boolean runtime = true;
		System.out.println("War");

		while (runtime) {
			System.out.println("Player deck size: " + playerDeck.size());
			System.out.println("Computer deck size: " + computerDeck.size() + "\n");

			System.out.println("Type \"S\" to shuffle player deck; type \"Q\" to quit;");
			System.out.print("type anything else to fight a battle: ");
			String response = sc.nextLine();

			switch (response) {
				case "s":
					playerDeck.shuffle();
					System.out.println("Player deck has been shuffled.\n");
					break;

				case "q":
					runtime = false;
					continue;

				default:
					fightBattle();
					System.out.println();
					break;
			}

			if (playerDeck.isEmpty() || computerDeck.isEmpty()) {
				runtime = false;
			}
		}
	}

	/**
	 * Splits 52 cards evenly among player and computer decks
	 * 
	 * @throws NullPointerException when either the player or computer decks are
	 *                              null
	 */
	public static void setupDecks() {
		if (playerDeck == null || computerDeck == null) {
			throw new NullPointerException();
		}

		// fills deck with 52 shuffled cards
		playerDeck.fill();
		playerDeck.shuffle();

		// distributes half of cards in player deck for the computer deck
		int deckSize = playerDeck.size();
		for (int i = 0; i < deckSize / 2; i++) {
			computerDeck.add(playerDeck.draw());
		}
	}

	/**
	 * Simulates a battle of war
	 * 
	 * @throws NullPointerException when either the player or computer decks are
	 *                              null
	 */
	public static void fightBattle() {
		if (playerDeck == null || computerDeck == null) {
			throw new NullPointerException();
		}

		Card playerTopCard = playerDeck.draw();
		Card computerTopCard = computerDeck.draw();
		contestedCards.add(playerTopCard);
		contestedCards.add(computerTopCard);

		System.out.println("Player drew a " + playerTopCard);
		System.out.println("Computer drew a " + computerTopCard);

		int diff = playerTopCard.compareRank(computerTopCard);
		if (diff < 0) {
			// computer won
			System.out.println(String.format("Computer won %d cards.", contestedCards.size()));

			// adds all contested cards to computer deck
			while (!contestedCards.isEmpty()) {
				computerDeck.add(contestedCards.draw());
			}

		
		} else if (diff > 0) {
			// player won
			System.out.println(String.format("Player won %d cards.", contestedCards.size()));

			// adds all contested cards to player deck
			while (!contestedCards.isEmpty()) {
				playerDeck.add(contestedCards.draw());
			}

		} else {
			// war is declared
			System.out.println("\nWar is declared.");
			if (playerDeck.size() < 4) {
				System.out.println("Player ran out of cards to declare war.");
				return;

			} else if (computerDeck.size() < 4) {
				System.out.println("Computer ran out of cards to declare war.");
			}

			System.out.println("Player and computer each draw 3 cards.");
			for (int i = 0; i < 3; i++) {
				contestedCards.add(playerDeck.draw());
				contestedCards.add(computerTopCard);
			}

			fightBattle();
		}
	}
}

/**********************************************************************
 * What is the point of the interface List? What does it mean for a Java class
 * (e.g., LinkedList) to implement an interface?
 **********************************************************************/

/**********************************************************************
 * List whatever help (if any) that you received
 **********************************************************************/

/**********************************************************************
 * Describe any serious problems you encountered.
 **********************************************************************/

/**********************************************************************
 * List any other comments here. Feel free to provide any feedback on how much
 * you learned from doing the assignment, and whether you enjoyed doing it.
 **********************************************************************/
