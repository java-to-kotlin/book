package book

import book.Line
import book.Line.Marker.Begin
import book.Line.Marker.End
import book.Line.Marker.Mute
import book.Line.Marker.Resume
import book.Line.Text
import java.io.File
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


//val snipName = args[0]
//var output = false
//
//File(args[1]).useLines { lines ->
//    lines.forEach { line ->
//        val parsed = parseLine(line)
//
//        when (parsed) {
//            null -> {
//                System.err.println("unable to parse marker from line: $line")
//            }
//            is Text ->
//                if (output) {
//                    println(parsed.text)
//                }
//            is Begin ->
//                if (snipName in parsed.tags) {
//                    output = true
//                }
//            is End ->
//                if (snipName in parsed.tags) {
//                    output = false
//                }
//            is Mute ->
//                if (snipName in parsed.tags) {
//                    println(parsed.replacementLine)
//                    output = false
//                }
//            is Resume ->
//                if (snipName in parsed.tags) {
//                    output = true
//                }
//        }
//    }
//}
