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

private val negateTagOperator = "!"

private val tagSpec = """$negateTagOperator?[a-zA-Z0-9_]+""".toRegex()

private fun commaSeparated(element: Regex) =
    """$element(\s*,\s*$element)*""".toRegex()

private val markerSyntax =
    """\s*(?<directive>[a-z]+)(\s*:\s*(?<tags>${commaSeparated(tagSpec)}))?\s*(?:\[(?<replacement>[^]]+)]\s*)?""".toRegex()

private val markedLinePattern =
    """^(?<indent>\s*)///""".toRegex()

private val markedLineSyntax =
    """${markedLinePattern}${markerSyntax}$""".toRegex()

private val annotationPattern = """///\s*${markerSyntax}$""".toRegex()

fun parseLine(line: String): Line {
    return when {
        line.isMarker() -> parseMarkedLine(
            markedLineSyntax.matchEntire(line)
                ?: error("cannot parse line marker: \"$line\"")
        )
        else -> Text(line)
    }
}

private fun String.isMarker() = markedLinePattern.containsMatchIn(this)

fun parseMarkedLine(m: MatchResult): Line.Marker {
    val parts = m.groups
    val tags = parseTags(parts)

    return when (val directive = parseDirective(parts)) {
        "begin" -> Begin(tags)
        "end" -> End(tags)
        "mute" -> Ellipsis(tags, true, parts["indent"]?.value ?: "", parts["replacement"]?.value ?: "...")
        "note" -> Ellipsis(tags, false, parts["indent"]?.value ?: "", parts["replacement"]?.value ?: "...")
        "resume" -> Resume(tags)
        else -> error("unsupported directive: $directive")
    }
}

private fun parseDirective(parts: MatchGroupCollection) =
    parts["directive"]?.value ?: error("no snippet directive")



private fun parseTags(parts: MatchGroupCollection): TagCriteria {
    return when (val tagsStr = parts["tags"]?.value) {
        null -> { _ -> true }
        else -> {
            val tagSpecs = tagsStr
                .split(",")
                .map(String::trim)
                .filterNot(String::isEmpty)

            val includedTags = tagSpecs.filter { !it.startsWith(negateTagOperator) }.toSet()
            val excludedTags = tagSpecs.filter { it.startsWith(negateTagOperator) }.map { it.drop(1) }.toSet()

            return fun(tag: String?) =
                when (tag) {
                    null -> includedTags.isEmpty()
                    else -> tag in includedTags && tag !in excludedTags
                }
        }
    }
}

fun Iterable<String>.snipped(tagName: String?): List<String> {
    val result = mutableListOf<String>()
    var output = tagName == null
    this.forEach { line ->
        when (val parsed = parseLine(line)) {
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
        val replacement = matchResult.groups["replacement"]?.value ?: ""
        val appliesToTag = parseTags(matchResult.groups)

        if (appliesToTag(tagName)) {
            when (directive) {
                "note" -> replacement
                "change" -> "/// |"
                "insert" -> "/// +"
                "delete" -> "/// -"
                else -> error("unknown highlight directive: $directive")
            }
        } else ""
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
