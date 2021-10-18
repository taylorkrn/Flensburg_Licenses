package bprqueue;

import list.DlrList;

import java.util.List;
import java.util.NoSuchElementException;

public class DlrBoundedPriorityQueue<E> implements BoundedPriorityQueue<E>  {


    int size;
    final List<E>[] prioArray;
    long priorityBitList;


    private void checkPriority(int priority) {
        if ( priority < BoundedPriorityQueue.HIGHEST_PRIORITY ||
                priority > BoundedPriorityQueue.LOWEST_PRIORITY ) {
            throw new IllegalArgumentException();
        }
    }

    public DlrBoundedPriorityQueue() {
        //noinspection unchecked
        prioArray = (DlrList<E>[])new DlrList[BoundedPriorityQueue.LOWEST_PRIORITY+1];
        for ( int p = 0; p < prioArray.length; p++ ) {
            prioArray[p] = new DlrList<>();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (priorityBitList == 0);
    }

    @Override
    public boolean add(E e, int priority) {
        checkPriority(priority);
        prioArray[priority].add(e);
        size++;
        priorityBitList |= (1L << priority);
        return true;
    }

    @Override
    public boolean offer(E e, int priority) {
        return add(e,priority);
    }

    @Override
    public E peek() {
        int p = Long.numberOfTrailingZeros(priorityBitList);
        if ( p == 64 ) return null;
        return prioArray[p].get(0);
    }

    @Override
    public E element() {
        if ( isEmpty() ) throw new NoSuchElementException();
        return peek();
    }

    @Override
    public E poll() {
        int p = Long.numberOfTrailingZeros(priorityBitList);
        if ( p == 64 ) return null;
        E element = prioArray[p].remove(0);
        size--;
        if ( prioArray[p].isEmpty() ) priorityBitList &= ~(1L << p);
        return element;
    }

    @Override
    public E remove() {
        if ( isEmpty() ) throw new NoSuchElementException();
        return poll();
    }
}
