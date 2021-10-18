package bprqueue;

import java.util.NoSuchElementException;

public interface BoundedPriorityQueue<E> {

    /** The best priority value */
    int HIGHEST_PRIORITY = 0;
    /** The worst priority value */
    int LOWEST_PRIORITY = 63;

    /**
     * Insert element into bounded priority queue
     * @param e The element to be inserted
     * @param priority Its priority in range HIGHEST_PRIORITY..LOWEST_PRIORITY
     * @return true
     */
    @SuppressWarnings("SameReturnValue")
    boolean add(E e, int priority);

    /**
     * Insert element into bounded priority queue
     * @param e The element to be inserted
     * @param priority Its priority in Range HIGHEST_PRIORITY..LOWEST_PRIORITY
     * @return true
     *
     * This method is equivalent to add, since this priority queue does not
     * enforce any capacity restrictions.
     */
    boolean offer(E e, int priority);

    /**
     * Retrieve, but do not remove, an element from the queue, such that
     * no other element in the queue has a higher priority.
     * @return null if the queue is empty, otherwise an element with highest priority.
     */
    E peek();

    /**
     * Retrieve, but do not remove, an element from the queue, such that
     * no other element in the queue has a higher priority.
     * @return an element with highest priority, if the que is non-empty.
     * @throws NoSuchElementException - if this queue is empty
     */
    E element();

    /**
     * Retrieve and remove an element from the queue, such that
     * no other element in the queue has a higher priority.
     * @return null if the queue is empty, otherwise an element with highest priority.
     */
    E poll();

    /**
     * Retrieve and remove an element from the queue, such that
     * no other element in the queue has a higher priority.
     * @return an element with highest priority, if the queue is non-empty.
     * @throws NoSuchElementException - if this queue is empty
     */
    E remove();

    /**
     * Queue size
     * @return the number of elements contained in the bounded priority queue
     */
    public int size();

    /**
     * Check for emptiness
     * @return true if and only if the queue is empty.
     */
    public boolean isEmpty();

}
