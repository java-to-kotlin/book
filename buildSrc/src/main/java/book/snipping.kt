package book

import book.Line.Marker.*
import book.Line.Text

typealias TagCriteria = (String?) -> Boolean

data class Annotation(val text: String, val tags: Set<String>)

sealed class Line {
    data class Text(val text: String, val annotation: Annotation? = null) : Line()

    sealed class Marker : Line() {
        abstract val appliesToTag: TagCriteria

        data class Begin(override val appliesToTag: TagCriteria) : Marker()
        data class End(override val appliesToTag: TagCriteria) : Marker()
        data class Ellipsis(
            override val appliesToTag: TagCriteria,
            val mute: Boolean,
            val prefix: String,
            val ellipsis: String
        ) : Marker()

        data class Resume(override val appliesToTag: TagCriteria) : Marker()
    }
}

val Ellipsis.replacementLine get() = prefix + ellipsis

private val markerSyntax =
    """\s*(?<directive>[a-z]+)(\s*:\s*(?<tags>(?:\s|[a-zA-Z0-9_,])+))?\s*(?:\[(?<replacement>[^]]+)]\s*)?""".toRegex()

private val markedLinePattern =
    """^(?<indent>\s*)///""".toRegex()

private val markedLineSyntax =
    """${markedLinePattern}${markerSyntax}$""".toRegex()

private val annotationPattern = """///\s*${markerSyntax}$""".toRegex()

fun parseLine(line: String): Line? {
    return when {
        markedLinePattern.containsMatchIn(line) -> parseMarkedLine(
            markedLineSyntax.matchEntire(line)
                ?: error("cannot parse line marker: \"$line\"")
        )
        else -> Text(line)
    }
}

fun parseMarkedLine(m: MatchResult): Line.Marker? {
    val parts = m.groups
    val directive = parseDirective(parts)
    val tags = parseTags(m)
    return when (directive) {
        "begin" -> Begin(tags)
        "end" -> End(tags)
        "mute" -> Ellipsis(tags, true, parts["indent"]?.value ?: "", parts["replacement"]?.value ?: "...")
        "note" -> Ellipsis(tags, false, parts["indent"]?.value ?: "", parts["replacement"]?.value ?: "...")
        "resume" -> Resume(tags)
        else -> null
    }
}

private fun parseDirective(parts: MatchGroupCollection) =
    parts["directive"]?.value ?: error("no sippet directive")


private fun parseTags(parts: MatchResult): TagCriteria {
    return when (val tagsStr = parts.groups["tags"]?.value) {
        null -> { _ -> true }
        else -> tagsStr
            .split(",")
            .map(String::trim)
            .filterNot(String::isEmpty)
            .toSet()
            .let { parsedTags ->
                fun(tag: String?) = tag != null && parsedTags.contains(tag)
            }
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
                if (parsed.appliesToTag(tagName)) {
                    output = true
                }
            is End ->
                if (parsed.appliesToTag(tagName)) {
                    output = false
                }
            is Ellipsis ->
                if (parsed.appliesToTag(tagName)) {
                    result.add(parsed.replacementLine)
                    if (parsed.mute) output = false
                }
            is Resume ->
                if (parsed.appliesToTag(tagName)) {
                    output = true
                }
        }
    }

    return result
}



private fun String.withHighlight(tagName: String?) =
    annotationPattern.replace(this) { matchResult ->
        val directive = matchResult.groups["directive"]?.value
        val appliesToTag = parseTags(matchResult)
        when {
            appliesToTag(tagName) -> "/// $directive"
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
