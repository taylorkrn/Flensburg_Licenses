package stack;

import dlr.Dlr;

import java.util.EmptyStackException;

public class DlrStack<E> extends Dlr<E> {

    public DlrStack() {
        super();
    }

    public E peek() {
        if ( isEmpty() ) throw new EmptyStackException();
        return listHead.getPrev().getData();
    }

    public E pop() {
        if ( isEmpty() ) throw new EmptyStackException();
        E element = listHead.getPrev().getData();
        unlink(listHead.getPrev());
        return element;
    }

    public E push(E item) {
        append(item);
        return item;
    }

    public int search(Object o) {
        int idx = dlrLastIndexOf(o);
        return (idx == -1) ? idx : (size() - idx);
    }

}
