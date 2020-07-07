package grain.java1;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


public class TheGrainOfJava1Point0Test extends TestCase {
    public void testMap() {
        List words = new ArrayList();
        words.add("a");
        words.add("b");
        words.add("c");

        // begin: map
        List upperCaseWords = Lists.map(new StringToUpperCase(), words);
        // end: map

        List expected = new ArrayList();
        expected.add("A");
        expected.add("B");
        expected.add("C");

        assertEquals(expected, upperCaseWords);
    }

    public void testMutateList() {
        List words = new ArrayList();
        words.add("a");
        words.add("b");
        words.add("c");

        // begin: map_iteratively
        List upperCaseWords = new ArrayList();
        for (int i = 0; i < words.size(); i++) {
            upperCaseWords.add(((String)words.get(i)).toUpperCase());
        }
        // end: map_iteratively

        List expected = new ArrayList();
        expected.add("A");
        expected.add("B");
        expected.add("C");

        assertEquals(expected, upperCaseWords);
    }


    public void testReduce() {
        List counts = new ArrayList();
        counts.add(new Integer(1));
        counts.add(new Integer(20));
        counts.add(new Integer(12));

        // begin: reduce
        Integer total = (Integer)Lists.reduce(new AddIntegers(), counts);
        // end: reduce

        assertEquals(33, total.intValue());
    }

    public void testReduceByIteration() {
        List counts = new ArrayList();
        counts.add(new Integer(1));
        counts.add(new Integer(20));
        counts.add(new Integer(12));

        // begin: reduce_iteratively
        int total = 0;
        for (int i = 0; i < counts.size(); i++) {
            total += ((Integer)counts.get(i)).intValue();
        }
        // end: reduce_iteratively

        assertEquals(33, total);
    }

}
