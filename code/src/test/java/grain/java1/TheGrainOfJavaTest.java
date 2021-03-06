package grain.java1;

import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TheGrainOfJavaTest {
    @Test
    public void testMap() {
        Vector words = new Vector();
        words.add("a");
        words.add("b");
        words.add("c");

        /// begin: map
        Vector upperCaseWords = Vectors.map(words, new StringToUpperCase());
        /// end: map

        Vector expected = new Vector();
        expected.add("A");
        expected.add("B");
        expected.add("C");

        assertEquals(expected, upperCaseWords);
    }

    @Test
    public void testMutateList() {
        Vector words = new Vector();
        words.add("a");
        words.add("b");
        words.add("c");

        /// begin: map_iteratively
        Vector upperCaseWords = new Vector();
        for (int i = 0; i < words.size(); i++) {
            upperCaseWords.add(((String)words.get(i)).toUpperCase());
        }
        /// end: map_iteratively

        Vector expected = new Vector();
        expected.add("A");
        expected.add("B");
        expected.add("C");

        assertEquals(expected, upperCaseWords);
    }


    @Test
    public void testReduce() {
        Vector counts = new Vector();
        counts.add(new Integer(1));
        counts.add(new Integer(20));
        counts.add(new Integer(12));

        /// begin: reduce
        int sum = ((Integer) Vectors.fold(counts, new Integer(0), new AddIntegers()))
            .intValue();
        /// end: reduce

        assertEquals(33, sum);
    }

    @Test
    public void testReduceByIteration() {
        Vector counts = new Vector();
        counts.add(new Integer(1));
        counts.add(new Integer(20));
        counts.add(new Integer(12));

        /// begin: reduce_iteratively
        int sum = 0;
        for (int i = 0; i < counts.size(); i++) {
            sum += ((Integer)counts.get(i)).intValue();
        }
        /// end: reduce_iteratively

        assertEquals(33, sum);
    }
}
