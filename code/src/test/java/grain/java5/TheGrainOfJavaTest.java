package grain.java5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TheGrainOfJavaTest{
    @Test
    public void testReduce() {
        List<Integer> counts = Arrays.asList(1, 20, 12);

        /// begin: reduce
        int sum = Lists.fold(counts, 0,
            new Function2<Integer, Integer, Integer>() {
                @Override
                public Integer apply(Integer arg1, Integer arg2) {
                    return arg1 + arg2;
                }
            });
        /// end: reduce

        assertEquals(33, sum);
    }

    @Test
    public void testReduceByIteration() {
        List<Integer> counts = Arrays.asList(1, 20, 12);

        /// begin: reduce_iteratively
        int sum = 0;
        for (Integer count : counts) {
            sum += count;
        }
        /// end: reduce_iteratively

        assertEquals(33, sum);
    }
}
