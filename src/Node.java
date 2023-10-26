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

	public String toString() {
		return String.format("%s: %s", super.toString(), this.data);
	}
}
