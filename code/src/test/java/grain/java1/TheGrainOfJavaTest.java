package grain.java1;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TheGrainOfJavaTest extends TestCase {
    public void testMap() {
        List words = Arrays.asList("a", "b", "c");

        /// begin: map
        List upperCaseWords = Lists.map(new StringToUpperCase(), words);
        /// end: map

        List expected = Arrays.asList("A", "B", "C");

        assertEquals(expected, upperCaseWords);
    }

    public void testMutateList() {
        List words = Arrays.asList("a", "b", "c");

        /// begin: map_iteratively
        List upperCaseWords = new ArrayList();
        for (int i = 0; i < words.size(); i++) {
            upperCaseWords.add(((String)words.get(i)).toUpperCase());
        }
        /// end: map_iteratively

        List expected = Arrays.asList("A", "B", "C");

        assertEquals(expected, upperCaseWords);
    }


    public void testReduce() {
        List counts = Arrays.asList(new Integer(1), new Integer(20), new Integer(12));

        /// begin: fold
        int sum = ((Integer) Lists.fold(new AddIntegers(), new Integer(0), counts)).intValue();
        /// end: fold

        assertEquals(33, sum);
    }

    public void testReduceByIteration() {
        List counts = Arrays.asList(new Integer(1), new Integer(20), new Integer(12));

        /// begin: reduce_iteratively
        int sum = 0;
        for (int i = 0; i < counts.size(); i++) {
            sum += ((Integer)counts.get(i)).intValue();
        }
        /// end: reduce_iteratively

        assertEquals(33, sum);
    }
}
