package book

import book.Line.Marker.*
import book.Line.Text
import java.io.File

typealias TagCriteria = (String?) -> Boolean

data class Annotation(val text: String, val tags: Set<String>)

sealed class Line {
    data class Text(val text: String, val annotation: Annotation? = null) : Line()

    sealed class Marker : Line() {
        abstract val appliesToTag: TagCriteria

        data class Begin(
            override val appliesToTag: TagCriteria,
            val indent: String
        ) : Marker()

        data class End(
            override val appliesToTag: TagCriteria
        ) : Marker()

        data class Ellipsis(
            override val appliesToTag: TagCriteria,
            val mute: Boolean,
            val prefix: String,
            val ellipsis: String
        ) : Marker()

        data class Resume(
            override val appliesToTag: TagCriteria
        ) : Marker()
    }
}

val Ellipsis.replacementLine get() = prefix + ellipsis

fun Text.isBlank() = text.isBlank()

private fun Text.isPackageStatement() = text.startsWith("package")

private fun Text.isImportStatement() = text.startsWith("import")


private val negateTagOperator = "!"

private val tagSpec = """$negateTagOperator?[a-zA-Z0-9_]+""".toRegex()

private fun commaSeparated(element: Regex) =
    """$element(\s*,\s*$element)*""".toRegex()

private val markerSyntax =
    """\s*(?<directive>[a-z]+)(\s*:\s*(?<tags>${commaSeparated(tagSpec)}))?\s*(?:\[(?<replacement>[^]]*)]\s*)?""".toRegex()

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
        "begin" -> Begin(tags, parts.indent())
        "end" -> End(tags)
        "mute" -> Ellipsis(tags, true, parts.indent(), parts.replacement())
        "note" -> Ellipsis(tags, false, parts.indent(), parts.replacement())
        "resume" -> Resume(tags)
        else -> error("unsupported directive: $directive")
    }
}

private fun MatchGroupCollection.replacement() =
    get("replacement")?.value ?: "..."

private fun MatchGroupCollection.indent() =
    get("indent")?.value ?: ""

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

fun Iterable<String>.snipped(tagName: String?, kotlinVersion: String): List<String> {
    val result = mutableListOf<String>()

    var currentRegionIsSelected = tagName == null
    var inPreamble = true
    var exdent = ""

    fun shouldSkip(line: Text): Boolean {
        return line.isPackageStatement() && tagName == null
            || line.isImportStatement() && tagName == null
            || line.isBlank() && inPreamble
    }

    map(::parseLine).forEach { line ->
        when (line) {
            is Text ->
                if (currentRegionIsSelected && !shouldSkip(line)) {
                    result.add(line
                        .highlightedText(tagName)
                        .removePrefix(exdent)
                        .replace("\$KOTLIN_VERSION", kotlinVersion)
                    )
                    inPreamble = false
                }
            is Begin ->
                if (line.appliesToTag(tagName)) {
                    currentRegionIsSelected = true
                    exdent = line.indent
                }
            is End ->
                if (line.appliesToTag(tagName)) {
                    currentRegionIsSelected = false
                    exdent = ""
                }
            is Ellipsis ->
                if (currentRegionIsSelected && line.appliesToTag(tagName)) {
                    if (line.ellipsis.isNotEmpty()) result.add(line.replacementLine)
                    if (line.mute) currentRegionIsSelected = false
                }
            is Resume ->
                if (line.appliesToTag(tagName)) {
                    currentRegionIsSelected = true
                }
        }
    }

    return result
        .dropLastWhile { it.isBlank() }
        .map { it.normaliseEllipsisComment() }
        .map { it.replace("SOME_CODE()", "...") }
}

private val ellipsisCommentPattern = Regex("""^(\s*)(//\s*)(\.\.\..*)$""")

private fun String.normaliseEllipsisComment() =
    ellipsisCommentPattern.matchEntire(this)?.groupValues
        ?.let { it[1] + it[3] }
        ?: this

val kotlinVersion by lazy {
    File(".kotlin-version").readText().trim()
}

private fun Text.highlightedText(tagName: String?) =
    annotationPattern.replace(text) { matchResult ->
        val directive = matchResult.groups["directive"]?.value
        val replacement = matchResult.groups["replacement"]?.value ?: ""
        val appliesToTag = parseTags(matchResult.groups)

        if (appliesToTag(tagName)) {
            when (directive) {
                "note" -> replacement.normaliseEllipsisComment() // Hack to avoid lots of code changes
                "change" -> "/// |"
                "insert" -> "/// +"
                "delete" -> "/// -"
                else -> error("unknown highlight directive: $directive")
            }
        } else ""
    }

