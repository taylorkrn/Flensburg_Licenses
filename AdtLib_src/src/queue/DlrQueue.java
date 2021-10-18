package queue;

import dlr.Dlr;

import java.util.*;

public class DlrQueue<E>  extends Dlr<E>  implements Queue<E> {
    @Override
    public boolean contains(Object o) {
        for(E e : this){
            if(e.equals(o)) return true;
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
    public boolean add(E e) {
        return append(e);
    }

    @Override
    public boolean remove(Object o) {
        if ( isEmpty() ) throw new NoSuchElementException();
        Node n = find(o);
        if ( n != null ) {
            unlink(n);
        }
        return ( n != null );
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
    public boolean offer(E e) {
        return append(e);
    }

    @Override
    public E remove() {
        if ( isEmpty() ) throw new NoSuchElementException();
        return poll();
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
        return peek();
    }

    @Override
    public E peek() {
        if ( isEmpty() ) return null;
        return listHead.getNext().getData();
    }
}
