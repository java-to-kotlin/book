package sequences;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.util.stream.Collectors.toList;

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

class Loop2 {
    /// begin: loop2
    public static double averageNonBlankLength(List<String> strings) {
        var sum = 0;
        for (var s : strings) {
            sum += s.isBlank() ? 0 : s.length();
        }
        return sum / (double) strings.size();
    }
    /// end: loop2
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

class StreamEx2 {
    /// begin: stream2
    public static double averageNonBlankLength(List<String> strings) {
        return strings
            .stream()
            .mapToInt(s -> s.isBlank() ? 0 : s.length())
            .average()
            .orElse(Double.NaN);
    }
    /// end: stream2
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

class TranslatingLoop {
    /// begin: translatingLoop
    public static List<String> translatedWordsUntilSTOP(List<String> strings) {
        var result = new ArrayList<String>();
        for (var word: strings) {
            String translation = translate(word);
            if (translation.equalsIgnoreCase("STOP"))
                break;
            else
                result.add(translation);
        }
        return result;
    }
    /// end: translatingLoop

    public static String translate(String word) {
        return word;
    }
}

class TranslatingStream {
    /// begin: translatingStream
    public static List<String> translatedWordsUntilSTOP(List<String> strings) {
        return strings
            .stream()
            .map(word -> translate(word))
            .takeWhile(translation -> !translation.equalsIgnoreCase("STOP"))
            .collect(toList());
    }
    /// end: translatingStream

    public static String translate(String word) {
        return word;
    }
}