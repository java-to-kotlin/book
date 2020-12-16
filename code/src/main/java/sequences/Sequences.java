package sequences;

import java.util.Collection;
import java.util.List;

class Loop {
    /// begin: loop
    public static double averageLengthOfNonBlanks(List<String> strings) {
        var sum = 0;
        for (var s : strings) {
            if (!s.isBlank())
                sum += s.length();
        }
        return sum / (double) strings.size();
    }
    /// end: loop
}

class Sequence {
    /// begin: sequence
    public static double averageLengthOfNonBlanks(List<String> strings) {
        return strings
            .stream()
            .filter(s -> !s.isBlank())
            .mapToInt(String::length)
            .sum()
            / (double) strings.size();
    }
    /// end: sequence
}

class ParallelSequence {
    /// begin: parallelSequence
    public static double averageLengthOfNonBlanks(List<String> strings) {
        return strings
            .parallelStream() // <1>
            .filter(s -> !s.isBlank())
            .mapToInt(String::length)
            .sum()
            / (double) strings.size();
    }
    /// end: parallelSequence
}
