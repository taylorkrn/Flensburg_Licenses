package hashing;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DlrHashSetTest {

    @Test
    public void iteratorTest() {
        Set<Integer> s = new DlrHashSet<>(7);
        List<Integer> expectedElementsList = Arrays.asList(1,2,3,4,5,6);
        Set<Integer> expectedElements = new HashSet<>(expectedElementsList);
        s.addAll(expectedElements);

        Set<Integer> encounteredElements = new HashSet<>();
        for (Integer integer : s) {
            encounteredElements.add(integer);
        }
        assertEquals(expectedElements,encounteredElements);
    }

    @SuppressWarnings({"SimplifiableJUnitAssertion", "EqualsWithItself", "ConstantConditions", "EqualsBetweenInconvertibleTypes"})
    @Test
    public void equalsTest() {
        Set<String> set1 = new DlrHashSet<>(7);
        Set<String> set2 = new HashSet<>();

        assertTrue(set1.equals(set1));
        assertTrue(set1.equals(set2));

        assertFalse(set1.equals(null));
        assertFalse(set1.equals("X"));

        set1 = new DlrHashSet<>(7);
        set1.add("A");
        set1.add("B");
        set2 = new HashSet<>();
        set2.add(new String("B"));
        set2.add(new String("A"));

        assertTrue(set1.equals(set2));

        set1 = new DlrHashSet<>(7);
        set1.add("A");
        set2 = new HashSet<>();
        set2.add(new String("B"));
        set2.add(new String("A"));

        assertFalse(set1.equals(set2));

        set1 = new DlrHashSet<>(7);
        set1.add("A");
        set1.add("B");
        set2 = new HashSet<>();
        set2.add(new String("C"));

        assertFalse(set1.equals(set2));

        set1 = new DlrHashSet<>(7);
        set1.add("A");
        set1.add("B");
        set2 = new HashSet<>();
        set2.add(new String("B"));
        set2.add(new String("C"));

        assertFalse(set1.equals(set2));

        set1.clear();
        assertEquals(0,set1.size());
        assertTrue(set1.isEmpty());
    }


    @Test
    public void hashCodeTest() {
        Set<String> set = new DlrHashSet<>(7);
        assertEquals(0, set.hashCode());

        set = new DlrHashSet<>(7);
        String s1 = "A";
        String s2 = "B";
        String s3 = "C";
        set.add(s1);
        set.add(s2);
        set.add(s3);
        int expected = s1.hashCode() + s2.hashCode() + s3.hashCode();
        assertEquals(expected, set.hashCode());
    }


    @Test
    public void toArrayTest() {
        Integer[] arr = {9,10,1,5,28};
        Set<Integer> expectedElems = new HashSet<>(Arrays.asList(arr));
        Set<Integer> set = new DlrHashSet<>(7);
        set.addAll(Arrays.asList(arr));
        Object[] resultArr = set.toArray();

        assertEquals(5,resultArr.length);
        assertEquals(expectedElems, new HashSet<>(Arrays.asList(resultArr)));

        assertArrayEquals(new Object[0], (new DlrHashSet<Integer>(7)).toArray());


        // toArray(T[] a) with a of same size
        arr = new Integer[]{9, 10, 1, 5, 28};
        expectedElems = new HashSet<>(Arrays.asList(arr));
        Integer[] targetArr = new Integer[arr.length];

        set = new DlrHashSet<>(7);
        set.addAll(Arrays.asList(arr));
        resultArr = set.toArray(targetArr);

        assertSame(targetArr, resultArr);
        assertEquals(5,resultArr.length);
        assertEquals(expectedElems, new HashSet<>(Arrays.asList(resultArr)));


        // toArray(T[] a) with a of larger size
        arr = new Integer[]{9, 10, 1, 5, 28};
        expectedElems = new HashSet<>(Arrays.asList(arr));
        targetArr = new Integer[arr.length * 2];
        Arrays.fill(targetArr, -1);

        set = new DlrHashSet<>(7);
        set.addAll(Arrays.asList(arr));
        resultArr = set.toArray(targetArr);

        assertSame(targetArr, resultArr);
        assertEquals(arr.length * 2,resultArr.length);
        assertEquals(expectedElems, new HashSet<>(Arrays.asList(Arrays.copyOf(resultArr, arr.length))));
        assertNull(resultArr[arr.length]);
        for (int i = arr.length + 1; i < arr.length * 2; ++i) {
            assertEquals(-1, resultArr[i]);
        }


        // toArray(T[] a) with a of smaller size
        arr = new Integer[]{9, 10, 1, 5, 28};
        expectedElems = new HashSet<>(Arrays.asList(arr));
        targetArr = new Integer[arr.length-3];

        set = new DlrHashSet<>(7);
        set.addAll(Arrays.asList(arr));
        resultArr = set.toArray(targetArr);

        assertNotSame(targetArr, resultArr);
        assertEquals(5,resultArr.length);
        assertEquals(expectedElems, new HashSet<>(Arrays.asList(resultArr)));
    }


    @Test
    public void addRemoveTest() {
        Set<String> set = new DlrHashSet<>(7);
        assertEquals(0, set.size());

        assertTrue(set.add("A"));
        assertEquals(1, set.size());
        assertTrue(set.contains(new String("A")));

        assertFalse(set.add("A"));
        assertEquals(1, set.size());
        assertTrue(set.contains(new String("A")));

        assertTrue(set.add("B"));
        assertEquals(2, set.size());
        assertTrue(set.contains(new String("A")));
        assertTrue(set.contains(new String("B")));

        assertFalse(set.remove("C"));
        assertEquals(2, set.size());
        assertTrue(set.contains(new String("A")));
        assertTrue(set.contains(new String("B")));

        assertTrue(set.remove("A"));
        assertEquals(1, set.size());
        assertFalse(set.contains(new String("A")));
        assertTrue(set.contains(new String("B")));
    }


    @Test(expected = NullPointerException.class)
    public void addAllNullExceptionTest() {
        (new DlrHashSet<>(7)).addAll(null);
    }

    @SuppressWarnings("RedundantOperationOnEmptyContainer")
    @Test(expected = NullPointerException.class)
    public void removeAllNullExceptionTest() {
        (new DlrHashSet<>(7)).removeAll(null);
    }


    @SuppressWarnings("RedundantOperationOnEmptyContainer")
    @Test(expected = NullPointerException.class)
    public void retainAllNullExceptionTest() {
        (new DlrHashSet<>(7)).retainAll(null);
    }


    @Test(expected = NullPointerException.class)
    public void containsAllNullExceptionTest() {
        (new DlrHashSet<>(7)).containsAll(null);
    }


    @SuppressWarnings({"RedundantOperationOnEmptyContainer"})
    @Test
    public void multipleAdditionRemovalTest() {

        assertTrue((new DlrHashSet<>(7)).containsAll(new HashSet<>()));
        assertFalse((new DlrHashSet<>(7)).removeAll(new HashSet<>()));
        assertFalse((new DlrHashSet<>(7)).retainAll(new HashSet<>()));
        assertFalse((new DlrHashSet<>(7)).addAll(new HashSet<>()));

        Set<String> set = new DlrHashSet<>(7);
        assertTrue(set.addAll(Arrays.asList("A","B","C", "D")));
        assertTrue(set.containsAll(Arrays.asList("A","B","C", "D")));
        assertTrue(set.containsAll(Arrays.asList("C","B")));
        assertTrue(set.containsAll(Arrays.asList("C","B","D")));

        Set<String> set1 = new DlrHashSet<>(7);
        Set<String> set2 = new DlrHashSet<>(7);
        set1.addAll(Arrays.asList("A","B","C"));
        assertFalse(set1.removeAll(set2));
        assertTrue(set1.containsAll(Arrays.asList("A","B","C")));
        assertEquals(3,set1.size());

        set1 = new DlrHashSet<>(7);
        set2 = new DlrHashSet<>(7);
        set1.addAll(Arrays.asList("A","B","C"));
        set2.addAll(Arrays.asList("D","E"));
        assertFalse(set1.removeAll(set2));
        assertTrue(set1.containsAll(Arrays.asList("A","B","C")));
        assertEquals(3,set1.size());

        set1 = new DlrHashSet<>(7);
        set2 = new DlrHashSet<>(7);
        set1.addAll(Arrays.asList("A","E","B","C"));
        set2.addAll(Arrays.asList("B","A","K"));
        assertTrue(set1.removeAll(set2));
        assertTrue(set1.containsAll(Arrays.asList("C","E")));
        assertEquals(2,set1.size());

        set1 = new DlrHashSet<>(7);
        set2 = new DlrHashSet<>(7);
        set1.addAll(Arrays.asList("A","B","C"));
        set2.addAll(set1);
        assertTrue(set1.removeAll(set2));
        assertEquals(0,set1.size());

        set1 = new DlrHashSet<>(7);
        set2 = new DlrHashSet<>(7);
        set1.addAll(Arrays.asList("A","B","C"));
        set2.addAll(Arrays.asList("D","E"));
        assertTrue(set1.retainAll(set2));
        assertEquals(0,set1.size());

        set1 = new DlrHashSet<>(7);
        set2 = new DlrHashSet<>(7);
        set1.addAll(Arrays.asList("A","B","C"));
        set2.addAll(Arrays.asList("A","C","K"));
        assertTrue(set1.retainAll(set2));
        assertTrue(set1.containsAll(Arrays.asList("A","C")));
        assertEquals(2,set1.size());

        // Sick case: remove set from itself
        set1 = new DlrHashSet<>(7);
        set1.addAll(Arrays.asList("A","B","C"));
        assertTrue(set1.removeAll(set1));
        assertTrue(set1.isEmpty());
        assertEquals(0,set1.size());
    }


    @Test
    public void testForEachRemaining() {

        Set<String> set = new DlrHashSet<>(7);
        assertTrue(set.addAll(Arrays.asList("A","B","C", "D")));

        Set<String> set2 = new HashSet<>();
        Iterator<String> ite = set.iterator();
        set2.add(ite.next());

        ite.forEachRemaining(e -> set2.add(e));

        assertTrue(set.equals(set2));
        assertTrue(set2.equals(set));

    }

    @Test
    public void largeSetsTest() {

        Random r = new Random();
        Set<Integer> set = new DlrHashSet<>(102197);

        for ( int i = 0; i < 300000; i++ ) {
            assertTrue( set.add(i) );
        }
        assertEquals(300000,set.size());
        for ( int i = 0; i < 300000; i++ ) {
            assertFalse( set.add(i) );
        }
        for ( int i = 0; i < 300000; i++ ) {
            assertTrue( set.contains(i) );
        }

        set.clear();
        assertEquals(0,set.size());
        assertTrue(set.isEmpty());

        int expectedSize = 0;
        int numRejected = 0;
        int[] rejectedNumbers = new int[300000];
        for ( int i = 0; i < 300000; i++ ) {
            int theNumber = r.nextInt();
            if ( set.add(i) ) {
                expectedSize++;
            }
            else {
                rejectedNumbers[numRejected++] = theNumber;
            }
        }
        assertEquals(expectedSize,set.size());
        for ( int i = 0; i < numRejected; i++ ) {
            assertTrue( set.contains(rejectedNumbers[i]) );
        }

        for ( int i = 0; i < numRejected; i++ ) {
            assertTrue( set.remove(rejectedNumbers[i]) );
        }

        for ( int i = 0; i < numRejected; i++ ) {
            assertFalse( set.contains(rejectedNumbers[i]) );
        }

        assertEquals(expectedSize-numRejected,set.size());


    }

}