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

    val delme = listOf<String>().map { it.length }.toSet()
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

