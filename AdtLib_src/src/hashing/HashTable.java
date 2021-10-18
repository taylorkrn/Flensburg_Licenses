package hashing;

import list.DlrList;

import java.util.*;
import java.util.function.Consumer;

/**
 * Class implementing a generic hash table
 * @param <E> The generic type of the objects to be managed in this table
 */
@SuppressWarnings("WhileLoopReplaceableByForEach")
public class HashTable<E> {

    /**
     * Hash table array, each element carrying the
     * head of a (possibly empty) bucket list.
     */
    final List<E>[] a;

    /** Number of elements in the table */
    int size;

    /** Modification counter */
    int modCtr;

    /* ----------------------------------------------------------------------
     *  Inner class HashTableIterator
     * --------------------------------------------------------------------- */

    private class HashTableIterator implements Iterator<E> {

        int localModCtr;
        boolean nextHasBeenCalled;

        Iterator<E> currentBucketListIterator;
        int currentTableIdx;

        public HashTableIterator() {
            currentBucketListIterator = a[0].iterator();
            localModCtr = modCtr;
        }

        protected void checkForComodification() {
            if ( modCtr != localModCtr ) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public boolean hasNext() {
            checkForComodification();
            while ( ! currentBucketListIterator.hasNext() ) {
                if ( ++currentTableIdx >= a.length ) return false;
                currentBucketListIterator = a[currentTableIdx].iterator();
            }
            return true;
        }

        @Override
        public E next() {
            checkForComodification();
            if ( ! hasNext() ) throw new NoSuchElementException();
            nextHasBeenCalled = true;
            return currentBucketListIterator.next();
        }

        @Override
        public void remove() {
            checkForComodification();
            if ( ! nextHasBeenCalled ) throw new IllegalStateException();
            currentBucketListIterator.remove();
            nextHasBeenCalled = false;
            size--;
            modCtr++;
            localModCtr++;
        }

        @Override
        public void forEachRemaining(Consumer<? super E> action) {
            while ( hasNext() ) {
                action.accept(next());
            }
        }
    }


    /**
     * Constructor
     * @param p  Length of the internal hash table array to be created
     */
    public HashTable(int p) {
        //noinspection unchecked
        a = (List<E>[])new DlrList[p];
        for ( int i = 0; i < p; i++ ) {
            a[i] = new DlrList<>();
        }
    }

    /**
     * Insert element into the hash table, if it does not exist already
     * @param element The object to be inserted. It will be appended
     *                to the list stored at a[k], where k is
     *                calculated as Math.floorMod(element.hashCode(),p)
     *
     * @return true If the object was not already in the hash table
     *              and has therefore been inserted,
     *         false otherwise
     */
    boolean add(E element) {
        Objects.requireNonNull(element);
        int idx = Math.floorMod(element.hashCode(),a.length);
        ListIterator<E> ite = a[idx].listIterator();
        //noinspection WhileLoopReplaceableByForEach
        while ( ite.hasNext() ) {
            if ( element.equals(ite.next()) ) return false;
        }
        a[idx].add(element);
        size++;
        modCtr++;
        return true;
    }

    /**
     * Remove an element from the hash table.
     * @param element The element to be removed. The element will be searched
     *                in the bucket list a[k], where k is calculated as
     *                Math.floorMod(element.hashCode(),p)
     * @return true if the element could be found and deleted from the bucket list,
     *              otherwise false.
     */
    boolean remove(Object element) {
        Objects.requireNonNull(element);
        int idx = Math.floorMod(element.hashCode(),a.length);
        ListIterator<E> ite = a[idx].listIterator();
        while ( ite.hasNext() ) {
            if ( element.equals(ite.next())) {
                ite.remove();
                size--;
                return true;
            }
        }
        return false;
    }



    /**
     * Check whether an element is contained in the hash table
     * @param element The element to be checked for presence in the table.
     *                The element will be searched
     *                in the bucket list a[k], where k is calculated as
     *                Math.floorMod(element.hashCode(),p). For comparison, the
     *                element's equals method will be applied.
     * @return true If the element could be found, otherwise false
     */
    boolean contains(Object element) {
        Objects.requireNonNull(element);
        int idx = Math.floorMod(element.hashCode(),a.length);
        ListIterator<E> ite = a[idx].listIterator();
        while ( ite.hasNext() ) {
            if ( element.equals(ite.next())) return true;
        }
        return false;
    }

    /**
     * Size
     * @return The number of elements contained in the hash table
     */
    public int size() {
        return size;
    }

    /**
     * Check if empty
     * @return true if and only if the table does not contain any elements
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    Iterator<E> iterator() {
        return new HashTableIterator();
    }

    void clear() {
        size = 0;
        modCtr++;
        for (List<E> es : a) {
            es.clear();
        }
    }

    public Object[] toArray() {
        Object[] objArray = new Object[size];
        int idx = 0;
        for (List<E> es : a) {
            Iterator<E> ite = es.iterator();
            while (ite.hasNext()) {
                objArray[idx++] = ite.next();
            }
        }
        return objArray;
    }

    public <T> T[] toArray(T[] otherArray) {
        if ( otherArray == null ) throw new NullPointerException();
        int idx = 0;
        if ( size <= otherArray.length ) {
            for (List<E> es : a) {
                Iterator<E> ite = es.iterator();
                while (ite.hasNext()) {
                    //noinspection unchecked
                    otherArray[idx++] = (T) ite.next();
                }
            }
            if ( otherArray.length > size ) otherArray[size] = null;
            return otherArray;
        }
        else {
            @SuppressWarnings("unchecked")
            T[] b = (T[]) java.lang.reflect.Array.newInstance(otherArray.getClass().getComponentType(), size);
            idx = 0;
            for (List<E> es : a) {
                Iterator<E> ite = es.iterator();
                while (ite.hasNext()) {
                    //noinspection unchecked
                    b[idx++] = (T) ite.next();
                }
            }
            return b;
        }
    }

    public int hashCode() {
        int h = 0;
        Iterator<E> ite = iterator();
        while ( ite.hasNext() ) {
            E element = ite.next();
            if ( element != null ) h += element.hashCode();
        }
        return h;
    }

}
