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
        data class Resume(override val tags: Set<String>) : Marker()
    }
}

val Mute.replacementLine get() = indent + replacement


val markerPattern = Pattern.compile("""(?<indent>\s*)///\s*(?<directive>[a-z]+)\s*:\s*(?<tags>(?:\s|[a-zA-Z_,])+)\s*(?:\[(?<replacement>[^\]]+)\]\s*)?""")

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

fun Iterable<String>.snipped(name: String?): List<String> {
    val result = mutableListOf<String>()
    var output = name == null
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
                if (name in parsed.tags) {
                    output = true
                }
            is End ->
                if (name in parsed.tags) {
                    output = false
                }
            is Mute ->
                if (name in parsed.tags) {
                    result.add(parsed.replacementLine)
                    output = false
                }
            is Resume ->
                if (name in parsed.tags) {
                    output = true
                }
        }
    }
    return result
}

/*
/// begin: foo
fun foo(): Int {
    val a = 10
    /// mute: foo [// it doesn't matter what happens in the rest of the function...]
    println("you should not see this")
    /// resume: foo
    return a
}
/// end: foo

*/
