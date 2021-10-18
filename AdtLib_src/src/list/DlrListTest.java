package list;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class DlrListTest {

    private DlrList<Integer> integerListOne, integerListTwo, counting, repeating;
    private Iterator<Integer> iteratorOne, iteratorTwo;
    private ListIterator<Integer> iteratorThree;
    private Integer[] empty;
    private final Integer[] TEST1 = {0, 1, 2, 3, 4, 5};
    private final Integer[] TEST2 = {2, 0, 1, 1, 1, 2};
    private final Integer[] TEST3 = {0, 8, 1, 5};
    private final Integer[] TEST4 = {0, -1, 2, 1, 4, 3, 6, 5, 8, 7};


    /**
     * BeforeEach failed,
     * as such we call this method explicitly before each test case.
     * <p>
     * Initializes the test attributes.
     *
     * @author Adem Balcioglu + Mischael Lameter
     */
    private void setup() {
        integerListOne = new DlrList<>();
        integerListOne.addAll(Arrays.asList(TEST3));
        iteratorOne = integerListOne.iterator();

        integerListTwo = new DlrList<>();
        integerListTwo.addAll(Arrays.asList(TEST4));
        iteratorTwo = integerListTwo.iterator();
        iteratorThree = integerListTwo.listIterator();

        counting = new DlrList<>();
        counting.addAll(Arrays.asList(TEST1));

        repeating = new DlrList<>();
        repeating.addAll(Arrays.asList(TEST2));
    }

    /**
     * Test for contains
     *
     * @author Adem Balcioglu
     */
    @Test
    public void contains() {
        setup();

        //Basic Tests
        assertTrue("Contains Function failed!", integerListOne.contains(0));
        assertTrue("Contains Function failed!", integerListOne.contains(8));
        assertTrue("Contains Function failed!", integerListOne.contains(1));
        assertTrue("Contains Function failed!", integerListOne.contains(5));
        assertFalse("Contains Function failed!", integerListOne.contains(15));
        assertFalse("Contains Function failed!", integerListOne.contains(81));
        assertTrue("Contains Function failed!", integerListTwo.contains(-1));
        assertFalse("Contains Function failed!", integerListTwo.contains(-2));

        //Tests with null
        assertFalse("Contains Function failed!", integerListTwo.contains(null));
        integerListTwo.add(null);
        assertTrue("Contains Function failed!", integerListTwo.contains(null));
    }

    /**
     * Test for iterator
     *
     * @author Adem Balcioglu
     */
    @Test
    public void iterator() {
        setup();

        assertTrue("HasNext Method Function failed!", iteratorOne.hasNext());

        while (iteratorOne.hasNext()) {
            if (iteratorOne.next() == 0) {
                iteratorOne.remove();
            }
        }

        assertEquals("Wrong result! The amount of numbers left are not as expected!", 3, integerListOne.size());
        assertFalse("Iterator has next after running throw!", iteratorOne.hasNext());

        assertTrue("HasNext Method Function failed!", iteratorTwo.hasNext());

        while (iteratorTwo.hasNext()) {
            if (iteratorTwo.next() > 0) {
                iteratorTwo.remove();
            }
        }

        assertEquals("Wrong result! The amount of numbers left are not as expected!", 2, integerListTwo.size());
        assertFalse("Iterator has next after running throw!", iteratorTwo.hasNext());
    }

    /**
     * Test for toArray
     *
     * @author Adem Balcioglu
     */
    @Test
    public void toArray() {
        setup();
        Integer[] values = {0, 8, 1, 5};
        Integer[] valuesTwo = {0, -1, 2, 1, 4, 3, 6, 5, 8, 7};
        assertArrayEquals("ToArray Function failed!", values, integerListOne.toArray());
        assertArrayEquals("ToArray Function failed!", valuesTwo, integerListTwo.toArray());
    }

    /**
     * Test for testToArray
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testToArray() {
        setup();

        try {
            integerListOne.toArray(empty);
            fail();
        } catch (NullPointerException npe) {
            //System.err.println("Null element not Allowed!");
        }

        Integer[] values = {0, 8, 1, 5, null, 5, 6, 7, 8, 9};
        assertArrayEquals("Failed to build in an Array with larger size!", values, integerListOne.toArray(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));

        assertArrayEquals("Failed to build in an Array with same size!", integerListTwo.toArray(), integerListTwo.toArray(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}));

        assertArrayEquals("Failed to build in an Array with smaller size!", integerListTwo.toArray(), integerListTwo.toArray(integerListOne.toArray()));
    }

    /**
     * Test for add
     *
     * @author Adem Balcioglu
     */
    @Test
    public void add() {
        setup();

        assertTrue(integerListOne.add(1231));
        assertTrue("Add Function failed!", integerListOne.contains(1231));

        assertTrue(integerListTwo.add(-1));
        assertTrue("Add Function failed with negative number!", integerListTwo.contains(-1));
        int negativeOneCount = 0;
        for (Integer i : integerListTwo) {
            if (i == -1) negativeOneCount++;
        }

        assertEquals("Wrong amount of elements after adding", 2, negativeOneCount);
    }

    /**
     * Test for remove
     *
     * @author Adem Balcioglu
     */
    @Test
    public void remove() {
        setup();

        assertTrue("Remove Function failed!", integerListOne.remove(Integer.valueOf(0)));
        assertEquals("Wrong amount of elements left after removing!", 3, integerListOne.size());

        assertEquals("Wrong result! Remove Function failed!", 8, (int) integerListOne.get(0));

        assertTrue("Remove Function failed!", integerListTwo.remove(Integer.valueOf(0)));
        assertFalse("Removing was successful even if the elements was not in the List!", integerListTwo.remove(Integer.valueOf(0)));
        assertEquals("Wrong amount of elements left after removing!", 9, integerListTwo.size());

        assertEquals("Wrong result! Remove Function failed!", -1, (int) integerListTwo.get(0));

    }

    /**
     * Test for containsAll
     *
     * @author Adem Balcioglu
     */
    @Test
    public void containsAll() {
        setup();
        assertTrue("Wrong Result, List does contain the specified elements!", integerListOne.containsAll(Arrays.asList(0, 8, 1, 5)));
        assertTrue("Wrong Result, List does contain the specified elements!", integerListOne.containsAll(Arrays.asList(1, 0, 8, 1, 5)));
        assertFalse("Wrong Result, List does not contain the specified elements!", integerListOne.containsAll(Arrays.asList(-1, 0, 8, 1, 5)));

        assertTrue("Wrong Result, List does contain the specified elements!", integerListTwo.containsAll(integerListOne));
        assertTrue("Wrong Result, List does contain the specified elements!", integerListTwo.containsAll(Arrays.asList(1, 0, 8, 1, 5)));
        assertFalse("Wrong Result, List does not contain the specified elements!", integerListTwo.containsAll(Arrays.asList(-2, -1, 0)));
    }

    /**
     * Test for addAll
     *
     * @author Adem Balcioglu
     */
    @Test
    public void addAll() {
        setup();
        assertTrue("Add Function failed!", integerListOne.addAll(integerListTwo));
        assertTrue("Wrong result! Add Function failed!", integerListTwo.containsAll(integerListOne));

        //Parameter must not be null!
        try {
            integerListOne.addAll(Arrays.asList(empty));
            fail();
        } catch (NullPointerException npe) {
            //System.err.println("Null element not allowed!");
        }

        //Parameter must not be null!
        try {
            integerListTwo.addAll(Arrays.asList(empty));
            fail();
        } catch (NullPointerException npe) {
            //System.err.println("Null element not allowed!");
        }

        // addAll with empty collection: expect return value false,
        // expect list unchanged
        List<Integer> backup = new LinkedList<>(integerListOne);
        assertFalse(integerListOne.addAll(Collections.emptyList()));
        assertEquals(integerListOne,backup);

    }

    /**
     * Test for addAll with additional index parameter
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testAddAll() {
        setup();
        //Reference of elements in integerListOne
        Integer[] reference = integerListOne.toArray(new Integer[]{});
        assertTrue("Add Function with additional index parameter failed!", integerListOne.addAll(integerListOne.size(), integerListTwo));
        assertTrue("Add Function with additional index parameter failed!", integerListTwo.addAll(0, Arrays.asList(reference)));
        assertArrayEquals("Wrong result, AddAll Function with additional index parameter failed!", integerListOne.toArray(), integerListTwo.toArray());

        //Parameter must not be null!
        try {
            integerListOne.addAll(0, Arrays.asList(empty));
            fail();
        } catch (NullPointerException npe) {
            //System.err.println("Null element not allowed!");
        }

        try {
            integerListTwo.addAll(0, Arrays.asList(empty));
            fail();
        } catch (NullPointerException npe) {
            //System.err.println("Null element not allowed!");
        }
        // addAll with empty collection: expect return value false,
        // expect list unchanged
        List<Integer> backup = new LinkedList<>(integerListOne);
        assertFalse(integerListOne.addAll(2,Collections.emptyList()));
        assertEquals(integerListOne,backup);
    }

    /**
     * Test for removeAll
     *
     * @author Adem Balcioglu
     */
    @Test
    public void removeAll() {
        setup();
        assertTrue("Remove Function failed!", integerListTwo.removeAll(integerListOne));
        assertEquals("Wrong amount of elements removed!", integerListTwo.size(), 6);
        assertArrayEquals("Wrong result!", integerListTwo.toArray(), new Integer[]{-1, 2, 4, 3, 6, 7});

        //Parameter must not be null!
        try {
            integerListOne.removeAll(Arrays.asList(empty));
            fail();
        } catch (NullPointerException npe) {
            //System.err.println("Null element not allowed!");
        }

        try {
            integerListTwo.removeAll(Arrays.asList(empty));
            fail();
        } catch (NullPointerException npe) {
            //System.err.println("Null element not allowed!");
        }

        // Remove empty list. Expect return value false and unchanged original list
        List<Integer> backup = new LinkedList<>(integerListOne);
        assertFalse(integerListOne.removeAll(Collections.emptyList()));
        assertEquals(integerListOne,backup);
    }

    @Test
    public void retainAll() {
        setup();

        assertTrue("Retain Function failed!", integerListTwo.retainAll(integerListOne));
        assertEquals("Wrong amount of elements left after retaining!", integerListTwo.size(), 4);
        assertArrayEquals("Wrong result!", integerListTwo.toArray(), new Integer[]{0, 1, 5, 8});

        //Parameter must not be null!
        try {
            integerListOne.retainAll(Arrays.asList(empty));
            fail();
        } catch (NullPointerException npe) {
            //System.err.println("Null element not allowed!");
        }
        try {
            integerListTwo.retainAll(Arrays.asList(empty));
            fail();
        } catch (NullPointerException npe) {
            //System.err.println("Null element not allowed!");
        }

        // Retain empty list. Expect return value true and empty original list
        List<Integer> backup = new LinkedList<>(integerListOne);
        assertTrue(integerListOne.retainAll(Collections.emptyList()));
        assertTrue(integerListOne.isEmpty());

        // Retain the same list. Expect return value false and unchanged original list
        integerListOne.addAll(backup);
        assertFalse(integerListOne.retainAll(integerListOne));
        assertEquals(integerListOne,backup);
    }

    /**
     * Test for get
     *
     * @author Mischael Lameter
     */
    @Test
    public void get() {
        setup();
        /* Index Exceptions */

        // less than 0
        try {
            counting.get(-100);
            fail("Index was less than 0!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // equal size
        try {
            counting.get(counting.size());
            fail("Index was greater than size!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // greater than size
        try {
            counting.get(counting.size() + 1);
            fail("Index was greater than size!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        /* Correctness */
        for (int i = 0; i < counting.size(); i++)
            assertEquals("Received wrong value at index " + i, (int) counting.get(i), (int) TEST1[i]);
    }

    /**
     * Test for set
     *
     * @author Mischael Lameter
     */
    @Test
    public void set() {
        setup();
        /* Index Exceptions */

        // less than 0
        try {
            counting.set(-100, 25);
            fail("Index was less than 0!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // greater than size
        try {
            counting.set(counting.size() + 100, 25);
            fail("Index was greater than size!");
        } catch (IndexOutOfBoundsException ignored) {
        }


        /* Correctness */
        assertTrue(counting.set(1, 25) == 1);
        assertEquals("Wrong index was changed!", 0, (int) counting.get(0));
        assertEquals("Index was set to wrong value!", 25, (int) counting.get(1));

        for (int i = 2; i < counting.size(); i++)
            assertTrue("Wrong index was changed!", (int) counting.get(i) == TEST1[i]);
    }

    /**
     * Test for add, with additional index parameter
     *
     * @author Mischael Lameter
     */
    @Test
    public void testAdd() {
        setup();
        /* Index Exceptions */

        // less than 0
        try {
            counting.add(-100, 25);
            fail("Index was less than 0!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // greater than size
        try {
            counting.add(counting.size() + 100, 25);
            fail("Index was greater than size!");
        } catch (IndexOutOfBoundsException ignored) {
        }


        /* Correctness */

        counting.add(1, 25);
        assertEquals("Element before index was changed!", 0, (int) counting.get(0));
        assertEquals("Element wasn't inserted at correct index!", 25, (int) counting.get(1));

        for (int i = 2; i < counting.size(); i++)
            assertEquals("Element not at expected index!", (int) counting.get(i), (int) TEST1[i - 1]);

    }

    /**
     * Test for remove, with index parameter
     *
     * @author Mischael Lameter
     */
    @Test
    public void testRemove() {
        setup();
        /* Index Exceptions */

        // less than 0
        try {
            counting.remove(-100);
            fail("Index was less than 0!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // greater than size
        try {
            counting.remove(counting.size() + 100);
            fail("Index was greater than size!");
        } catch (IndexOutOfBoundsException ignored) {
        }


        /* Correctness */

        // correct element removed
        int r = counting.remove(1);
        assertEquals("Wrong element was removed!", r, (int) TEST1[1]);

        // correct alignment of other elements
        assertEquals("Other element was changed!", (int) counting.get(0), (int) TEST1[0]);

        for (int i = 2; i < counting.size(); i++)
            assertEquals("Index has wrong element!", (int) counting.get(i), (int) TEST1[i + 1]);
    }

    /**
     * Test for indexOf
     *
     * @author Mischael Lameter
     */
    @Test
    public void indexOf() {
        setup();
        // not contained case
        assertEquals("Recieved index for a value which is not in the list!", repeating.indexOf(123), -1);

        // unique value case
        assertEquals("Did not receive correct index for unique value!", 1, repeating.indexOf(0));

        // repeating-in-sequence
        assertEquals("Did not receive first index for repeating values!", 2, repeating.indexOf(1));
    }

    /**
     * Test for lastIndexOf
     *
     * @author Mischael Lameter
     */
    @Test
    public void lastIndexOf() {
        setup();
        // not contained case
        assertEquals("Recieved index for a value which is not in the list!", repeating.lastIndexOf(123), -1);

        // unique value case
        assertEquals("Did not receive correct index for unique value!", 1, repeating.lastIndexOf(0));

        // repeating-in-sequence
        assertEquals("Did not receive last index for repeating values!", 4, repeating.lastIndexOf(1));
    }

    /**
     * Test for listIterator, no parameters
     *
     * @author Mischael Lameter
     */
    @Test
    public void listIterator() {
        setup();
        // null case
        ListIterator<Integer> lit = counting.listIterator();
        assertNotNull("Returned null!", lit);

        // correct start
        assertEquals("ListIterator did not start at 0!", 0, lit.nextIndex());

        // correctness
        Integer[] vals = new Integer[TEST1.length];

        int i = 0;
        while (lit.hasNext())
            vals[i++] = lit.next();

        assertArrayEquals("ListIterator did not return correct values!", TEST1, vals);
    }

    /**
     * Test for listIterator, with index parameter
     *
     * @author Mischael Lameter
     */
    @Test
    public void testListIterator() {
        setup();
        /* Index Exceptions */

        // less than zero
        try {
            counting.listIterator(-100);
            fail("Index was less than 0!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // greater than size
        try {
            counting.listIterator(counting.size() + 100);
            fail("Index was greater than size!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        /* Correctness of Index */
        assertEquals("ListIterator had wrong index!", 2, counting.listIterator(2).nextIndex());
    }

    /**
     * Test for subList
     *
     * @author Mischael Lameter
     */
    @Test
    public void subList() {
        setup();
        // Index Exceptions

        // fromIndex < 0
        try {
            counting.subList(-100, 2);
            fail("Start Index was less than 0!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // toIndex > size
        try {
            counting.subList(0, TEST1.length + 100);
            fail("End Index was greater than size!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // fromIndex > toIndex
        try {
            counting.subList(3, 2);
            fail("Start Index was greater than End Index!");
        } catch (IllegalArgumentException ignored) {
        }


        // Equal Indices
        assertTrue("Start Index = End Index did not result in empty List!", counting.subList(2, 2).isEmpty());

        // Example Case

        // correctness of result

        List<Integer> sub = counting.subList(2, 4);
        for (int i = 0; i < sub.size(); i++) {
            assertEquals("Expected " + TEST1[i + 2] + ", got " + sub.get(i), sub.get(i), TEST1[i + 2]);
        }

        // correctness of structure
        sub.set(0, 0);
        assertEquals("Change in sublist did not carry over!", 0, (int) counting.get(2));
    }

    /**
     * Tests comodification
     */
    @Test
    public void commodifySubList() {
        setup();
        List<Integer> sub = counting.subList(0, 4);
        /* Commodification Exceptions */
        counting.remove(2);

        try {
            sub.size();
            fail("Expected ConcurrentModificationException!");
        } catch (ConcurrentModificationException ignored) {
        }

        try {
            sub.get(2);
            fail("Expected ConcurrentModificationException!");
        } catch (ConcurrentModificationException ignored) {
        }

        try {
            sub.set(2, 25);
            fail("Expected ConcurrentModificationException!");
        } catch (ConcurrentModificationException ignored) {
        }

        try {
            sub.add(1, 25);
            fail("Expected ConcurrentModificationException!");
        } catch (ConcurrentModificationException ignored) {
        }

        try {
            sub.remove(2);
            fail("Expected ConcurrentModificationException!");
        } catch (ConcurrentModificationException ignored) {
        }

        try {
            sub.addAll(1, repeating);
            fail("Expected ConcurrentModificationException!");
        } catch (ConcurrentModificationException ignored) {
        }

        try {
            sub.listIterator(1);
            fail("Expected ConcurrentModificationException!");
        } catch (ConcurrentModificationException ignored) {
        }
    }

    /**
     * Test for a subList of a sublist
     *
     * @author Mischael Lameter
     */
    @Test
    public void subSubList() {
        setup();

        List<Integer> sub = counting.subList(0, 4);

        /* for subList() */
        // fromIndex < 0
        try {
            sub.subList(-100, 2);
            fail("Start Index was less than 0!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // toIndex > size
        try {
            sub.subList(0, TEST1.length + 100);
            fail("End Index was greater than size!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // fromIndex > toIndex
        try {
            sub.subList(3, 2);
            fail("Start Index was greater than End Index!");
        } catch (IllegalArgumentException ignored) {
        }

        // correctness
        List<Integer> subsub = sub.subList(0, 2);
        for (int i = 0; i < subsub.size(); i++)
            assertEquals(subsub.get(i), sub.get(i));

        /* for listIterator */
        // index < 0
        try {
            sub.listIterator(-100);
            fail("Index was less than 0!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // index > size
        try {
            sub.listIterator(sub.size() + 100);
            fail();
        } catch (IndexOutOfBoundsException ignored) {
        }

    }

    /**
     * Test sublist add
     *
     * @author Mischael Lameter
     */
    @Test
    public void subAdd() {
        setup();
        List<Integer> sub = counting.subList(0, 4);

        // index < 0
        try {
            sub.add(-100, -100);
            fail("Index was less than 0!");
        } catch (IndexOutOfBoundsException ignored) {
        }

        // index > size
        try {
            sub.add(sub.size() + 100, 100);
            fail();
        } catch (IndexOutOfBoundsException ignored) {
        }

        // correctness
        sub.add(2, 10);

        assertEquals(10, (int) sub.get(2));
        assertEquals(10, (int) counting.get(2));

        assertEquals(sub.get(0), TEST1[0]);
        assertEquals(sub.get(1), TEST1[1]);
        assertEquals(counting.get(0), TEST1[0]);
        assertEquals(counting.get(1), TEST1[1]);

        int i = 3;
        for (; i < sub.size(); i++) {
            assertEquals(sub.get(i), TEST1[i - 1]);
            assertEquals(counting.get(i), TEST1[i - 1]);
        }
        for (; i < counting.size(); i++)
            assertEquals(counting.get(i), TEST1[i - 1]);
    }

    /**
     * Test sublist remove
     *
     * @author Mischael Lameter
     */
    @Test
    public void subRemove() {
        setup();
        List<Integer> sub = counting.subList(1, 5);

        Integer r = sub.remove(3);

        assertEquals(r, TEST1[4]);
        assertEquals(3, sub.size());
        assertEquals(counting.size(), TEST1.length - 1);
        assertFalse(counting.contains(TEST1[4]));
    }

    /**
     * Test sublist addAll
     *
     * @author Mischael Lameter
     */
    @Test
    public void subAddAll() {
        setup();
        List<Integer> sub = counting.subList(3, counting.size());

        assertTrue(sub.addAll(Arrays.asList(TEST2)));
        assertFalse(sub.addAll(Collections.emptyList()));

        assertEquals(sub.size(), TEST1.length + TEST2.length - 3);
        assertEquals(counting.size(), TEST1.length + TEST2.length);

        int i = 0;
        for (; i < TEST1.length - 3; i++)
            assertEquals(sub.get(i), TEST1[i + 3]);
        for (; i < sub.size(); i++)
            assertEquals(sub.get(i), TEST2[i + 3 - TEST1.length]);

        i = 0;
        for (; i < TEST1.length; i++)
            assertEquals(counting.get(i), TEST1[i]);
        for (; i < counting.size(); i++)
            assertEquals(counting.get(i), TEST2[i - TEST1.length]);
    }

    /**
     * Test for sublist addAll, with index parameter
     *
     * @author Mischael Lameter
     */
    @Test
    public void subIndexAddAll() {
        setup();
        List<Integer> sub = counting.subList(0, 3);

        try {
            sub.addAll(-100, Arrays.asList(TEST2));
            fail();
        } catch (IndexOutOfBoundsException ignored) {
        }

        try {
            sub.addAll(sub.size() + 100, Arrays.asList(TEST2));
            fail();
        } catch (IndexOutOfBoundsException ignored) {
        }


        sub.addAll(0, Arrays.asList(TEST2));

        int i = 0;
        for (; i < TEST2.length; i++) {
            assertEquals(sub.get(i), TEST2[i]);
            assertEquals(counting.get(i), TEST2[i]);
        }

        assertEquals(sub.get(i), TEST1[0]);
        assertEquals(counting.get(i++), TEST1[0]);
        assertEquals(sub.get(i), TEST1[1]);
        assertEquals(counting.get(i++), TEST1[1]);
        assertEquals(sub.get(i), TEST1[2]);
        assertEquals(counting.get(i++), TEST1[2]);

        for (; i < TEST1.length + TEST2.length; i++)
            assertEquals(counting.get(i), TEST1[i - TEST2.length]);
    }

    /**
     * Test for sublist iterator
     *
     * @author Mischael Lameter
     */
    @Test
    public void subIterator() {
        setup();
        List<Integer> sub = counting.subList(0, 4);

        assertNotNull(sub.iterator());
        assertNotNull(sub.listIterator());
        assertNotNull(sub.listIterator(0));

        try {
            sub.listIterator(-1);
            fail();
        } catch (IndexOutOfBoundsException ignored) {
        }
        try {
            sub.listIterator(sub.size() + 100);
            fail();
        } catch (IndexOutOfBoundsException ignored) {
        }

        Iterator<Integer> subit = sub.listIterator(0);
        int i = 0;
        while (subit.hasNext()) {
            Integer n = subit.next();
            assertEquals(sub.get(i), n);
            assertEquals(counting.get(i), n);
            i++;
        }

        if (i != 4)
            fail("i = " + i);
    }

    //DlrListIterator

    /**
     * Test for hasPrevious
     *
     * @author Adem Balcioglu
     */
    @Test
    public void hasPrevious() {
        setup();
        assertFalse("Wrong Result! List Iterator has not an previous Element at Index 0!",
                iteratorThree.hasPrevious());
        iteratorThree.next();
        assertTrue("Wrong Result! List Iterator has an previous Element at Index 1!",
                iteratorThree.hasPrevious());

        while(iteratorThree.hasNext())
        {
            iteratorThree.next();

            assertTrue("Wrong Result! List Iterator has an previous Element at Index 1!",
                    iteratorThree.hasPrevious());
        }
    }

    /**
     * Test for previous
     *
     * @author Adem Balcioglu
     */
    @Test
    public void previous() {
        setup();
        try {
            iteratorThree.previous();
            fail("Exception was expected!");
        } catch (NoSuchElementException ignored) {

        }
        iteratorThree.next();
        try {
            assertEquals(0, (int) iteratorThree.previous());
        } catch (NoSuchElementException e) {
            fail("Exception thrown in wrong case!");
        }

        while (iteratorThree.hasNext()) {
            iteratorThree.next();
        }

        //Multiple precious call
        while (iteratorThree.hasPrevious()) {
            try {
                iteratorThree.previous();
            } catch (NoSuchElementException e) {
                fail("Exception thrown in wrong case!");
            }
        }
    }

    /**
     * Test for nextIndex
     *
     * @author Adem Balcioglu
     */
    @Test
    public void nextIndex() {
        setup();
        assertEquals(0, iteratorThree.nextIndex());

        //Result: {0 ... 9}
        DlrList<Integer> numberForward = new DlrList<>();
        for (int i = 0; i < 10; i++) {
            numberForward.add(i);
        }

        ListIterator<Integer> ite = numberForward.listIterator();

        while (ite.hasNext()) {
            assertEquals("Wrong Index!", ite.nextIndex(), (int) ite.next());
        }
    }

    /**
     * Test for previousIndex
     *
     * @author Adem Balcioglu
     */
    @Test
    public void previousIndex() {
        setup();
        assertEquals(0, iteratorThree.nextIndex());

        //Result: {0 ... 9}
        DlrList<Integer> numberForward = new DlrList<>();
        for (int i = -1; i < 10; i++) {
            numberForward.add(i);
        }

        ListIterator<Integer> ite = numberForward.listIterator();

        while (ite.hasNext()) {
            if (ite.hasPrevious()) {
                assertEquals("Wrong Index!", ite.previousIndex(), (int) ite.next());
            } else {
                ite.next();
            }
        }
    }

    /**
     * Test for Listiterator - set
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testSet() {
        setup();
        try {
            iteratorThree.set(2000);
            fail("Exception expected! Next method has not been called yet, there is no selected element!");
        } catch (IllegalStateException ignored) {
        }
        iteratorThree.next();
        try {
            iteratorThree.set(2000);
        } catch (IllegalStateException e) {
            fail("Exception thrown in wrong case!");
        }

        assertEquals("Set Function failed!", 2000, (int) iteratorThree.previous());

        //Multiple call
        while (iteratorThree.hasNext()) {
            iteratorThree.next();
            try {
                iteratorThree.set(2000);
            } catch (IllegalStateException e) {
                fail("Exception thrown in wrong case!");
            }
        }

        while (iteratorThree.hasPrevious()) {
            assertEquals("Set Function failed!", 2000, (int) iteratorThree.previous());
        }
    }

    /**
     * Test for Listiterator - add
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testListIteratorAdd() {
        setup();
        iteratorThree.add(2000);
        iteratorThree.add(-2000);
        iteratorThree.add(0);
        iteratorThree.add(1 << 31);
        assertNotEquals("Add Method inserts elements in wrong order!",
                2000, (int) iteratorThree.next());
        iteratorThree.previous();
        assertEquals((1 << 31), (int) iteratorThree.next());
        assertEquals(0, (int) iteratorThree.next());
        assertEquals(-2000, (int) iteratorThree.next());
        assertEquals(2000, (int) iteratorThree.next());
    }

    /**
     * Test for sublist hasNext
     *
     * @author Adem Balcioglu
     */
    @Test
    public void hasNext() {
        setup();
        List<Integer> sublist = integerListTwo.subList(0,4); //{0,-1,2,1,4}
        ListIterator<Integer> ite = sublist.listIterator(0);

        while (ite.next() != 1)
        {
            assertTrue("Wrong Result! List Iterator has an previous Element at Index 1!",
                    ite.hasNext());
        }
        assertFalse("Wrong Result! List Iterator has an previous Element at Index 1!",
                ite.hasNext());
    }

    /**
     * Test for sublist next
     *
     * @author Adem Balcioglu
     */
    @Test
    public void next() {
        setup();
        List<Integer> sublist = integerListTwo.subList(0,4); //{0,-1,2,1,4}
        ListIterator<Integer> ite = sublist.listIterator(0);

        try {
            assertEquals(0,(int)ite.next());
        }catch (NoSuchElementException e)
        {
            fail("Exception thrown in wrong case!");
        }

        while (ite.hasNext())
        {
            try {
                ite.next();
            }catch (NoSuchElementException e)
            {
                fail("Exception thrown in wrong case!");
            }
        }

        try {
            ite.next();
            fail("Exception expected!");
        }catch (NoSuchElementException ignored)
        {
        }
    }

    /**
     * Test for sublist previous
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testPrevious() {
        setup();
        List<Integer> sublist = integerListTwo.subList(0,4); //{0,-1,2,1,4}
        ListIterator<Integer> ite = sublist.listIterator(0);
        try {
            ite.previous();
            fail("Exception was expected!");
        } catch (NoSuchElementException ignored) {

        }
        ite.next();
        try {
            assertEquals(0, (int) ite.previous());
        } catch (NoSuchElementException e) {
            fail("Exception thrown in wrong case!");
        }

        while (ite.hasNext()) {
            ite.next();
        }

        //Multiple precious call
        while (ite.hasPrevious()) {
            try {
                ite.previous();
            } catch (NoSuchElementException e) {
                fail("Exception thrown in wrong case!");
            }
        }
    }

    /**
     * Test for sublist nextIndex
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testNextIndex() {
        setup();
        List<Integer> sublist = integerListTwo.subList(0,4); //{0,-1,2,1,4}
        ListIterator<Integer> ite = sublist.listIterator(0);
        assertEquals(0, ite.nextIndex());

        //Result: {0 ... 9}
        DlrList<Integer> numberForward = new DlrList<>();
        for (int i = 0; i < 10; i++) {
            numberForward.add(i);
        }

        ite = numberForward.subList(0,8).listIterator(0);

        while (ite.hasNext()) {
            assertEquals("Wrong Index!", ite.nextIndex(), (int) ite.next());
        }
    }

    /**
     * Test for sublist prevIndex
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testPrevIndex() {
        setup();
        List<Integer> sublist = integerListTwo.subList(0,4); //{0,-1,2,1,4}
        ListIterator<Integer> ite = sublist.listIterator(0);
        assertEquals(-1, ite.previousIndex());

        //Result: {0 ... 9}
        DlrList<Integer> numberForward = new DlrList<>();
        for (int i = -1; i < 10; i++) {
            numberForward.add(i);
        }

        ite = numberForward.subList(0,8).listIterator(0);

        while (ite.hasNext()) {
            assertEquals("Wrong Index!", ite.previousIndex(), (int) ite.next());
        }
    }

    /**
     * Test for sublist remove
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testSubListRemove() {
        setup();
        List<Integer> sublist = integerListTwo.subList(0,4); //{0,-1,2,1,4}
        ListIterator<Integer> ite = sublist.listIterator(0);

        while (ite.hasNext())
        {
            if(ite.next()>0) ite.remove();
        }

        ite = sublist.listIterator(0);

        try {
            assertEquals(0, (int) ite.next());
            assertEquals(-1, (int) ite.next());
        }catch(NoSuchElementException e)
        {
            fail();
        }
        assertFalse(ite.hasNext());
    }

    /**
     * Test for sublist set
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testSubListSet() {
        setup();
        List<Integer> sublist = integerListTwo.subList(0,4); //{0,-1,2,1,4}
        ListIterator<Integer> ite = sublist.listIterator(0);
        try {
            ite.set(2000);
            fail("Exception expected! Next method has not been called yet, there is no selected element!");
        } catch (IllegalStateException ignored) {
        }
        ite.next();
        try {
            ite.set(2000);
        } catch (IllegalStateException e) {
            fail("Exception thrown in wrong case!");
        }

        assertEquals("Set Function failed!", 2000, (int) ite.previous());

        //Multiple call
        while (ite.hasNext()) {
            ite.next();
            try {
                ite.set(2000);
            } catch (IllegalStateException e) {
                fail("Exception thrown in wrong case!");
            }
        }

        while (ite.hasPrevious()) {
            assertEquals("Set Function failed!", 2000, (int) ite.previous());
        }
    }

    /**
     * Test for sublist add
     *
     * @author Adem Balcioglu
     */
    @Test
    public void testSubListAdd() {
        setup();
        List<Integer> sublist = integerListTwo.subList(0,4); //{0,-1,2,1,4}
        ListIterator<Integer> ite = sublist.listIterator(0);
        ite.add(2000);
        ite.add(-2000);
        ite.add(0);
        ite.add(1 << 31);
        assertNotEquals("Add Method inserts elements in wrong order!",
                2000, (int) ite.next());
        ite.previous();
        assertEquals((1 << 31), (int) ite.next());
        assertEquals(0, (int) ite.next());
        assertEquals(-2000, (int) ite.next());
        assertEquals(2000, (int) ite.next());
    }

    @Test
    public void testHashCode() {

        List<Integer> l1 = new DlrList<>();
        for (int i : TEST1) l1.add(i);

        List<Integer> l2 = new DlrList<>();
        l2.addAll(l1);
        assertEquals(l1.hashCode(),l2.hashCode());

        Integer aux  = l2.get(2);
        l2.set(2,0);
        l2.set(0,aux);

        assertNotEquals(l1.hashCode(),l2.hashCode());

        // Back to back test: LinkedLists must have the same hashCode
        l1.clear();;
        for (int i : TEST1) l1.add(i);
        List<Integer> ll = new LinkedList<>();
        for (int i : TEST1) ll.add(i);

        assertEquals(l1.hashCode(),ll.hashCode());
    }

    @Test
    public void testEquals() {

        List<Integer> l1 = new DlrList<>();
        for (int i : TEST1) l1.add(i);

        assertTrue(l1.equals(l1));

        List<Integer> l2 = new DlrList<>();
        l2.addAll(l1);
        assertTrue(l1.equals(l2));
        assertTrue(l2.equals(l1));
        assertFalse(l1.equals(null));

        Integer aux  = l2.get(2);
        l2.set(2,0);
        l2.set(0,aux);

        assertFalse(l1.equals(l2));
        assertFalse(l2.equals(l1));

        // Lists of different size must not be equal
        l1.clear();
        for (int i : TEST1) l1.add(i);
        l2.clear();
        for (int i : TEST1) l2.add(i);
        l2.add(17);
        assertFalse(l1.equals(l2));
        assertFalse(l2.equals(l1));

    }

}
