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
        data class Ellipsis(override val tags: Set<String>, val mute: Boolean, val prefix: String, val ellipsis: String) : Marker()
        data class Resume(override val tags: Set<String>) : Marker()
    }
}

val Ellipsis.replacementLine get() = prefix + ellipsis

val markerPattern =
    Pattern.compile("""(?<prefix>\s*)///\s*(?<directive>[a-z]+)\s*:\s*(?<tags>(?:\s|[a-zA-Z0-9_,])+)\s*(?:\[(?<replacement>[^\]]+)\]\s*)?""")

fun parseMarker(m: Matcher): Line.Marker? {
    val directive = m.group("directive")
    val tags = m.group("tags").split(",").map(String::trim).filterNot(String::isEmpty).toSet()
    return when (directive) {
        "begin" -> Begin(tags)
        "end" -> End(tags)
        "mute" -> Ellipsis(tags, true, m.group("prefix"), m.group("replacement") ?: "...")
        "note" -> Ellipsis(tags, false, m.group("prefix"), m.group("replacement"))
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
                    result.add(parsed.text.withHighlight(tagName))
                }
            is Begin ->
                if (tagName in parsed.tags) {
                    output = true
                }
            is End ->
                if (tagName in parsed.tags) {
                    output = false
                }
            is Ellipsis ->
                if (tagName in parsed.tags) {
                    result.add(parsed.replacementLine)
                    if (parsed.mute) output = false
                }
            is Resume ->
                if (tagName in parsed.tags) {
                    output = true
                }
        }
    }

    return result
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
/// note: foo [here is an example]
fun foo(): Int {
    val a = 10
    /// mute: foo [// it doesn't matter what happens in the rest of the function...]
    println("you should not see this")
    /// resume: foo
    return a
}
/// end: foo

*/
