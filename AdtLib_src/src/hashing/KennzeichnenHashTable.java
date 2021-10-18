package hashing;

import FlensburgData.FlensburgPerson;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;

public class KennzeichnenHashTable extends HashTable<FlensburgPerson> {


    /**
     * Constructor
     *
     * @param p Length of the internal hash table array to be created
     */
    public KennzeichnenHashTable(int p) {
        super(p);
    }

    /**
     * Insert element into the hash table, if it does not exist already
     * @param element The object to be inserted.
     * @return true If the object was not already in the hash table
     *              and has therefore been inserted,
     *         false otherwise
     */
    @Override
    public boolean add(FlensburgPerson element) {
        Objects.requireNonNull(element);
        int idx = Math.floorMod(element.kfzHashCode(),a.length);
        ListIterator<FlensburgPerson> ite = a[idx].listIterator();
        //noinspection WhileLoopReplaceableByForEach
        while ( ite.hasNext() ) {
            if ( ite.next().kfzEquals(element.getKfz()) ) return false;
        }
        a[idx].add(element);
        size++;
        modCtr++;
        return true;
    }

    /**
     * Remove an element from the hash table.
     * @param kfz The element to be removed.
     * @return true if the element could be found and deleted from the bucket list,
     *              otherwise false.
     */
    public FlensburgPerson remove(String kfz) {
        Objects.requireNonNull(kfz);
        int idx = Math.floorMod(kfz.hashCode(),a.length);
        ListIterator<FlensburgPerson> ite = a[idx].listIterator();
        FlensburgPerson test;
        while ( ite.hasNext() ) {
            test = ite.next();
            if ( test.kfzEquals(kfz)) {
                ite.remove();
                size--;
                return test;
            }
        }
        return null;
    }

    /**
     * Check whether an element is contained in the hash table
     * @param kfz The element to be checked for presence in the table
     * @return true If the element could be found, otherwise false
     */
    public FlensburgPerson search(String kfz) {
        Objects.requireNonNull(kfz);
        int idx = Math.floorMod(kfz.hashCode(),a.length);
        ListIterator<FlensburgPerson> ite = a[idx].listIterator();
        FlensburgPerson test;
        while ( ite.hasNext() ) {
            test = ite.next();
            if ( test.kfzEquals(kfz)) return test;
        }
        return null;
    }


    @Override
    public int hashCode() {
        int h = 0;
        Iterator<FlensburgPerson> ite = iterator();
        while ( ite.hasNext() ) {
            FlensburgPerson element = ite.next();
            if ( element != null ) h += element.kfzHashCode();
        }
        return h;
    }
}
