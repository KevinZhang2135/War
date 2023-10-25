import java.util.NoSuchElementException;

/**
 * Interface for lists.
 */
public interface List<E> {

    /**
     * Returns the first element in this list
     * 
     * @return the first element in this list
     * @throws NoSuchElementException if list is empty
     */
    public Node<E> getFirst();

    /**
     * Returns the element with the specified position in this list
     * 
     * @param index the position of the element
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public Node<E> get(int index);

    /**
     * Replaces the object at the specified position
     * 
     * @param index   the position to replace
     * @param element the element to be stored
     * @return the previous value of the element at index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public E set(int index, E element);

    /**
     * Inserts new node to front
     * 
     * @param element the element to be added to the list
     * @return true
     */
    public boolean addFirst(E element);

    /**
     * Adds the object x to the end of the list.
     * 
     * @param element the element to be added to the list
     * @return true
     */
    public boolean add(E element);

    /**
     * Adds the object x at the specified position
     * 
     * @param index   the position to add the element
     * @param element the element to be added to the list
     * @return true
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public boolean add(int index, E element);

    /**
     * Removes first object from the list
     * 
     * @return the object that was removed
     * @throws NoSuchElementException if list is empty
     */
    public E removeFirst();

    /**
     * Removes last object from the list
     * 
     * @return the object that was removed
     * @throws NoSuchElementException if list is empty
     */
    public E removeLast();

    /**
     * Removes the object at the specified position
     * 
     * @param index the position to remove
     * @return the object that was removed
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public E remove(int index);

    /**
     * Returns the index of the specified data
     *
     * @param element the element to look for
     * @return the index of the data in the list, or -1 if it is not contained
     *         within the list
     */
    public int indexOf(E element);

    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     *
     * @param element element whose presence in this list is to be tested.
     * @return <code>true</code> if the specified element is present;
     *         <code>false</code> otherwise.
     */
    public boolean contains(E element);

    /**
     * Tests if this list has no elements.
     * 
     * @return <tt>true</tt> if this list has no elements; <tt>false</tt>
     *         otherwise.
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this list
     * 
     * @return the number of elements in this list
     */
    public int size();

    /**
     * Clears all elements from list
     */
    public void clear();

}