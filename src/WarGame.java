class WarGame {

	public static void main(String[] args) {
		Deck deck = new Deck();
		deck.fill();
		deck.shuffle();
		
		System.out.println(deck.size());
		while (!deck.isEmpty()) {
			System.out.println(deck.draw());
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
