package dlr;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DlrTest {

    int auxSum;

    static <E> void print(Dlr<E> d) {
        for ( Dlr<E>.Node n = d.listHead.next; n != d.listHead; n = n.next ) {
            System.out.println(n.getData().toString());
        }
    }


    @Test
    public void unlink() {
        Dlr<Integer> l1 = new Dlr<>();

        // Boundary test: only one element
        l1.append(100);
        assertEquals(1, l1.size());
        Dlr<Integer>.Node n = l1.getNode(0);
        l1.unlink(n);
        assertTrue(l1.isEmpty());

        // add 10 elements
        for ( int i = 0; i< 10; i++ ) {
            l1.append(i);
        }

        // Boundary test: unlink last element
        l1.unlink(l1.listHead.prev);
        assertEquals(9,l1.size());
        for ( int i = 0; i< 9; i++ ) {
            assertEquals(i, (int) l1.getNode(i).getData());
        }

        // Boundary test: unlink first element
        l1.unlink(l1.listHead.next);
        assertEquals(8,l1.size());
        for ( int i = 0; i< 8; i++ ) {
            assertEquals((i + 1), (int) l1.getNode(i).getData());
        }

        // unlink a node inside the list
        l1.clear();
        assertTrue(l1.isEmpty() && l1.size() == 0);
        // add 10 elements
        for ( int i = 0; i< 10; i++ ) {
            l1.append(i);
        }
        l1.unlink(l1.getNode(5));
        assertEquals(9,l1.size());
        for ( int i = 0; i< 4; i++ ) {
            assertEquals(i, (int) l1.getNode(i).getData());
        }
        for ( int i = 5; i< 9; i++ ) {
            assertEquals((i + 1), (int) l1.getNode(i).getData());
        }


    }

    @Test
    public void insertBefore() {
        Dlr<Integer> l1 = new Dlr<>();
        Dlr<Integer>.Node n = l1.new Node(9);
        l1.insertBefore(l1.listHead,n);
        assertEquals(9, (int) l1.first());
        assertEquals(1,l1.size());

        n = l1.new Node(0);
        l1.insertBefore(l1.listHead.next,n);
        assertEquals(0, (int) l1.first());
        assertEquals(2,l1.size());
        assertEquals(9, (int) l1.getNode(1).getData());

        for ( int i = 1; i< 9; i++ ) {
            l1.insertBefore(l1.getNode(i),l1.new Node(i));
        }
        for ( int i = 0; i < 10; i++ ) {
            assertEquals(i, (int) l1.getNode(i).getData());
        }
    }

    @Test
    public void findFirstLast() {
        Dlr<Integer> l1 = new Dlr<>();
        for ( int i = 0; i< 10; i++ ) {
            l1.append(i);
        }
        l1.append(0);
        l1.append(1);
        assertNull(l1.find(200));
        assertEquals(-1,l1.dlrIndexOf(200));
        assertEquals(-1,l1.dlrLastIndexOf(200));

        for ( int i = 0; i < 10; i++ ) {
            assertSame(l1.find(i), l1.getNode(i));
        }
        assertSame(l1.findLast(1), l1.getNode(11));
        assertEquals(11, l1.dlrLastIndexOf(1));
        assertSame(l1.findLast(0), l1.getNode(10));
        assertEquals(10, l1.dlrLastIndexOf(0));
        for ( int i = 2; i < 10; i++ ) {
            assertSame(l1.findLast(i), l1.getNode(i));
            assertSame(l1.findLast(i), l1.find(i));
            assertEquals(l1.dlrIndexOf(i), l1.dlrLastIndexOf(i));
        }

        assertNull(l1.findLast(200));
    }

    @Test
    public void checkPositionIndex() {
        Dlr<Integer> l1 = new Dlr<>();
        for ( int i = 0; i< 10; i++ ) {
            l1.append(i);
        }

        l1.checkPositionIndex(0);
        assertTrue(true);// Will fail if exception is thrown
        l1.checkPositionIndex(10);
        assertTrue(true);// Will fail if exception is thrown

        try {
            l1.checkPositionIndex(-1);
            fail(); // No exception thrown
        }
        catch ( IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }

        try {
            l1.checkPositionIndex(11);
            fail(); // No exception thrown
        }
        catch ( IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }

    }

    @Test
    public void checkNodeIndex() {
        Dlr<Integer> l1 = new Dlr<>();
        for ( int i = 0; i< 10; i++ ) {
            l1.append(i);
        }

        l1.checkNodeIndex(0);
        assertTrue(true);// Will fail if exception is thrown
        l1.checkNodeIndex(9);
        assertTrue(true);// Will fail if exception is thrown

        try {
            l1.checkNodeIndex(-1);
            fail(); // No exception thrown
        }
        catch ( IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }

        try {
            l1.checkNodeIndex(10);
            fail(); // No exception thrown
        }
        catch ( IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
    }

    @Test
    public void appendGetNode() {
        Dlr<Integer> l1 = new Dlr<>();
        assertEquals(l1.listHead,l1.getNode(0));
        for ( int i = 0; i< 10; i++ ) {
            assertTrue(l1.append(i));
        }
        assertEquals(10,l1.size());
        for ( int i = 1; i< 10; i++ ) {
           assertEquals(i,l1.getNode(i).getData().intValue());
        }
        assertEquals(l1.listHead,l1.getNode(l1.size()));

        try {
            //noinspection ResultOfMethodCallIgnored
            l1.getNode(-1).getData();
            fail(); // No exception thrown
        }
        catch ( IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }

        try {
            //noinspection ResultOfMethodCallIgnored
            l1.getNode(l1.size()+1).getData();
            fail(); // No exception thrown
        }
        catch ( IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }

    }

    @Test
    public void insertAtIndexGetByIndex() {
        Dlr<Integer> l1 = new Dlr<>();
        l1.insertAtIndex(0,9);
        assertEquals(9, (int) l1.first());
        assertEquals(1,l1.size());

        l1.insertAtIndex(0,0);
        assertEquals(0, (int) l1.first());
        assertEquals(2,l1.size());
        assertEquals(9, (int) l1.getByIndex(1));

        for ( int i = 1; i< 9; i++ ) {
            l1.insertAtIndex(i,i);
        }
        for ( int i = 0; i < 10; i++ ) {
            assertEquals(i, (int) l1.getByIndex(i));
        }
    }


    @Test
    public void setByIndex() {
        Dlr<Integer> l1 = new Dlr<>();

        try {
            l1.setByIndex(0,17);
            fail(); // No exception thrown
        }
        catch ( IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }


        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        for ( int i = 0; i < 10; i++ ) {
            Integer aux = l1.setByIndex(i, l1.getByIndex(i)+100);
            assertEquals((int) aux, i);
        }
        for ( int i = 0; i < 10; i++ ) {
            Integer aux = l1.getByIndex(i);
            assertEquals((int) aux, i + 100);
        }

    }

    @Test
    public void removeByIndex() {
        Dlr<Integer> l1 = new Dlr<>();

        try {
            l1.removeByIndex(0);
            fail(); // No exception thrown
        }
        catch ( IndexOutOfBoundsException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }


        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        assertEquals(0, (int) l1.removeByIndex(0));
        for ( int i = 0; i < 9; i++ ) {
            assertEquals((i + 1), (int) l1.getByIndex(i));
        }
        assertEquals(9, (int) l1.removeByIndex(l1.size() - 1));
        for ( int i = 0; i < 8; i++ ) {
            assertEquals((i + 1), (int) l1.getByIndex(i));
        }
        assertEquals(8,l1.size());
        assertEquals(5, (int) l1.removeByIndex(4));
        for ( int i = 0; i < 4; i++ ) {
            assertEquals((i + 1), (int) l1.getByIndex(i));
        }
        for ( int i = 4; i < 7; i++ ) {
            assertEquals((i + 2), (int) l1.getByIndex(i));
        }
        assertEquals(7,l1.size());

    }


    @Test
    public void first() {
        Dlr<Integer> l1 = new Dlr<>();
        try {
            l1.first();
            fail(); // No exception thrown
        }
        catch ( NoSuchElementException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
        for ( int i = 0; i< 10; i++ ) {
            l1.append(i);
        }
        assertEquals(0, (int) l1.first());
    }

    @Test
    public void size() {
        Dlr<Integer> l1 = new Dlr<>();
        assertEquals(0,l1.size());
        assertTrue(l1.isEmpty());
        for ( int i = 0; i< 10; i++ ) {
            l1.append(i);
        }
        assertEquals(10,l1.size());
        assertFalse(l1.isEmpty());
    }

    @Test
    public void checkListInvariants() {
        Dlr<Integer> l1 = new Dlr<>();
        assertSame(l1.listHead, l1.listHead.getNext());
        assertSame(l1.listHead, l1.listHead.getPrev());
        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        for ( int i = 0; i < 10; i++ ) {
            assertSame(l1.getNode(i).getNext(), l1.getNode(i + 1));
            assertSame(l1.getNode(i + 1).getPrev(), l1.getNode(i));
        }
    }

    @Test
    public void setData() {
        Dlr<Integer> l1 = new Dlr<>();
        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        for ( int i = 0; i < 10; i++ ) {
            l1.getNode(i).setData(l1.getNode(i).getData()+100);
        }
        for ( int i = 0; i < 10; i++ ) {
            assertEquals((i + 100), (int) l1.getByIndex(i));
        }
    }

    @Test
    public void checkIterator() {
        Dlr<Integer> l1 = new Dlr<>();
        Iterator<Integer> ite = l1.new DlrIterator();
        Iterator<Integer> rIte = l1.new DlrReverseIterator();
        assertFalse(ite.hasNext());
        assertFalse(rIte.hasNext());

        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        ite = l1.new DlrIterator();
        rIte = l1.new DlrReverseIterator();
        for ( int i = 0; i < 10; i++ ) {
            assertEquals(i, (int) ite.next());
            assertEquals(9 - i, (int) rIte.next());
        }
        ite = l1.new DlrIterator();
        while ( ite.hasNext() ) {
            if ( ite.next() % 2 == 1 ) {
                ite.remove();
            }
        }
        assertEquals(5,l1.size());
        ite = l1.new DlrIterator();
        rIte = l1.new DlrReverseIterator();
        for ( int i = 0; i < 5; i += 2) {
            assertEquals(i, (int) ite.next());
            assertEquals(8 - i, (int) rIte.next());
        }

        l1.clear();
        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        rIte = l1.new DlrReverseIterator();
        while ( rIte.hasNext() ) {
            if ( rIte.next() % 2 == 1 ) {
                rIte.remove();
            }
        }
        ite = l1.new DlrIterator();
        rIte = l1.new DlrReverseIterator();
        for ( int i = 0; i < 5; i += 2) {
            assertEquals(i, (int) ite.next());
            assertEquals(8 - i, (int) rIte.next());
        }

        l1.clear();
        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        ite = l1.new DlrIterator();
        auxSum = 0;
        for ( int i = 0; i < 5; i++ ) {
            assertEquals(i, (int) ite.next());
        }
        ite.forEachRemaining(e -> auxSum += e);
        assertEquals(35,auxSum);

        l1.clear();
        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        rIte = l1.new DlrReverseIterator();
        auxSum = 0;
        for ( int i = 9; i >= 5; i-- ) {
            assertEquals(i, (int) rIte.next());
        }
        rIte.forEachRemaining(e -> auxSum += e);
        assertEquals(10,auxSum);

    }

    @Test
    public void comodificationTests() {
        Dlr<Integer> l1 = new Dlr<>();
        Iterator<Integer> ite = l1.new DlrIterator();
        Iterator<Integer> rite = l1.new DlrReverseIterator();

        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        try {
            ite.next();
            fail(); // No exception thrown
        }
        catch ( ConcurrentModificationException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
        try {
            rite.next();
            fail(); // No exception thrown
        }
        catch ( ConcurrentModificationException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }

        ite = l1.new DlrIterator();
        rite = l1.new DlrReverseIterator();
        l1.unlink(l1.listHead.next);
        l1.insertBefore(l1.listHead,l1.new Node(15));
        try {
            ite.next();
            fail(); // No exception thrown
        }
        catch ( ConcurrentModificationException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }
        try {
            rite.next();
            fail(); // No exception thrown
        }
        catch ( ConcurrentModificationException e) {
            assertTrue(true); // Expected exception came
        }
        catch (Exception e) {
            fail(); // did not get the expected exception
        }

    }

    @Test
    public void testHashCode() {
        Dlr<Integer> l1 = new Dlr<>();
        for ( int i = 0; i < 10; i++ ) {
            l1.append(i);
        }
        Set<Integer> s = new HashSet<Integer>();
        for ( int i = 0; i < 10; i++ ) {
            s.add(l1.getByIndex(i));
        }
        assertEquals(l1.hashCode(),s.hashCode());
    }
}