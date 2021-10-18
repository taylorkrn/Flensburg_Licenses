package hashing;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class HashTableTest {

    @Test
    public void basicTest(){
        HashTable<Integer> test = new HashTable<Integer>(10);
        int[] toAdd = new int[]{2,12,13,33,24,54,16,48,8,9};
        for (int a: toAdd){
            test.add(a);
        }
        assertTrue(test.contains(12));
        assertTrue(test.contains(2));

        assertTrue(test.remove(54));
        assertFalse(test.remove(54));

        assertFalse(test.contains(54));
        assertEquals(test.size(),9);
        assertFalse(test.isEmpty());

        Iterator<Integer> ite = test.iterator();

        assertTrue(ite.next().equals(2));
        ite.remove();
        assertTrue(ite.hasNext());
        assertFalse(test.contains(2));

        Object[] out = test.toArray();
        Object[] out2 = new Object[3];
        out2 = test.toArray(out2);
        assertTrue(Arrays.equals(out,out2));
        Object[] out3 = new Object[23];
        out3 = test.toArray(out3);
        Object[] finalout =  new Object[8];
        for (int i = 0; i<finalout.length;i++){
            finalout[i]=out3[i];
        }
        assertTrue(Arrays.equals(out,finalout));

        test.clear();
        assertTrue(test.isEmpty());
    }
}