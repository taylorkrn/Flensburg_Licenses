package hashing;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

public class DlrHashSet<E> implements Set<E> {

    HashTable<E> hashTable;

    public DlrHashSet(int p) {
        hashTable = new HashTable(p);
    }

    @Override
    public int size() {
        return hashTable.size();
    }

    @Override
    public boolean isEmpty() {
        return hashTable.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return hashTable.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return hashTable.iterator();
    }

    @Override
    public Object[] toArray() {
        return hashTable.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return hashTable.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return hashTable.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return hashTable.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Objects.requireNonNull(c);
        for ( Object e : c ) {
            if ( ! hashTable.contains(e) ) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        Objects.requireNonNull(c);
        boolean returnValue = false;
        for ( E e : c ) {
            returnValue |= hashTable.add(e);
        }
        return returnValue;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        Iterator<E> ite = hashTable.iterator();
        boolean returnValue = false;
        while ( ite.hasNext() ) {
            if ( !c.contains(ite.next()) ) {
                ite.remove();
                returnValue = true;
            }
        }
        return returnValue;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean returnValue = false;
        for ( Object o : c ) {
            returnValue |= hashTable.remove(o);
        }
        return returnValue;
    }

    @Override
    public void clear() {
        hashTable.clear();
    }

    @Override
    public int hashCode() {
        return hashTable.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if ( o == this ) return true;
        if ( ! (o instanceof Set) ) return false;
        Collection<?> c = (Collection<?>) o;
        if (c.size() != size()) return false;
        try {
            return containsAll(c);
        } catch (ClassCastException | NullPointerException unused) {
            return false;
        }
    }

}