class Main {

	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<>();
		list.add(1000);
		list.add(2000);
		list.add(3000);
		list.add(4000);
		list.add(5000, 4);

		System.out.println("Head to tail: " + list.header.showNext());
		System.out.println("Tail to head: " + list.tail.showPrev());		
	}
}
