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

    @JvmStatic
    fun main(args: Array<String>) {
        println(averageNonBlankLength(emptyList()))
        println(averageNonBlankLength(listOf("     ", "1")))
    }
}

object Sequences3 {
    /// begin: takesSequence
    fun averageNonBlankLength(strings: Sequence<String>): Double {
        var size = 0
        return (strings
            .onEachIndexed { i, _ -> size = i + 1 }
            .filter { it.isNotBlank() }
            .sumBy(String::length)
            / size.toDouble())
    }
    /// end: takesSequence

    @JvmStatic
    fun main(args: Array<String>) {
        println(averageNonBlankLength(emptySequence()))
        println(averageNonBlankLength(sequenceOf("     ", "1")))
    }
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



