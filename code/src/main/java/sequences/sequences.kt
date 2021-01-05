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



