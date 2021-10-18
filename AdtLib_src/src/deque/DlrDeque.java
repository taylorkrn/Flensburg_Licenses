package deque;

import dlr.Dlr;

import java.util.*;

public class DlrDeque<E> extends Dlr<E> implements Deque<E> {
    @Override
    public void addFirst(E e) {
        insertBefore(listHead.getNext(),new Node(e));
    }

    @Override
    public void addLast(E e) {
        append(e);
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        if ( isEmpty() ) throw new NoSuchElementException();
        E element = listHead.getNext().getData();
        unlink(listHead.getNext());
        return element;
    }

    @Override
    public E removeLast() {
        if ( isEmpty() ) throw new NoSuchElementException();
        E element = listHead.getPrev().getData();
        unlink(listHead.getPrev());
        return element;
    }

    @Override
    public E pollFirst() {
        if ( isEmpty() ) return null;
        E element = listHead.getNext().getData();
        unlink(listHead.getNext());
        return element;
    }

    @Override
    public E pollLast() {
        if ( isEmpty() ) return null;
        E element = listHead.getPrev().getData();
        unlink(listHead.getPrev());
        return element;
    }

    @Override
    public E getFirst() {
        if ( isEmpty() ) throw new NoSuchElementException();
        return listHead.getNext().getData();
    }

    @Override
    public E getLast() {
        if ( isEmpty() ) throw new NoSuchElementException();
        return listHead.getPrev().getData();
    }

    @Override
    public E peekFirst() {
        if ( isEmpty() ) return null;
        return listHead.getNext().getData();
    }

    @Override
    public E peekLast() {
        if ( isEmpty() ) return null;
        return listHead.getPrev().getData();
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        Node n = find(o);
        if ( n != null ) unlink(n);
        return (n != null );
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        Node n = findLast(o);
        if ( n != null ) unlink(n);
        return (n != null );
    }

    @Override
    public boolean add(E e) {
        return append(e);
    }

    @Override
    public boolean offer(E e) {
        return append(e);
    }

    @Override
    public E remove() {
        if ( isEmpty() ) throw new NoSuchElementException();
        E element = listHead.getNext().getData();
        unlink(listHead.getNext());
        return element;
    }

    @Override
    public E poll() {
        if ( isEmpty() ) return null;
        E element = listHead.getNext().getData();
        unlink(listHead.getNext());
        return element;
    }

    @Override
    public E element() {
        if ( isEmpty() ) throw new NoSuchElementException();
        return listHead.getNext().getData();
    }

    @Override
    public E peek() {
        if ( isEmpty() ) return null;
        return listHead.getNext().getData();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if ( c == null ) throw new NullPointerException("c must not be null");
        int sizeOld = size();
        c.forEach(this::add);
        return  (size() > sizeOld);
    }



    protected void deleteElementsEqualTo(Object o) {
        removeIf(e -> Objects.equals(o, e));
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
        int oldSize = size();
        removeIf(e -> !c.contains(e));
        return (oldSize > size());
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
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
    public boolean contains(Object o) {
        for ( E e : this ) {
            if ( Objects.equals(o,e) ) return true;
        }
        return false;
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
    public Iterator<E> descendingIterator() {
        return new DlrReverseIterator();
    }
}
