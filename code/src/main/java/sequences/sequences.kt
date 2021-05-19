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
    class CountingSequence<T>(
        private val wrapped: Sequence<T>
    ) : Sequence<T> {
        var count = 0
        override fun iterator() =
            wrapped.onEach { count++ }.iterator()
    }

    fun averageNonBlankLength(strings: Sequence<String>): Double {
        val countingSequence = CountingSequence(strings)
        return (countingSequence
                .filter { it.isNotBlank() }
                .sumBy(String::length)
                / countingSequence.count.toDouble())
    }
    /// end: CountingSequence
}

object Sequences6 {
    /// begin: average
    fun averageNonBlankLength(strings: Sequence<String>): Double =
        strings
            .map { if (it.isBlank()) 0 else it.length }
            .average()
    /// end: average
}

object Sequences7 {
    /// begin: averageBy
    inline fun <T> Sequence<T>.averageBy(selector: (T) -> Int): Double {
        var sum: Double = 0.0
        var count: Int = 0
        for (element in this) {
            sum += selector(element)
            checkCountOverflow(++count)
        }
        return if (count == 0) Double.NaN else sum / count
    }
    /// end: averageBy

    /// begin: useAverageBy
    fun averageNonBlankLength(strings: Sequence<String>): Double =
        strings.averageBy {
            if (it.isBlank()) 0 else it.length
        }
    /// end: useAverageBy

    fun checkCountOverflow(count: Int): Int {
        when {
            count < 0 -> throw ArithmeticException("Count overflow has happened.")
            else -> return count
        }
    }
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

