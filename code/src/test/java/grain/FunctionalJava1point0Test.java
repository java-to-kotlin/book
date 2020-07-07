package grain;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

// begin: string_to_uppercase
class StringToUpperCase implements Function1 {
    public Object apply(Object arg) {
        return ((String) arg).toUpperCase();
    }
}
// begin: string_to_uppercase

// begin: integer_add
class IntegerAdd implements Monoid {
    public Object nil() {
        return new Integer(0);
    }

    public Object apply(Object arg1, Object arg2) {
        return new Integer(((Integer) arg1).intValue() +
            ((Integer) arg2).intValue());
    }
}
// end: integer_add

public class FunctionalJava1point0Test extends TestCase {
    public void testMap() {
        // begin: map
        List words = new ArrayList();
        words.add("a");
        words.add("b");
        words.add("c");

        List upperCaseWords = Lists.map(new StringToUpperCase(), words);
        // end: map

        List expected = new ArrayList();
        expected.add("A");
        expected.add("B");
        expected.add("C");

        assertEquals(expected, upperCaseWords);
    }

    public void testReduce() {
        // begin: reduce
        List counts = new ArrayList();
        counts.add(new Integer(1));
        counts.add(new Integer(20));
        counts.add(new Integer(12));

        Integer total = (Integer)Lists.reduce(new IntegerAdd(), counts);
        // end: reduce

        assertEquals(33, total.intValue());
    }

}
