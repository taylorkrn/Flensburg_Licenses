package hashing;

import list.DlrList;

import java.util.*;

import java.lang.Math;

public class OpenHashTable<E>{

    /**
     * Hash table array, each element carrying the
     * head of a (possibly empty) bucket list.
     */
    final Object[] a;

    /** Number of elements in the table */
    int size;

    /** Modification counter */
    int modCtr;

    /**
     * Constructor
     * @param p  Length of the internal hash table array to be created
     */
    public OpenHashTable(int p) {
        //noinspection unchecked
        a = new Object[p];
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
        final double idx = Math.floorMod(element.hashCode(),a.length);
        double newidxdouble = idx;
        int newidx = (int)newidxdouble;
        double j = 0;
        while (j < a.length){
            if (a[newidx].equals(null)){
                a[newidx] = element;
                size++;
                modCtr++;
                return true;
            } else {
                newidxdouble = idx - Math.pow(Math.ceil(j/2.0),2)*Math.pow(-1,j);
                newidx = (int)newidxdouble;
                j++;
            }
        }
        return false;
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
        final double idx = Math.floorMod(element.hashCode(),a.length);
        double newidxdouble = idx;
        int newidx = (int)newidxdouble;
        double j = 0;
        while (j < a.length){
            if (a[newidx].equals(element)){
                a[newidx] = null;
                size--;
                modCtr++;
                return true;
            } else {
                newidxdouble = idx - Math.pow(Math.ceil(j/2.0),2)*Math.pow(-1,j);
                newidx = (int)newidxdouble;
                j++;
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
        if (this.isEmpty()){
            return false;
        } else {
            Objects.requireNonNull(element);
            double idx = Math.floorMod(element.hashCode(), a.length);
            double newidxdouble = idx;
            int newidx = (int) newidxdouble;
            double j = 0;
            while (j < a.length) {
                if (a[newidx].equals(element)) {
                    return true;
                } else {
                    newidxdouble = idx - Math.pow(Math.ceil(j / 2), 2) * Math.pow(-1, j);
                    newidx = (int) newidxdouble;
                    j++;
                }
            }
            return false;
        }
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
    boolean isEmpty() {
        return (size == 0);
    }


    void clear() {
        size = 0;
        for (int i = 0;i<a.length;i++){
            a[i] = null;
        }
    }

    public int hashCode() {
        int h = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != null) h += a[i].hashCode();
        }
        return h;
    }
}
