package deque;

import org.junit.Test;
import queue.DlrQueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DlrDequeTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    public void singleAdditionRemovalTest() {
        DlrDeque<Integer> dq = new DlrDeque<>();
        assertNull(dq.peek());
        assertNull(dq.peekFirst());
        assertNull(dq.peekLast());
        assertNull(dq.pollFirst());
        assertNull(dq.pollLast());

        // addFirst
        dq.addFirst(2);
        assertEquals((Integer) 2, dq.peek());
        assertEquals((Integer) 2, dq.peekFirst());
        assertEquals((Integer) 2, dq.getFirst());
        assertEquals((Integer) 2, dq.peekLast());
        assertEquals((Integer) 2, dq.getLast());

        dq.addFirst(1);
        assertEquals((Integer) 1, dq.peek());
        assertEquals((Integer) 1, dq.peekFirst());
        assertEquals((Integer) 1, dq.getFirst());
        assertEquals((Integer) 2, dq.peekLast());
        assertEquals((Integer) 2, dq.getLast());

        // addLast
        dq.addLast(3);
        assertEquals((Integer) 1, dq.peek());
        assertEquals((Integer) 1, dq.peekFirst());
        assertEquals((Integer) 1, dq.getFirst());
        assertEquals((Integer) 3, dq.peekLast());
        assertEquals((Integer) 3, dq.getLast());

        // add
        assertTrue(dq.add(4));
        assertEquals((Integer) 1, dq.peekFirst());
        assertEquals((Integer) 4, dq.peekLast());

        // pollLast
        assertEquals((Integer) 4, dq.pollLast());
        assertEquals((Integer) 1, dq.peekFirst());
        assertEquals((Integer) 3, dq.peekLast());

        // pollFirst
        assertEquals((Integer) 1, dq.pollFirst());
        assertEquals((Integer) 2, dq.peekFirst());
        assertEquals((Integer) 3, dq.peekLast());

        // offerFirst
        assertTrue(dq.offerFirst(0));
        assertEquals((Integer) 0, dq.peekFirst());
        assertEquals((Integer) 3, dq.peekLast());

        // offerLast
        assertTrue(dq.offerLast(5));
        assertEquals((Integer) 0, dq.peekFirst());
        assertEquals((Integer) 5, dq.peekLast());

        // removeLast
        assertEquals((Integer) 5, dq.removeLast());
        assertEquals((Integer) 0, dq.peekFirst());
        assertEquals((Integer) 3, dq.peekLast());

        // removeFirst
        assertEquals((Integer) 0, dq.removeFirst());
        assertEquals((Integer) 2, dq.peekFirst());
        assertEquals((Integer) 3, dq.peekLast());

        // removeFirst
        assertEquals((Integer) 2, dq.remove());
        assertEquals((Integer) 3, dq.peekFirst());
        assertEquals((Integer) 3, dq.peekLast());

        // offer
        assertTrue(dq.offer(7));
        assertEquals((Integer) 3, dq.peekFirst());
        assertEquals((Integer) 7, dq.peekLast());

        // poll
        assertEquals((Integer) 3, dq.poll());
        assertEquals((Integer) 7, dq.peekFirst());
        assertEquals((Integer) 7, dq.peekLast());

        // push
        dq.push(9); // missing addFirst(e); in push() !
        assertEquals((Integer) 9, dq.peekFirst());
        assertEquals((Integer) 7, dq.peekLast());

        // element
        assertEquals((Integer) 9, dq.element());
        assertEquals((Integer) 9, dq.peekFirst());
        assertEquals((Integer) 7, dq.peekLast());

        // pop
        assertEquals((Integer) 9, dq.pop());
        assertEquals((Integer) 7, dq.peekFirst());
        assertEquals((Integer) 7, dq.peekLast());
    }


    @Test(expected = NoSuchElementException.class)
    public void removeEmptyExceptionTest() {
        (new DlrDeque<>()).remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeFirstEmptyExceptionTest() {
        (new DlrDeque<>()).removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeLastEmptyExceptionTest() {
        (new DlrDeque<>()).removeLast();
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NoSuchElementException.class)
    public void getFirstEmptyExceptionTest() {
        (new DlrDeque<>()).getFirst();
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NoSuchElementException.class)
    public void getLastEmptyExceptionTest() {
        (new DlrDeque<>()).getLast();
    }

    @SuppressWarnings("ConstantConditions")
    @Test(expected = NoSuchElementException.class)
    public void elementEmptyExceptionTest() {
        (new DlrDeque<>()).element();
    }

    @Test(expected = NullPointerException.class)
    public void addAllNullExceptionTest() {
        (new DlrDeque<>()).addAll(null);
    }

    @SuppressWarnings("RedundantOperationOnEmptyContainer")
    @Test(expected = NullPointerException.class)
    public void removeAllNullExceptionTest() {
        (new DlrDeque<>()).removeAll(null);
    }


    @SuppressWarnings("ConstantConditions")
    @Test
    public void containsTest() {
        DlrDeque<String> dq = new DlrDeque<>();
        assertFalse(dq.contains("X"));

        dq.add(new String("A"));
        dq.add(new String("B"));
        dq.add(new String("C"));

        assertFalse(dq.contains("X"));
        assertTrue(dq.contains("A"));
        assertTrue(dq.contains("B"));
        assertTrue(dq.contains("C"));

        assertTrue(dq.containsAll(new ArrayList<>()));
        assertTrue(dq.containsAll(Arrays.asList("A","B")));
        assertTrue(dq.containsAll(Arrays.asList("B","C","A")));
        assertFalse(dq.containsAll(Arrays.asList("B","D")));
        assertFalse(dq.containsAll(Arrays.asList("A","B","C","D")));
    }

    @Test
    public void multipleAdditionRemovalTest() {
        DlrDeque<Integer> dq = new DlrDeque<>();
        assertEquals(0, dq.size());

        // addAll
        assertTrue(dq.addAll(Arrays.asList(1,2,3,4)));
        assertEquals(4, dq.size());
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertEquals((Integer) 4, dq.poll());
        assertNull(dq.poll());
        assertEquals(0, dq.size());

        dq = new DlrDeque<>();
        assertTrue(dq.addAll(Arrays.asList(1,2,3,4)));
        assertFalse(dq.addAll(new ArrayList<Integer>()));

        // removeAll
        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,4));
        dq.removeAll(Arrays.asList(4,2));
        assertEquals(2, dq.size());
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertNull(dq.poll());
        assertEquals(0, dq.size());

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,4));
        assertFalse(dq.removeAll(new ArrayList<Integer>()));
        assertFalse(dq.removeAll(Arrays.asList(9,10)));
        assertTrue(dq.removeAll(Arrays.asList(1,2,70)));

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,4));
        dq.removeAll(new ArrayList<Integer>());
        assertEquals(4, dq.size());
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertEquals((Integer) 4, dq.poll());

        // clear
        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,4));
        dq.clear();
        assertNull(dq.poll());
        assertEquals(0, dq.size());

        // retainAll
        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,4));
        dq.retainAll(new ArrayList<Integer>());
        assertEquals(0, dq.size());

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,4));
        dq.retainAll(Arrays.asList(4,2));
        assertEquals(2, dq.size());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 4, dq.poll());

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,4));
        assertFalse(dq.retainAll(Arrays.asList(4,3,2,1,0,-1)));
        assertTrue(dq.retainAll(Arrays.asList(9,10)));
    }


    @Test
    public void removeOccurrencesTest() {
        // remove
        DlrDeque<Integer> dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,1,2,3));
        assertFalse(dq.remove(6));
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertNull(dq.poll());

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,1,2,3));
        assertTrue(dq.remove(3));
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertNull(dq.poll());

        // removeFirstOccurrence
        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,1,2,3));
        assertFalse(dq.removeFirstOccurrence(0));
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertNull(dq.poll());

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,1,2,3));
        assertTrue(dq.removeFirstOccurrence(2));
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertNull(dq.poll());

        // removeLastOccurrence
        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,1,2,3));
        assertFalse(dq.removeLastOccurrence(7));
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertNull(dq.poll());

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,1,2,3));
        assertTrue(dq.removeLastOccurrence(2));
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 2, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertEquals((Integer) 1, dq.poll());
        assertEquals((Integer) 3, dq.poll());
        assertNull(dq.poll());

    }


    @Test
    public void toArrayTest() {
        Integer[] arr = {9,10,1,5,28};
        Integer[] expectedArr = Arrays.copyOf(arr,arr.length);

        DlrDeque<Integer> dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(arr));
        assertArrayEquals(expectedArr, dq.toArray());

        arr = new Integer[0];
        expectedArr = Arrays.copyOf(arr,arr.length);

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(arr));
        assertArrayEquals(expectedArr, dq.toArray());

        // toArray(T[] a) with a of same size
        arr = new Integer[]{9, 10, 1, 5, 28};
        expectedArr = Arrays.copyOf(arr,arr.length);
        Integer[] targetArr = new Integer[arr.length];

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(arr));
        Object[] resultArr = dq.toArray(targetArr);
        assertSame(targetArr, resultArr);
        assertArrayEquals(expectedArr, resultArr);


        // toArray(T[] a) with a of smaller size
        arr = new Integer[]{9, 1, 23, 10, -1};
        expectedArr = Arrays.copyOf(arr,arr.length);
        targetArr = new Integer[arr.length - 3];

        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(arr));
        resultArr = dq.toArray(targetArr);
        assertNotSame(targetArr, resultArr);
        assertArrayEquals(expectedArr, resultArr);


        // toArray(T[] a) with a of larger size
        arr = new Integer[]{9, 1, 23, 10, 30};
        targetArr = new Integer[arr.length * 2];
        Arrays.fill(targetArr, -1);
        // the expected arr consists of arr, followed by a null entry, with any following values unchanged
        // i.e.: expectedArr = [9, 1, 23, 10, 30, null, -1, -1, -1, -1]
        expectedArr = Arrays.copyOf(targetArr,targetArr.length);
        System.arraycopy(arr, 0, expectedArr, 0, arr.length);
        expectedArr[arr.length] = null;


        dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(arr));
        resultArr = dq.toArray(targetArr);
        assertSame(targetArr, resultArr);
        assertArrayEquals(expectedArr, resultArr);
    }


    @SuppressWarnings("ConstantConditions")
    @Test(expected = NullPointerException.class)
    public void toArrayNullExceptionTest() {
        Object[] arr = null;
        (new DlrDeque<>()).toArray(arr);
    }


    @Test
    public void iteratorTest() {
        DlrDeque<Integer> dq = new DlrDeque<>();
        dq.addAll(Arrays.asList(1,2,3,4));

        Iterator<Integer> ascIter = dq.iterator();
        assertEquals((Integer) 1, ascIter.next());
        assertEquals((Integer) 2, ascIter.next());
        assertEquals((Integer) 3, ascIter.next());
        assertEquals((Integer) 4, ascIter.next());
        assertFalse(ascIter.hasNext());

        Iterator<Integer> descIter = dq.descendingIterator();
        assertEquals((Integer) 4, descIter.next());
        assertEquals((Integer) 3, descIter.next());
        assertEquals((Integer) 2, descIter.next());
        assertEquals((Integer) 1, descIter.next());
        assertFalse(descIter.hasNext());
    }
}
