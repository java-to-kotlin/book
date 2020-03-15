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
    dest.writeText(expandCodeBlocks(text, lookupWithRoot(srcRoot)))
}

private fun lookupWithRoot(dir: File) =
    fun(key: String): String {
        val file = dir.resolve(key.trim())
        if (!file.isFile) {
            val message = "File not found for $file"
            if (abortOnFailure) {
                error(message)
            } else {
                return message
            }
        }
        return FileSnippet(file).toString()
    }

private fun expandCodeBlocks(text: String, lookup: (String) -> String): String =
    expandedCodeBlockFinder.replace(text) { matchResult ->
        val lines = listOf(matchResult.groups["intro"]!!.value) +
            lookup(matchResult.groups["key"]!!.value) +
            matchResult.groups["outro"]!!.value
        lines.joinToString("\n")
    }

//language=RegExp
private val expandedCodeBlockFinder =
    """^(?<intro>// \[start-insert\] <(?<key>.*?)>)(.*?)^(?<outro>// \[end-insert].*?)$"""
        .toRegex(setOf(DOT_MATCHES_ALL, MULTILINE))

class FileSnippet(private val file: File) {
    override fun toString() = (listOf(
        "[source,$sourceType]",
        "----"
    ) + file.readLines().withoutPreamble() +
        "----").joinToString("\n")

    private val sourceType = when (file.extension) {
        "kt" -> "kotlin"
        "java" -> "java"
        else -> "text"
    }
}

private fun Iterable<String>.withoutPreamble(): List<String> = this
    .filter { !it.startsWith("import") && !it.startsWith("package") }
    .dropWhile { it.isEmpty() }
