package list;

import dlr.Dlr;

import java.util.*;

public class DlrList<E> extends Dlr<E> implements List<E>  {


    /**
     * Class implementing the ListIterator by extending the DlrIterator
     */
    private class DlrListIterator extends DlrIterator implements ListIterator<E> {

        /** Boolean value which indicates if the previous-Method has been called or not
         *  in order to ensure the remove method can only be carried out if the previous method
         *  or the next method of the superclass DlrIterator has been called at least once
         */
        private boolean prevHasBeenCalled;

        public DlrListIterator(int index) {
            super();
            // Set lastReturned to the predecessor of node number index.
            // Note that this also works with an empty list, because then
            // the only legal value of index is zero. (This is checked
            // by methods in DlrList itself.)
            this.lastReturned = getNode(index).getPrev();
            this.nextIndex = index;
        }

        @Override
        public boolean hasPrevious() {
            return (nextIndex > 0);
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
            prevHasBeenCalled = false;
            return lastReturned.getData();
        }

        @Override
        public E previous() {
            checkForComodification();
            if ( ! hasPrevious() ) {
                throw new NoSuchElementException();
            }
            E data = lastReturned.getData();
            lastReturned = lastReturned.getPrev();
            nextIndex--;
            nextHasBeenCalled = false;
            prevHasBeenCalled = true;
            return data;
        }

        @Override
        public void remove() {
            checkForComodification();

            if(nextHasBeenCalled) {
                Node newLr = lastReturned.getPrev();
                // Use method unlink() from the outer class DlrList.
                unlink(lastReturned);
                lastReturned = newLr;
                // Decrement the nextIndex, since a new element
                // sits where the deleted one was before
                // (or the list is empty ...)
                nextIndex--;
            }
            else if(prevHasBeenCalled) {

                // The element to be removed is the one returned by the last call of previous,
                // therefore is is the next element of lastReturned
                unlink(lastReturned.getNext());

                // The next call of previous should return the element before the deleted one and
                // the next call of next the one after the deleted one. Therefore, you do not change the
                // current index or the lastReturned element

            }
            else {
                throw new IllegalStateException();
            }

            nextHasBeenCalled = false;
            prevHasBeenCalled = false;
            localModCtr++;
        }


        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return (nextIndex - 1);
        }

        @Override
        public void set(E e) {
            checkForComodification();

            if(nextHasBeenCalled) {
                lastReturned.setData(e);
            }
            else if(prevHasBeenCalled) {
                lastReturned.getNext().setData(e);
            }
            else {
                throw new IllegalStateException();
            }
        }

        @Override
        public void add(E e) {
            checkForComodification();
            Node n = new Node(e);
            insertBefore(lastReturned.getNext(),n);
            nextHasBeenCalled = false;
            prevHasBeenCalled = false;
            localModCtr++;
        }
    }

    /**
     * Inner class implementing the SubList concept
     */
    private static class DlrSubList<E> extends DlrList<E> {

        private final DlrList<E> root;
        private final DlrSubList<E> parent;
        private final int offset;
        private int size;

        /**
         * Constructs a sublist of a DlrList, which is
         * not a SubList itself.
         */
        public DlrSubList(DlrList<E> root, int fromIndex, int toIndex) {
            this.root = root;
            this.parent = null;
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
            this.modCtr = root.modCtr;
        }

        /**
         * Constructs a sublist of another SubList.
         */
        protected DlrSubList(DlrSubList<E> parent, int fromIndex, int toIndex) {
            this.root = parent.root;
            this.parent = parent;
            this.offset = parent.offset + fromIndex;
            this.size = toIndex - fromIndex;
            this.modCtr = root.modCtr;
        }

        static void subListRangeCheck(int fromIndex, int toIndex, int size) {
            if (fromIndex < 0)
                throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
            if (toIndex > size)
                throw new IndexOutOfBoundsException("toIndex = " + toIndex);
            if (fromIndex > toIndex)
                throw new IllegalArgumentException("fromIndex(" + fromIndex +
                        ") > toIndex(" + toIndex + ")");
        }

        private void checkForComodification() {
            if (root.modCtr != this.modCtr)
                throw new ConcurrentModificationException();
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+size;
        }

        private void updateSizeAndModCount(int sizeChange) {
            DlrSubList<E> slist = this;
            do {
                slist.size += sizeChange;
                slist.modCtr = root.modCtr;
                slist = slist.parent;
            } while (slist != null);
        }

        public E set(int index, E element) {
            Objects.checkIndex(index, size);
            checkForComodification();
            return root.set(offset + index, element);
        }

        public E get(int index) {
            Objects.checkIndex(index, size);
            checkForComodification();
            return root.get(offset + index);
        }

        public int size() {
            checkForComodification();
            return size;
        }

        public void add(int index, E element) {
            rangeCheckForAdd(index);
            checkForComodification();
            root.add(offset + index, element);
            updateSizeAndModCount(1);
        }

        public E remove(int index) {
            Objects.checkIndex(index, size);
            checkForComodification();
            E result = root.remove(offset + index);
            updateSizeAndModCount(-1);
            return result;
        }

        public boolean addAll(Collection<? extends E> c) {
            return addAll(size, c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize==0)
                return false;
            checkForComodification();
            root.addAll(offset + index, c);
            updateSizeAndModCount(cSize);
            return true;
        }

        public Iterator<E> iterator() {
            return listIterator();
        }

        public ListIterator<E> listIterator(int index) {
            checkForComodification();
            rangeCheckForAdd(index);

            return new ListIterator<>() {
                private final ListIterator<E> i =
                        root.listIterator(offset + index);

                public boolean hasNext() {
                    return nextIndex() < size;
                }

                public E next() {
                    if (hasNext())
                        return i.next();
                    else
                        throw new NoSuchElementException();
                }

                public boolean hasPrevious() {
                    return previousIndex() >= 0;
                }

                public E previous() {
                    if (hasPrevious())
                        return i.previous();
                    else
                        throw new NoSuchElementException();
                }

                public int nextIndex() {
                    return i.nextIndex() - offset;
                }

                public int previousIndex() {
                    return i.previousIndex() - offset;
                }

                public void remove() {
                    i.remove();
                    updateSizeAndModCount(-1);
                }

                public void set(E e) {
                    i.set(e);
                }

                public void add(E e) {
                    i.add(e);
                    updateSizeAndModCount(1);
                }
            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size);
            return new DlrSubList<>(this, fromIndex, toIndex);
        }

    }


// =====================================================================
//    Class DlrList protected  methods
// ======================================================================

    protected void deleteElementsEqualTo(Object o) {
        removeIf(e -> Objects.equals(o, e));
    }

// =====================================================================
//    Class DlrList public constructors and methods
// ======================================================================


    public DlrList() {
        super();
    }

    @Override
    public boolean contains(Object o) {
        Node n = find(o);
        return (n != null);
    }

    @Override
    public Iterator<E> iterator() {
        return new DlrIterator();
    }

    @Override
    public Object[] toArray() {
        int idx = 0;
        Object[] a = new Object[size];
        for (E e : this) a[idx++] = e;
        return a;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if ( a == null ) throw new NullPointerException();
        int idx = 0;
        if ( size <= a.length ) {
            for (E e : this) //noinspection unchecked
                a[idx++] = (T) e;
            if ( a.length > size ) a[size] = null;
            return a;
        }
        else {
            @SuppressWarnings("unchecked")
            T[] b = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
            for (E e : this) //noinspection unchecked
                b[idx++] = (T) e;
            return b;
        }
    }

    @Override
    public boolean add(E e) {
        return append(e);
    }

    @Override
    public boolean remove(Object o) {
        Node n = find(o);
        if ( n != null ) {
            unlink(n);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if ( c == null ) throw new NullPointerException("c must not be null");
        for ( Object e : c ) {
            if ( ! contains(e) ) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if ( c == null ) throw new NullPointerException("c must not be null");
        c.forEach(this::add);
        return  (c.size() > 0);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if ( c == null ) throw new NullPointerException("c must not be null");
        Node n = getNode(index);
        c.forEach(e -> insertBefore(n,new Node(e)));
        return (c.size() > 0);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if ( c == null ) throw new NullPointerException("c must not be null");
        int oldSize = size;
        c.forEach(this::deleteElementsEqualTo);
        return (oldSize > size);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if ( c == null ) throw new NullPointerException("c must not be null");
        int oldSize = size;
        removeIf(e -> !c.contains(e));
        return (oldSize > size);
    }



    @Override
    public E get(int index) {
        Objects.checkIndex(index,size());
        return getNode(index).getData();
    }

    @Override
    public E set(int index, E element) {
        return setByIndex(index,element);
    }

    @Override
    public void add(int index, E element) {
        insertAtIndex(index,element);
    }

    @Override
    public E remove(int index) {
        return removeByIndex(index);
    }

    @Override
    public int indexOf(Object o) {
        return dlrIndexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return dlrLastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return new DlrListIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new DlrListIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        DlrSubList.subListRangeCheck(fromIndex, toIndex, size);
        return new DlrSubList<>(this,fromIndex,toIndex);
    }

    @Override public int hashCode() {
        int hashCode = 1;
        for (E e : this)
            hashCode = 31*hashCode + (e==null ? 0 : e.hashCode());
        return hashCode;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        // Note that this takes care of the null reference check:
        // (null instance of C) returns false for any class or interface C
        if (!(o instanceof List))
            return false;

        ListIterator<E> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            E o1 = e1.next();
            Object o2 = e2.next();
            if (!(Objects.equals(o1, o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

}
