package sequences

object Lists {
    /// begin: lists
    fun averageNonBlankLength(strings: List<String>): Double =
        (strings
            .filter { it.isNotBlank() }
            .map(String::length)
            .sum()
            / strings.size.toDouble())
    /// end: lists
}

object Sequences {
    /// begin: sequences
    fun averageNonBlankLength(strings: List<String>): Double =
        (strings
            .asSequence()
            .filter { it.isNotBlank() }
            .map(String::length)
            .sum()
            / strings.size.toDouble())
    /// end: sequences
}

object Sequences2 {
    /// begin: sumBy
    fun averageNonBlankLength(strings: List<String>): Double =
        (strings
            .asSequence()
            .filter { it.isNotBlank() }
            .sumBy(String::length)
            / strings.size.toDouble())
    /// end: sumBy
}

object Streams {
    /// begin: streams
    fun averageNonBlankLength(strings: List<String>): Double =
        (strings
            .stream()
            .filter { it.isNotBlank() }
            .mapToInt(String::length)
            .sum()
            / strings.size.toDouble())
    /// end: streams
}

object Sequences3 {
    /// begin: consumingTwice
    fun averageNonBlankLength(strings: List<String>): Double =
        averageNonBlankLength(strings.asSequence())

    fun averageNonBlankLength(strings: Sequence<String>): Double =
        (strings
            .filter { it.isNotBlank() }
            .sumBy(String::length)
            / strings.count().toDouble())
    /// end: consumingTwice

    /// begin: iterator
    fun averageNonBlankLength(strings: Iterator<String>): Double =
        averageNonBlankLength(strings.asSequence())
    /// end: iterator
}

object Sequences4 {
    /// begin: peekCount
    fun averageNonBlankLength(strings: Sequence<String>): Double {
        var count = 0
        return (strings
            .onEach { count++ }
            .filter { it.isNotBlank() }
            .sumBy(String::length)
            / count.toDouble())
    }
    /// end: peekCount
}

object Sequences5 {
    /// begin: CountingSequence
    fun averageNonBlankLength(strings: Sequence<String>): Double =
        CountingSequence(strings).let { counter ->
            (counter
                .filter { it.isNotBlank() }
                .sumBy(String::length)
                / counter.count.toDouble())
        }

    class CountingSequence<T>(
        private val wrapped: Sequence<T>
    ) : Sequence<T> {
        var count = 0

        override fun iterator() =
            wrapped.onEach { count++ }.iterator()
    }
    /// end: CountingSequence
}

object Sequences6 {
    /// begin: sumBy
    fun averageNonBlankLength(strings: List<String>): Double =
        strings
            .map { if (it.isBlank()) 0 else it.length }
            .average()
    /// end: sumBy
}


object TranslatingList {
    /// begin: translatingList
    fun translatedWordsUntilSTOP(strings: List<String>): List<String> =
        strings
            .map { translate(it) }
            .takeWhile { !it.equals("STOP", ignoreCase = true) }
    /// end: translatingList
}

object TranslatingSequence {
    /// begin: translatingSequence
    fun translatedWordsUntilSTOP(strings: List<String>): List<String> =
        strings
            .asSequence()
            .map { translate(it) }
            .takeWhile { !it.equals("STOP", ignoreCase = true) }
            .toList()
    /// end: translatingSequence
}

private fun translate(word: String): String {
    return word
}

