package book

import book.Line.Marker.*
import book.Line.Text
import java.util.regex.Matcher
import java.util.regex.Pattern


sealed class Line {
    data class Text(val text: String) : Line()

    sealed class Marker : Line() {
        abstract val tags: Set<String>

        data class Begin(override val tags: Set<String>) : Marker()
        data class End(override val tags: Set<String>) : Marker()
        data class Mute(override val tags: Set<String>, val indent: String, val replacement: String) : Marker()
        data class Insert(override val tags: Set<String>, val indent: String, val replacement: String) : Marker()
        data class Resume(override val tags: Set<String>) : Marker()
    }
}

val Mute.replacementLine get() = indent + replacement
val Insert.replacementLine get() = indent + replacement


val markerPattern =
    Pattern.compile("""(?<indent>\s*)///\s*(?<directive>[a-z]+)\s*:\s*(?<tags>(?:\s|[a-zA-Z_,])+)\s*(?:\[(?<replacement>[^\]]+)\]\s*)?""")

fun warn(s: String) {
    System.err.println(s)
}

fun parseMarker(m: Matcher): Line.Marker? {
    val directive = m.group("directive")
    val tags = m.group("tags").split(",").map(String::trim).filterNot(String::isEmpty).toSet()
    return when (directive) {
        "begin" -> Begin(tags)
        "end" -> End(tags)
        "mute" -> Mute(tags, m.group("indent"), m.group("replacement") ?: "...")
        "insert" -> Insert(tags, m.group("indent"), m.group("replacement"))
        "resume" -> Resume(tags)
        else -> null
    }
}

fun parseLine(line: String): Line? {
    val m = markerPattern.matcher(line)
    return when {
        m.matches() -> parseMarker(m)
        else -> Text(line)
    }
}

fun Iterable<String>.snipped(tagName: String?): List<String> {
    val result = mutableListOf<String>()
    var output = tagName == null
    this.forEach { line ->
        when (val parsed = parseLine(line)) {
            null -> {
                System.err.println("unable to parse marker from line: $line")
            }
            is Text ->
                if (output) {
                    result.add(parsed.text)
                }
            is Begin ->
                if (tagName in parsed.tags) {
                    output = true
                }
            is End ->
                if (tagName in parsed.tags) {
                    output = false
                }
            is Mute ->
                if (tagName in parsed.tags) {
                    result.add(parsed.replacementLine)
                    output = false
                }
            is Resume ->
                if (tagName in parsed.tags) {
                    output = true
                }
            is Insert ->
                if (tagName in parsed.tags) {
                    result.add(parsed.replacementLine)
                }
        }
    }
    return result.withHighlights(tagName)
}

private fun List<String>.withHighlights(tagName: String?) = this.map { line ->
    line.withHighlight(tagName)
}

private val highlightPattern = """///\s*(?<type>change|insert|delete)?(:\s*)(?<tag>.*)$""".toRegex()

private fun String.withHighlight(tagName: String?) =
    highlightPattern.replace(this) { matchResult ->
        val type = matchResult.groups["type"]?.value
        val tagValue = matchResult.groups["tag"]?.value
        when {
            tagValue.isNullOrBlank() -> "/// $type"
            tagValue == tagName -> "/// $type"
            else -> ""
        }
    }


/*
/// begin: foo
/// insert: foo [here is an example]
fun foo(): Int {
    val a = 10
    /// mute: foo [// it doesn't matter what happens in the rest of the function...]
    println("you should not see this")
    /// resume: foo
    return a
}
/// end: foo

*/
