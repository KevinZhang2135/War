public class Node <T>{
	public T data;
	public Node <T> prev, next;

	public Node(T data) {
		this.data = data;
		this.prev = null;
		this.next = null;
		
	}

	public Node(T data, Node<T> prev, Node <T> next) {
		this.data = data;
		this.prev = prev;
		this.next = next;
	}

	public String showNext() {
		if (this.next == null) {			
			return "" + this.next;
		}

		return this.next + " | " + this.next.showNext();
	}

	public String showPrev() {
		if (this.prev == null) {
			return "" + this.prev;
		}

		return this.prev + " | " + this.prev.showPrev();
	}

	public String toString() {
		return String.format("%s: %s", super.toString(), this.data);
	}
}
