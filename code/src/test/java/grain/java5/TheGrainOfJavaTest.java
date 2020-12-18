package grain.java5;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TheGrainOfJavaTest{

    @Test
    public void testMap() {
        List<String> words = Arrays.asList("a", "b", "c");

        /// begin: map
        List<String> upperCaseWords = Lists.map(words, new Function1<String, String>() {
            @Override
            public String apply(String arg) {
                return arg.toUpperCase();
            }
        });
        /// end: map

        assertEquals(Arrays.asList("A", "B", "C"), upperCaseWords);
    }

    @Test
    public void testMutateList() {
        List<String> words = Arrays.asList("a", "b", "c");

        /// begin: map_iteratively
        List<String> upperCaseWords = new ArrayList<String>();
        for (String word : words) {
            upperCaseWords.add(word.toUpperCase());
        }
        /// end: map_iteratively

        assertEquals(Arrays.asList("A", "B", "C"), upperCaseWords);
    }


    @Test
    public void testReduce() {
        List<Integer> counts = Arrays.asList(1, 20, 12);

        /// begin: reduce
        int sum = Lists.reduce(counts, 0,
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
