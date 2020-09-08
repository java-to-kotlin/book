package grain.java8;

import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class TheGrainOfJavaTest extends TestCase {
    public void testMap() {
        List<String> words = Arrays.asList("a", "b", "c");

        /// begin: map
        List<String> upperCaseWords = words.stream().map(String::toUpperCase).collect(toList());
        /// end: map

        assertEquals(Arrays.asList("A", "B", "C"), upperCaseWords);
    }

    public void testReduce() {
        List<Integer> counts = Arrays.asList(1, 20, 12);

        /// begin: reduce
        int sum = counts.stream().reduce(0, Integer::sum);
        /// end: reduce

        assertEquals(33, sum);
    }
}
