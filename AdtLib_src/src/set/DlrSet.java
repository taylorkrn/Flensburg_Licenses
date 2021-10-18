package set;

import dlr.Dlr;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class DlrSet<E> extends Dlr<E> implements Set<E> {

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
    public boolean add(E e) {
        if ( contains(e) ) return false;
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
        int sizeOld = size();
        c.forEach(this::add);
        return  (size() > sizeOld);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if ( c == null ) throw new NullPointerException("c must not be null");
        int oldSize = size();
        removeIf(e -> !c.contains(e));
        return (oldSize > size());
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        if (size() > c.size()) {
            for (Iterator<?> i = c.iterator(); i.hasNext(); )
                modified |= remove(i.next());
        } else {
            for (Iterator<?> i = iterator(); i.hasNext(); ) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
        }
        return modified;
    }

    @Override
    public boolean equals(Object other) {
        if ( this == other ) return true;
        if ( other == null ) return false;
        if ( ! (other instanceof  Set) ) return false;
        @SuppressWarnings("unchecked")
        Set<E> otherSet = (Set<E>)other;
        if ( size() != otherSet.size() ) return false;
        for ( E e : this ) {
            if ( ! otherSet.contains(e) ) return false;
        }
        return true;
    }


}
