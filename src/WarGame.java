import java.util.Scanner;

class WarGame {
	public static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		// creates player deck
		Deck playerDeck = new Deck();
		playerDeck.fill();
		playerDeck.shuffle();
		
		// divides player deck in half for the computer deck
		int deckSize = playerDeck.size();
		Deck computerDeck = new Deck();
		for (int i = 0; i < deckSize / 2; i++) {
			computerDeck.add(playerDeck.draw());
		}

		// main game loop
		boolean runtime = true;
		System.out.println("War");
		while (runtime) {
			System.out.println("Player deck size: " + playerDeck.size());
			System.out.println("Computer deck size: " + computerDeck.size() + "\n");
			sc.nextLine();
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
