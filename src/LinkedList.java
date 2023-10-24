import java.util.NoSuchElementException;

public class LinkedList<E> {
	public Node<E> header, tail;
	private int length;

	public LinkedList() {
		this.header = new Node<>(null);
		this.tail = new Node<>(null);
		this.length = 0;
	}

	/**
	 * Returns the first element in this list
	 * 
	 * @return the first element in this list
	 * @throws IllegalArgumentException if list is empty
	 */
	public Node<E> getFirst() {
		if (this.header.next == null) {
			throw new NoSuchElementException();
		}

		return this.header.next;
	}

	/**
	 * Returns the element with the specified position in this list
	 * 
	 * @param index the position of the element
	 * @return the element at the specified position in this list
	 * @throws IllegalArgumentException if index is invalid
	 */
	public Node<E> get(int index) {
		if (index < 0 || index >= this.length) {
			throw new IllegalArgumentException();
		}

		// segments search time in half
		if (index <= this.length / 2) {
			// traverses to index from head
			Node<E> traverser = this.header;
			for (int i = 0; i < index; i++) {
				traverser = traverser.next;
			}

			return traverser.next;

		}

		// traverses to index from tail
		Node<E> traverser = this.tail;
		for (int i = this.length - 1; i > index; i--) {
			traverser = traverser.prev;
		}

		return traverser.prev;
	}

	/**
	 * Replaces the object at the specified position
	 * 
	 * @param index the position to replace
	 * @param card  the element to be stored
	 * @return the previous value of the element at index
	 * @throws IllegalArgumentException if index is invalid
	 */
	public E set(int index, E data) {
		return null;
	}

	/*
	 * Inserts new node to front
	 */
	public boolean addFirst(E data) {
		Node<E> newNode = new Node<>(data, null, this.header.next);
		if (this.length == 0) {
			this.tail.prev = newNode;

		} else {
			this.header.next.prev = newNode;
		}

		this.header.next = newNode;
		this.length++;

		return true;
	}

	/**
	 * Adds the object x to the end of the list.
	 * 
	 * @param x the element to be added to this list
	 * @return true
	 */
	public boolean add(E data) {
		Node<E> newNode = new Node<>(data, this.tail.prev, null);
		if (this.length == 0) {
			this.header.next = newNode;

		} else {
			this.tail.prev.next = newNode;
		}

		this.tail.prev = newNode;
		this.length++;

		return true;
	}

	/**
	 * Adds the object x at the specified position
	 * 
	 * @param index the position to add the element
	 * @param x     the element to be added to the list
	 * @return true if the operation succeeded, false otherwise
	 * @throws IllegalArgumentException if index is invalid
	 */
	public boolean add(int index, E data) {
		if (index < 0 || index > this.length) {
			throw new IllegalArgumentException();
		}

		if (index == 0) {
			return this.addFirst(data);

		}

		if (index == this.length) {
			return this.add(data);
		}

		// segments search time in half
		Node<E> traverser = this.get(index);
		Node<E> newNode = new Node<>(data, traverser.prev, traverser);

		traverser.prev.next = newNode;
		traverser.prev = newNode;

		this.length++;
		return true;
	}

	/**
	 * Returns the index of the specified data
	 *
	 * @param data the element to look for
	 * @return the index of the data in the list, or -1 if it is not contained
	 *         within the list
	 */
	public int indexOf(E data) {
		return -1;
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 *
	 * @param element element whose presence in this List is to be tested.
	 * @return <code>true</code> if the specified element is present;
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(E element) {
		return true;
	}

	/**
	 * Tests if this list has no elements.
	 * 
	 * @return <tt>true</tt> if this list has no elements; <tt>false</tt>
	 *         otherwise.
	 */
	public boolean isEmpty() {
		return this.length == 0;
	}

	/**
	 * Returns the number of elements in this list
	 * 
	 * @return the number of elements in this list
	 */
	public int size() {
		return this.length;
	}

	/**
	 * Clears all elements from list
	 */
	public void clear() {
		this.header.next = null;
		this.tail.prev = null;

		this.length = 0;
	}

	public String toString() {
		String message = "[";
		Node<E> traverser = this.header;

		// iterates all nodes before the last node
		while (traverser.next != null) {
			if (traverser != header) {
				message += traverser.data + ", ";
			}

			traverser = traverser.next;
		}

		// edge case
		if (traverser != header) {
			message += traverser.data;
		}

		message += "]";
		return message;
	}

}
