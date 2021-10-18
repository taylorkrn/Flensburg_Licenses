package dlr;

import java.util.*;
import java.util.function.Consumer;

/**
 * Core source code for managing doubly linked ring lists
 */
public class Dlr<E> {

    /** List size */
    protected int size;

    /** List head */
    protected final Node listHead;

    /** Current hash code value */
    int hashCode;


    protected class Node {
        E data;
        Node next;
        Node prev;

        public Node() {
            this.data = null;
            this.next = this;
            this.prev = this;
        }

        public Node(E e) {
            this.data = e;
        }

        public E getData() {
            return data;
        }

        public void setData(E data) {
            this.data = data;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

    }


    /** Counter for changing list operations */
    protected int modCtr;

    /**
     * Inner class implementing the Iterator
     */
    protected class DlrIterator implements Iterator<E> {

        /** Expected modification counter value.
         *  All list modification are performed via
         *  methods of the outer class DlrList, which
         *  updates its own field modCtr after every
         *  DlrList-operation changing the list.
         *  localModCtr is used to detect situations where
         *  the DlrListIterator has been invalidated.
         */
        protected int localModCtr;

        /** The index of the list element to be returned by the
         *  next call of next();
         */
        protected int nextIndex;

        /** Boolean value which indicates if the next-Method has been called or not
         *  in order to ensure the remove method can only be carried out if the next method
         *  has been called at least once
         */
        protected boolean nextHasBeenCalled;

        /** The node which has been returned by the previous call
         *  to next, so that the following next call will return
         *  lastReturned.next. This attribute is als appropriately
         *  initialised in the constructor, where no  next operation
         *  has been performed so far.
         */
        protected Node lastReturned;

        public DlrIterator() {
            localModCtr = modCtr;
            nextIndex = 0;
            lastReturned = listHead;
        }

        /**
         * Check for intermediate modifications of the list
         * by methods from class DlrList (instead of using
         * modification methods defined in the DrlListIterator.
         * A modification of the list by DlrList-methods
         * invalidates this DlrListIterator. We use
         * condition modCtr != expModCtr for detecting such
         * a modification.
         */
        protected void checkForComodification() {
            if ( modCtr != localModCtr ) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            return (nextIndex < size);
        }

        @Override
        public E next() {
            checkForComodification();
            if ( ! hasNext() ) {
                throw new NoSuchElementException();
            }
            lastReturned = lastReturned.getNext();
            nextIndex++;
            nextHasBeenCalled = true;
            return lastReturned.getData();
        }

        @Override
        public void remove() {
            checkForComodification();
            if (!nextHasBeenCalled) {
                throw new IllegalStateException();
            }
            Node newLr = lastReturned.getPrev();
            // Use method unlink() from the outer class DlrList.
            unlink(lastReturned);
            lastReturned = newLr;
            // Decrement the nextIndex, since a new element
            // sits where the deleted one was before
            // (or the list is empty ...)
            nextIndex--;
            // set nextHasBeenCalled to false to ensure this method can
            // only be carried out again after next has been called
            // at least once
            nextHasBeenCalled = false;
            // unlink() increments modCtr, therefore our
            // expected modification counter needs to be incremented
            // as well
            localModCtr++;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            if ( action == null ) throw new NullPointerException("action must not be null");
            while ( hasNext() ) action.accept(next());
        }
    }


    /**
     * Inner class implementing the Iterator
     */
    protected class DlrReverseIterator implements Iterator<E> {

        /** Expected modification counter value.
         *  All list modification are performed via
         *  methods of the outer class DlrList, which
         *  updates its own field modCtr after every
         *  DlrList-operation changing the list.
         *  localModCtr is used to detect situations where
         *  the DlrListIterator has been invalidated.
         */
        protected int localModCtr;

        /** The index of the list element to be returned by the
         *  next call of next();
         */
        protected int nextIndex;

        /** Boolean value which indicates if the next-Method has been called or not
         *  in order to ensure the remove method can only be carried out if the next method
         *  has been called at least once
         */
        protected boolean nextHasBeenCalled;

        /** The node which has been returned by the previous call
         *  to next, so that the following next call will return
         *  lastReturned.next. This attribute is als appropriately
         *  initialised in the constructor, where no  next operation
         *  has been performed so far.
         */
        protected Node lastReturned;

        public DlrReverseIterator() {
            localModCtr = modCtr;
            nextIndex = size-1;
            lastReturned = listHead;
        }

        /**
         * Check for intermediate modifications of the list
         * by methods from class DlrList (instead of using
         * modification methods defined in the DrlListIterator.
         * A modification of the list by DlrList-methods
         * invalidates this DlrListIterator. We use
         * condition modCtr != expModCtr for detecting such
         * a modification.
         */
        protected void checkForComodification() {
            if ( modCtr != localModCtr ) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            return (nextIndex >= 0);
        }

        @Override
        public E next() {
            checkForComodification();
            if ( ! hasNext() ) {
                throw new NoSuchElementException();
            }
            lastReturned = lastReturned.getPrev();
            nextIndex--;
            nextHasBeenCalled = true;
            return lastReturned.getData();
        }

        @Override
        public void remove() {
            checkForComodification();
            if (!nextHasBeenCalled) {
                throw new IllegalStateException();
            }
            Node newLr = lastReturned.getNext();
            // Use method unlink() from the outer class DlrList.
            unlink(lastReturned);
            lastReturned = newLr;
            nextHasBeenCalled = false;
            // unlink() increments modCtr, therefore our
            // expected modification counter needs to be incremented
            // as well
            localModCtr++;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            if ( action == null ) throw new NullPointerException("action must not be null");
            while ( hasNext() ) action.accept(next());
        }
    }


// =====================================================================
//    Class Dlr protected  methods
// ======================================================================
 
    /**
     * Remove list element which has already been checked to exist
     * @param n The list element to be removed
     */
    protected void unlink(Node n) {
        Node before = n.prev;
        Node after = n.next;
        before.next = after;
        after.prev = before;
        size--;
        modCtr++;
        E data = n.getData();
        if ( data != null ) {
            hashCode -= data.hashCode();
        }
    }


    /**
     * Insert a node n BEFORE node after
     * @param after The node where n has to be inserted before
     * @param n  The new node to be inserted
     */
    protected void insertBefore(Node after, Node n) {
        Node before = after.prev;
        n.prev = before;
        n.next = after;
        before.next = n;
        after.prev = n;
        size++;
        modCtr++;
        E data = n.getData();
        if ( data != null ) {
            hashCode += data.hashCode();
        }
    }

    /**
     * Find first list node with data equal to given object
     * @param o The object to be compared to the node data
     * @return The first node n satisfying ( o == null and n.data == null )
     *         or ( o != null and o.equals(n.data) ),
     *         if such a node exists, null otherwise.
     */
    protected Node find(Object o) {
        for ( Node n = listHead.next; n != listHead; n = n.next ) {
            if ( Objects.equals(o,n.getData()) ) return n;
        }
        return null;
    }

    /**
     * Find last list node with data equal to given object
     * @param o The object to be compared to the node data
     * @return The last node n satisfying ( o == null and n.data == null )
     *         or ( o != null and o.equals(n.data) ),
     *         if such a node exists, null otherwise.
     */
    protected Node findLast(Object o) {
        for ( Node n = listHead.prev; n != listHead; n = n.prev ) {
            if ( Objects.equals(o,n.getData()) ) return n;
        }
        return null;
    }

    /**
     * A legal position index must be in range 0..theSize.
     * Recall that if idx == theSize, this means that the last
     * element of the list has been read, so that idx indicates the
     * end-of-list position
     * @param idx The index to be checked
     * @throws IndexOutOfBoundsException if idx is less than zero or greater than
     *         the list size.
     */
    protected void checkPositionIndex(int idx) {
        if ( idx < 0 || size < idx ) {
            throw new IndexOutOfBoundsException();
        }
    }

    protected void checkNodeIndex(int idx) {
        if ( idx < 0 || size <= idx ) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Return the node positioned at index idx
     * @param idx Index of the node to be returned;
     *            its legal range is 0..theSize.
     *  @return   For i == theSize, the listHead is returned.
     *            If the list is empty, the only legal value
     *            of idx is 0, and the list head is returned.
     *            Otherwise, the data-carrying node with
     *            number idx (idx in 0..size()-1)
     *            is returned.
     */
    protected Node getNode(int idx) {
        checkPositionIndex(idx);
        Node n;
        // If index is less than half the size, traverse
        // list in forward direction ...
        if ( (size >> 1) > idx ) {
            n = this.listHead.next;
            for ( int i = 0; i < idx; i++ ) {
                n = n.next;
            }
        }
        else {
            // ... else traverse list in backward direction
            n = this.listHead;
            for ( int i = size; i > idx; i-- ) {
                n = n.prev;
            }
        }
        return n;
    }





// =====================================================================
//    Class Dlr protected constructors and methods
// ======================================================================

    protected Dlr() {
        this.listHead = new Node();
        this.size = 0;
    }

    @SuppressWarnings("SameReturnValue")
    protected boolean append(E e) {
        Node newNode = new Node(e);
        insertBefore(listHead,newNode);
        return true;
    }

    protected void insertAtIndex(int index, E element) {
        Node n = getNode(index);
        insertBefore(n,new Node(element));
    }

    protected E getByIndex(int index) {
        return getNode(index).data;
    }

    protected E setByIndex(int index, E element) {
        checkNodeIndex(index);
        Node n = getNode(index);
        E oldElement = n.data;
        n.data = element;
        return oldElement;
    }

    protected E removeByIndex(int index) {
        checkNodeIndex(index);
        Node n = getNode(index);
        E oldElement = n.data;
        unlink(n);
        return oldElement;
    }

    protected int dlrIndexOf(Object o) {
        int idx = 0;
        for (Node n = listHead.next; n != listHead; n = n.next) {
            if (o.equals(n.getData())) return idx;
            idx++;
        }
        return -1;
    }

    protected int dlrLastIndexOf(Object o) {
        Node n;
        int idx;
        for ( n = listHead.prev, idx = size - 1; n != listHead; n = n.prev, idx-- ) {
            if ( Objects.equals(o,n.data) ) return idx;
        }
        return idx;
    }

    protected E first() {
        if ( isEmpty() ) throw new NoSuchElementException();
        return listHead.getNext().getData();
    }


// =====================================================================
//    Class Dlr public methods
// ======================================================================


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public void clear() {
        listHead.next = listHead;
        listHead.prev = listHead;
        size = 0;
        hashCode = 0;
    }

    public int hashCode() {
        return hashCode;
    }

}
