import java.util.NoSuchElementException;

public class LinkedList<E> implements List<E> {
	private Node<E> header, tail;
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
	 * @throws NoSuchElementException if list is empty
	 */
	public Node<E> getFirst() {
		if (this.length == 0) {
			throw new NoSuchElementException();
		}

		return this.header.next;
	}

	/**
	 * Returns the element with the specified position in this list
	 * 
	 * @param index the position of the element
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	public Node<E> get(int index) {
		if (index < 0 || index >= this.length) {
			throw new IndexOutOfBoundsException();
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
	 * @param index   the position to replace
	 * @param element the element to be stored
	 * @return the previous value of the element at index
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	public E set(int index, E element) {
		if (index < 0 || index >= this.length) {
			throw new IndexOutOfBoundsException();
		}

		Node<E> traverser = this.get(index);
		Node<E> replacedNode = traverser;
		traverser.data = element;

		return replacedNode.data;
	}

	/**
	 * Inserts new node to front
	 * 
	 * @param element the element to be added to the list
	 * @return true
	 */
	public boolean addFirst(E element) {
		Node<E> newNode = new Node<>(element, null, this.header.next);
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
	 * @param element the element to be added to the list
	 * @return true
	 */
	public boolean add(E element) {
		Node<E> newNode = new Node<>(element, this.tail.prev, null);
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
	 * @param index   the position to add the element
	 * @param element the element to be added to the list
	 * @return true
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	public boolean add(int index, E element) {
		if (index < 0 || index > this.length) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			return this.addFirst(element);
		}

		if (index == this.length) {
			return this.add(element);
		}

		// segments search time in half
		Node<E> traverser = this.get(index);
		Node<E> newNode = new Node<>(element, traverser.prev,
				traverser);

		traverser.prev.next = newNode;
		traverser.prev = newNode;

		this.length++;
		return true;
	}

	/**
	 * Removes first object from the list
	 * 
	 * @return the object that was removed
	 * @throws NoSuchElementException if list is empty
	 */
	public E removeFirst() {
		if (this.length == 0) {
			throw new NoSuchElementException();
		}

		Node<E> removedNode = this.header.next;
		this.header.next = removedNode.next;

		if (removedNode.next != null) {
			removedNode.next.prev = null;

		} else {
			this.tail.prev = null;
		}

		this.length--;
		return removedNode.data;
	}

	/**
	 * Removes last object from the list
	 * 
	 * @return the object that was removed
	 * @throws NoSuchElementException if list is empty
	 */
	public E removeLast() {
		if (this.length == 0) {
			throw new NoSuchElementException();
		}

		Node<E> removedNode = this.tail.prev;
		this.tail.prev = removedNode.prev;

		if (removedNode.prev != null) {
			removedNode.prev.next = null;

		} else {
			this.header.next = null;
		}

		this.length--;
		return removedNode.data;
	}

	/**
	 * Removes the object at the specified position
	 * 
	 * @param index the position to remove
	 * @return the object that was removed
	 * @throws IndexOutOfBoundsException if index is invalid
	 */
	public E remove(int index) {
		if (index < 0 || index >= this.length) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			return this.removeFirst();
		}

		if (index == this.length - 1) {
			return this.removeLast();
		}

		Node<E> removedNode = this.get(index);
		removedNode.prev.next = removedNode.next;
		removedNode.next.prev = removedNode.prev;

		this.length--;
		return removedNode.data;
	}

	/**
	 * Returns the index of the specified data
	 *
	 * @param element the element to look for
	 * @return the index of the data in the list, or -1 if it is not contained
	 *         within the list
	 */
	public int indexOf(E element) {
		Node<E> traverser = this.header.next;
		int index = 0;

		while (traverser != null) {
			if (traverser.data == element
					|| traverser.data.equals(element)) {
				return index;
			}

			traverser = traverser.next;
			index++;
		}

		return -1;
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 *
	 * @param element element whose presence in this list is to be tested.
	 * @return <code>true</code> if the specified element is present;
	 *         <code>false</code> otherwise.
	 */
	public boolean contains(E element) {
		return this.indexOf(element) != -1;
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

	@Override
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
