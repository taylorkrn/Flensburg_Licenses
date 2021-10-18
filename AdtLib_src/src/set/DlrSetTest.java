package set;

import deque.DlrDeque;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DlrSetTest {

    @SuppressWarnings("UseBulkOperation")
    @Test
    public void iteratorTest() {
        DlrSet<Integer> s = new DlrSet<>();
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
        DlrSet<String> set1 = new DlrSet<>();
        Set<String> set2 = new HashSet<>();

        assertTrue(set1.equals(set1));
        assertTrue(set1.equals(set2));

        assertFalse(set1.equals(null));
        assertFalse(set1.equals("X"));

        set1 = new DlrSet<>();
        set1.add("A");
        set1.add("B");
        set2 = new HashSet<>();
        set2.add(new String("B"));
        set2.add(new String("A"));

        assertTrue(set1.equals(set2));

        set1 = new DlrSet<>();
        set1.add("A");
        set2 = new HashSet<>();
        set2.add(new String("B"));
        set2.add(new String("A"));

        assertFalse(set1.equals(set2));

        set1 = new DlrSet<>();
        set1.add("A");
        set1.add("B");
        set2 = new HashSet<>();
        set2.add(new String("C"));

        assertFalse(set1.equals(set2));

        set1 = new DlrSet<>();
        set1.add("A");
        set1.add("B");
        set2 = new HashSet<>();
        set2.add(new String("B"));
        set2.add(new String("C"));

        assertFalse(set1.equals(set2));
    }


    @Test
    public void hashCodeTest() {
        DlrSet<String> set = new DlrSet<>();
        assertEquals(0, set.hashCode());

        set = new DlrSet<>();
        set.add(null);
        assertEquals(0, set.hashCode());

        set = new DlrSet<>();
        String s1 = "A";
        String s2 = "B";
        String s3 = "C";
        set.add(s1);
        set.add(s2);
        set.add(s3);
        int expected = s1.hashCode() + s2.hashCode() + s3.hashCode();
        assertEquals(expected, set.hashCode());
        set.add(null);
        assertEquals(expected, set.hashCode());

    }


    @Test
    public void toArrayTest() {
        Integer[] arr = {9,10,1,5,28};
        Set<Integer> expectedElems = new HashSet<>(Arrays.asList(arr));
        DlrSet<Integer> set = new DlrSet<>();
        set.addAll(Arrays.asList(arr));
        Object[] resultArr = set.toArray();

        assertEquals(5,resultArr.length);
        assertEquals(expectedElems, new HashSet<>(Arrays.asList(resultArr)));

        assertArrayEquals(new Object[0], (new DlrSet<Integer>()).toArray());


        // toArray(T[] a) with a of same size
        arr = new Integer[]{9, 10, 1, 5, 28};
        expectedElems = new HashSet<>(Arrays.asList(arr));
        Integer[] targetArr = new Integer[arr.length];

        set = new DlrSet<>();
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

        set = new DlrSet<>();
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

        set = new DlrSet<>();
        set.addAll(Arrays.asList(arr));
        resultArr = set.toArray(targetArr);

        assertNotSame(targetArr, resultArr);
        assertEquals(5,resultArr.length);
        assertEquals(expectedElems, new HashSet<>(Arrays.asList(resultArr)));
    }


    @Test
    public void addRemoveTest() {
        DlrSet<String> set = new DlrSet<>();
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
        (new DlrSet<>()).addAll(null);
    }


    @SuppressWarnings("RedundantOperationOnEmptyContainer")
    @Test(expected = NullPointerException.class)
    public void removeAllNullExceptionTest() {
        (new DlrSet<>()).removeAll(null);
    }


    @SuppressWarnings("RedundantOperationOnEmptyContainer")
    @Test(expected = NullPointerException.class)
    public void retainAllNullExceptionTest() {
        (new DlrSet<>()).retainAll(null);
    }


    @Test(expected = NullPointerException.class)
    public void containsAllNullExceptionTest() {
        (new DlrSet<>()).containsAll(null);
    }


    @SuppressWarnings({"RedundantOperationOnEmptyContainer"})
    @Test
    public void multipleAdditionRemovalTest() {

        assertTrue((new DlrSet<>()).containsAll(new HashSet<>()));
        assertFalse((new DlrSet<>()).removeAll(new HashSet<>()));
        assertFalse((new DlrSet<>()).retainAll(new HashSet<>()));
        assertFalse((new DlrSet<>()).addAll(new HashSet<>()));

        DlrSet<String> set = new DlrSet<>();
        assertTrue(set.addAll(Arrays.asList("A","B","C", null)));
        assertTrue(set.containsAll(Arrays.asList("A","B","C", null)));
        assertTrue(set.containsAll(Arrays.asList("C","B")));
        assertFalse(set.containsAll(Arrays.asList("C","B","D")));

        DlrSet<String> set1 = new DlrSet<>();
        DlrSet<String> set2 = new DlrSet<>();
        set1.addAll(Arrays.asList("A","B","C"));
        assertFalse(set1.removeAll(set2));
        assertTrue(set1.containsAll(Arrays.asList("A","B","C")));
        assertEquals(3,set1.size());

        set1 = new DlrSet<>();
        set2 = new DlrSet<>();
        set1.addAll(Arrays.asList("A","B","C"));
        set2.addAll(Arrays.asList("D","E"));
        assertFalse(set1.removeAll(set2));
        assertTrue(set1.containsAll(Arrays.asList("A","B","C")));
            assertEquals(3,set1.size());

        set1 = new DlrSet<>();
        set2 = new DlrSet<>();
        set1.addAll(Arrays.asList("A","E","B","C"));
        set2.addAll(Arrays.asList("B","A","K"));
        assertTrue(set1.removeAll(set2));
        assertTrue(set1.containsAll(Arrays.asList("C","E")));
        assertEquals(2,set1.size());

        set1 = new DlrSet<>();
        set2 = new DlrSet<>();
        set1.addAll(Arrays.asList("A","B","C"));
        set2.addAll(set1);
        assertTrue(set1.removeAll(set2));
        assertEquals(0,set1.size());

        set1 = new DlrSet<>();
        set2 = new DlrSet<>();
        set1.addAll(Arrays.asList("A","B","C"));
        set2.addAll(Arrays.asList("D","E"));
        assertTrue(set1.retainAll(set2));
        assertEquals(0,set1.size());

        set1 = new DlrSet<>();
        set2 = new DlrSet<>();
        set1.addAll(Arrays.asList("A","B","C"));
        set2.addAll(Arrays.asList("A","C","K"));
        assertTrue(set1.retainAll(set2));
        assertTrue(set1.containsAll(Arrays.asList("A","C")));
        assertEquals(2,set1.size());

        // Sick case: remove set from itself
        set1 = new DlrSet<>();
        set1.addAll(Arrays.asList("A","B","C"));
        assertTrue(set1.removeAll(set1));
        assertTrue(set1.isEmpty());
        assertEquals(0,set1.size());
    }
}
