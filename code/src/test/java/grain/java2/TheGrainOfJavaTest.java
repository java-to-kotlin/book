package grain.java2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TheGrainOfJavaTest{
    @Test
    public void testReduce() {
        List counts = Arrays.asList(new Integer(1), new Integer(20), new Integer(12));

        /// begin: reduce
        int sum = ((Integer) Lists.fold(counts, new Integer(0),
            new Function2() {
                public Object apply(Object arg1, Object arg2) {
                    int i1 = ((Integer) arg1).intValue();
                    int i2 = ((Integer) arg2).intValue();
                    return new Integer(i1 + i2);
                }
            })).intValue();
        /// end: reduce

        assertEquals(33, sum);
    }

    @Test
    public void testReduceByIteration() {
        List counts = Arrays.asList(new Integer(1), new Integer(20), new Integer(12));

        /// begin: reduce_iteratively
        int sum = 0;
        for (int i = 0; i < counts.size(); i++) {
            sum += ((Integer) counts.get(i)).intValue();
        }
        /// end: reduce_iteratively

        assertEquals(33, sum);
    }
}
