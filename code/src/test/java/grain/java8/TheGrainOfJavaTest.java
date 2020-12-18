package grain.java8;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TheGrainOfJavaTest{
    @Test
    public void testMap() {
        List<String> words = Arrays.asList("a", "b", "c");

        /// begin: map
        List<String> upperCaseWords = words.stream().map(String::toUpperCase).collect(toList());
        /// end: map

        assertEquals(Arrays.asList("A", "B", "C"), upperCaseWords);
    }

    @Test
    public void testReduce() {
        List<Integer> counts = Arrays.asList(1, 20, 12);

        /// begin: reduce
        int sum = counts.stream().reduce(0, Integer::sum);
        /// end: reduce

        assertEquals(33, sum);
    }
}
