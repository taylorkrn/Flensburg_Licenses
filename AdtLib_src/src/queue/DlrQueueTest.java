package queue;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DlrQueueTest {

    @Test
    public void contains() {
        DlrQueue<Integer> queue = new DlrQueue<>();
        queue.add(42);
        assertTrue(queue.contains(42));
        assertFalse(queue.contains(41));
    }

    @Test
    public void iterator() {
        DlrQueue<Integer> queue = new DlrQueue<>();
        //noinspection RedundantOperationOnEmptyContainer
        Iterator<Integer> ite = queue.iterator();

        assertFalse(ite.hasNext());

        for(int i = 0; i < 10; i++){
            queue.add(i);
        }

        ite = queue.iterator();

        while (ite.hasNext()) {
            if (ite.next() % 2 == 1) {
                ite.remove();
            }
        }
        assertEquals(5, queue.size());
    }

    @Test
    public void toArray() {
        Integer[] a = {0,1,2,3,4,5,6,7,8};
        DlrQueue<Integer> queue = new DlrQueue<>();
        for(int i = 0; i < 9; i++){
            queue.add(i);
        }
        assertArrayEquals(a, queue.toArray());

        Integer[] a2 = new Integer[12];
        a2[11] = 11;
        Integer[] a3 = {0,1,2,3,4,5,6,7,8,9,null,11};
        Integer[] a4 = {0,1,2,3,4,5,6,7,8,9};
        DlrQueue<Object> queueObj = new DlrQueue<>();
        for(int i = 0; i < 10; i++){
            queueObj.add(i);
        }

        //array is larger than queue
        //noinspection SuspiciousToArrayCall
        assertArrayEquals(a3, queueObj.toArray(a2));

        //array is smaller than queue
        //noinspection SuspiciousToArrayCall
        assertArrayEquals(a4, queueObj.toArray(a));

        //array has the same size as queue
        //noinspection SuspiciousToArrayCall
        assertSame(a4, queueObj.toArray(a4));
    }

    @Test
    public void addAndRemove() {
        DlrQueue<Integer> queue = new DlrQueue<>();

        //add elements to queue
        for(int i = 0; i < 100; i++){
            assertTrue(queue.add(i));
        }
        assertEquals(100, queue.size());
        assertFalse(queue.isEmpty());

        //try to remove nonexistent element from non-empty queue
        assertFalse(queue.remove(200));

        //remove all elements in queue
        for(int i = 99; i >= 0; i--){
            assertTrue(queue.remove(i));
        }
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());

        //try to remove nonexistent element from empty queue
        try {
            queue.remove(42);
            fail(); // No exception thrown
        }
        catch (NoSuchElementException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
    }

    @Test
    public void containsAll() {
        DlrQueue<Integer> queue = new DlrQueue<>();
        DlrQueue<Integer> queue2 = new DlrQueue<>();
        for(int i = 0; i < 100; i++){
            queue.add(i);
            queue2.add(i);
        }
        assertTrue(queue.containsAll(queue2));
        queue2.add(100);
        assertFalse(queue.containsAll(queue2));

        try {
            queue.containsAll(null);
            fail(); // No exception thrown
        }
        catch (NullPointerException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
    }

    @Test
    public void addAll() {
        DlrQueue<Integer> queue = new DlrQueue<>();
        DlrQueue<Integer> queue2 = new DlrQueue<>();

        for(int i = 0; i < 100; i++){
            queue2.add(i);
        }

        assertTrue(queue.addAll(queue2));
        assertTrue(queue.containsAll(queue2));
        assertFalse(queue.addAll(new DlrQueue<>()));

        try {
            queue.addAll(null);
            fail(); // No exception thrown
        }
        catch (NullPointerException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
    }

    @Test
    public void removeAll() {
        DlrQueue<Integer> queue = new DlrQueue<>();
        DlrQueue<Integer> queue2 = new DlrQueue<>();
        DlrQueue<Integer> queue3 = new DlrQueue<>();

        for(int i = 0; i < 100; i++){
            queue.add(i);
            queue2.add(i);
            queue3.add(i);
        }

        queue2.add(100);

        assertTrue(queue.removeAll(queue2));
        assertTrue(queue.isEmpty());

        assertTrue(queue2.removeAll(queue3));
        assertEquals(1, queue2.size());
        assertFalse(queue2.removeAll(queue3));

        try {
            queue.removeAll(null);
            fail(); // No exception thrown
        }
        catch (NullPointerException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
    }

    @Test
    public void retainAll() {
        DlrQueue<Integer> queue = new DlrQueue<>();
        DlrQueue<Integer> queue2 = new DlrQueue<>();
        DlrQueue<Integer> queue3 = new DlrQueue<>();

        for(int i = 0; i < 50; i++){
            queue.add(i);
            queue2.add(i);
            queue3.add(i);
        }
        for(int i = 50; i < 100; i++){
            queue2.add(i);
            queue3.add(i);
        }

        //specified collection contains the same elements and more
        assertFalse(queue.retainAll(queue2));
        assertEquals(50, queue.size());

        //specified collection contains only the same elements
        assertFalse(queue2.retainAll(queue3));
        assertEquals(100, queue2.size());

        //specified collection contains less elements
        assertTrue(queue2.retainAll(queue));
        assertEquals(50, queue2.size());

        try {
            queue.retainAll(null);
            fail(); // No exception thrown
        }
        catch (NullPointerException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
    }

    @Test
    public void offer() {
        DlrQueue<Integer> queue = new DlrQueue<>();

        for(int i = 0; i < 100; i++){
            assertTrue(queue.offer(i));
        }
        assertEquals(100, queue.size());
        assertFalse(queue.isEmpty());
    }

    @Test
    public void remove() {
        DlrQueue<Integer> queue = new DlrQueue<>();

        queue.offer(42);
        assertEquals(42, (int) queue.remove());

        try {
            queue.remove();
            fail(); // No exception thrown
        }
        catch (NoSuchElementException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
    }

    @Test
    public void poll() {
        DlrQueue<Integer> queue = new DlrQueue<>();

        queue.offer(42);
        assertEquals(Integer.valueOf(42), queue.poll());
        assertNull(queue.poll());
    }

    @Test
    public void element() {
        DlrQueue<Integer> queue = new DlrQueue<>();

        try {
            //noinspection ResultOfMethodCallIgnored
            queue.element();
            fail(); // No exception thrown
        }
        catch (NoSuchElementException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }

        queue.offer(42);
        assertEquals(42, (int) queue.element());
    }

    @Test
    public void peek() {
        DlrQueue<Integer> queue = new DlrQueue<>();

        //noinspection ConstantConditions
        assertNull(queue.peek());
        queue.offer(42);
        assertEquals(Integer.valueOf(42), queue.peek());
    }
}
