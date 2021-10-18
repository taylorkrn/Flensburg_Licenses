package stack;

import java.util.Random;

import static org.junit.Assert.*;

public class DlrStackTest {

    final Random r = new Random();

    @org.junit.Test
    public void peek() {
        DlrStack<Integer> stk = new DlrStack<>();
        stk.push(42);
        // noinspection SimplifiableJUnitAssertion
        assertTrue(stk.peek().equals(42));
    }

    @org.junit.Test
    public void pushAndPop() {
        DlrStack<Integer> stk = new DlrStack<>();
        int[] a = new int[100];
        for ( int i = 0; i < 100; i++ ) {
            a[i] = r.nextInt();
            //noinspection SimplifiableJUnitAssertion
            assertTrue(a[i] == stk.push(a[i]));
        }

        for ( int i = 99; i >= 0; i-- ) {
            //noinspection SimplifiableJUnitAssertion
            assertTrue(stk.pop().equals(a[i]));
        }

        assertTrue(stk.isEmpty());

    }

    @org.junit.Test
    public void search() {
        DlrStack<Integer> stk = new DlrStack<>();
        for ( int i = 0; i < 100; i++ ) {
            stk.push(i);
        }
        assertEquals(-1,stk.search(2011));
        assertEquals(1,stk.search(99));
        assertEquals(2,stk.search(98));
        assertEquals(100,stk.search(0));
        assertEquals(50,stk.search(50));
    }
}