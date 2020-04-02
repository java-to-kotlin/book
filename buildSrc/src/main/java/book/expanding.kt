package book

import java.io.File
import kotlin.text.RegexOption.DOT_MATCHES_ALL
import kotlin.text.RegexOption.MULTILINE

private const val abortOnFailure = true

fun processFiles(dir: File, srcRoot: File) {
    dir.walkTopDown().filter { it.name.endsWith(".ad") }.forEach { file ->
        processFile(file, file, srcRoot)
    }
}

private fun processFile(src: File, dest: File, srcRoot: File) {
    val text = src.readText()
    val newText = expandCodeBlocks(text, lookupWithRoot(srcRoot))
    if (newText != text)
        dest.writeText(newText)
}

private fun lookupWithRoot(dir: File) =
    fun(key: String): String {
        val (filename, fragment) = key.parse()
        val file = dir.resolve(filename)
        if (!file.isFile) {
            val message = "File not found for $file"
            if (abortOnFailure) {
                error(message)
            } else {
                return message
            }
        }
        return FileSnippet(file, fragment).toString()
    }

private fun String.parse(): Pair<String, String?> = this
    .trim()
    .split("#").let { it.first() to (if (it.size == 2) it[1] else null) }

private fun expandCodeBlocks(text: String, lookup: (String) -> String): String =
    expandedCodeBlockFinder.replace(text) { matchResult ->
        val lines = listOf(matchResult.groups["intro"]!!.value) +
            lookup(matchResult.groups["key"]!!.value) +
            matchResult.groups["outro"]!!.value
        lines.joinToString("\n")
    }

//language=RegExp
private val expandedCodeBlockFinder =
    """^(?<intro>// begin-insert: (?<key>.*?)$)(.*?)^(?<outro>// end-insert.*?)$"""
        .toRegex(setOf(DOT_MATCHES_ALL, MULTILINE))

class FileSnippet(private val file: File, private val fragment: String?) {
    override fun toString() = (listOf(
        "[source,$sourceType]",
        "----"
    ) + filter(file.readLines()) +
        "----").joinToString("\n")

    private val sourceType = when (file.extension) {
        "kt","ktx","kts" -> "kotlin"
        "java" -> "java"
        "gradle","groovy" -> "groovy"
        else -> "text"
    }

    private fun filter(lines: List<String>) = lines.withoutPreamble().snipped(fragment)
}

private fun Iterable<String>.withoutPreamble(): List<String> = this
    .filter { !it.startsWith("import") && !it.startsWith("package") }
    .dropWhile { it.isEmpty() }
