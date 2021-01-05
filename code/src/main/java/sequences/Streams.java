package sequences;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class Loop {
    /// begin: loop
    public static double averageNonBlankLength(List<String> strings) {
        var sum = 0;
        for (var s : strings) {
            if (!s.isBlank())
                sum += s.length();
        }
        return sum / (double) strings.size();
    }
    /// end: loop
}

class StreamEx {
    /// begin: stream
    public static double averageNonBlankLength(List<String> strings) {
        return strings
            .stream()
            .filter(s -> !s.isBlank())
            .mapToInt(String::length)
            .sum()
            / (double) strings.size();
    }
    /// end: stream
}

class ParallelStream {
    /// begin: parallelStream
    public static double averageNonBlankLength(List<String> strings) {
        return strings
            .parallelStream() // <1>
            .filter(s -> !s.isBlank())
            .mapToInt(String::length)
            .sum()
            / (double) strings.size();
    }
    /// end: parallelStream
}

class NaiveStream {
    /// begin: naiveStream
    public static double averageNonBlankLength(List<String> strings) {
        return strings
            .parallelStream()
            .filter(s -> !s.isBlank())
            .map(String::length)
            .reduce(0, Integer::sum)
            / (double) strings.size();
    }
    /// end: naiveStream
}


class ConsumingTwice {
    /// begin: consumingTwice
    public static double averageNonBlankLength(List<String> strings) {
        return averageNonBlankLength(strings.stream());
    }

    public static double averageNonBlankLength(Stream<String> strings) {
        return strings
            .filter(s -> !s.isBlank())
            .mapToInt(String::length)
            .sum()
            / (double) strings.count();
    }
    /// end: consumingTwice
}