package grain.java8;

import grain.java5.Function1;
import grain.java5.Function2;
import grain.java5.Lists;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

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

        /// begin: fold
        int sum = counts.stream().reduce(0, Integer::sum);
        /// end: fold

        assertEquals(33, sum);
    }
}
