package book

import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.text.RegexOption.DOT_MATCHES_ALL
import kotlin.text.RegexOption.MULTILINE

private const val abortOnFailure = false

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
        val (versionedFile, tag) = key.parse(dir)
        if (!versionedFile.exists()) {
            val message = "File not found for $versionedFile"
            if (abortOnFailure) {
                error(message)
            } else {
                return message
            }
        }
        return FileSnippet(versionedFile, tag).toString()
    }

private fun String.parse(rootDir: File): Pair<VersionedFile, String?> {
    val (file, fragment) = this.trim().split("#").let { it.first() to (if (it.size == 2) it[1] else null) }
    val (path, version) = file.split(":").let { it.last() to (if (it.size == 2) it.first() else null) }
    return VersionedFile(file = rootDir.resolve(path).canonicalFile, version = version) to fragment
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
    """^(?<intro>// begin-insert: (?<key>.*?)$)(.*?)^(?<outro>// end-insert.*?)$"""
        .toRegex(setOf(DOT_MATCHES_ALL, MULTILINE))

class FileSnippet(val versionedFile: VersionedFile, val fragment: String?) {
    override fun toString() = (listOf(
        "[source,$sourceType]",
        "----"
    ) + filter(versionedFile.readLines()) +
        "----").joinToString("\n")

    private val sourceType = when (versionedFile.file.extension) {
        "kt", "ktx", "kts" -> "kotlin"
        "java" -> "java"
        "gradle", "groovy" -> "groovy"
        else -> "text"
    }

    private fun filter(lines: List<String>) =
        lines.withoutPreamble()
            .snipped(fragment)
            .also {
                if (it.isEmpty()) {
                    error("tag $fragment not found in file $versionedFile")
                }
            }
}

private fun Iterable<String>.withoutPreamble(): List<String> = this
    .filter { !it.startsWith("import") && !it.startsWith("package") }
    .dropWhile { it.isEmpty() }

data class VersionedFile(
    val file: File,
    val version: String?
) {
    fun readLines(): List<String> =
        when (version) {
            null -> file.readLines()
            else -> readVersioned(file, version)
        }

    private fun readVersioned(file: File, version: String): List<String> =
        "git show $version:./${file.name}"
            .also(::println)
    .runCommand(workingDir = file.parentFile)?.lines() ?: listOf("UNKNOWN")

    fun exists(): Boolean = file.isFile
}

fun String.runCommand(
    workingDir: File = File("."),
    timeoutAmount: Long = 2,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS
): String? = try {
    ProcessBuilder(split("\\s".toRegex()))
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start().apply { waitFor(timeoutAmount, timeoutUnit) }
        .inputStream.bufferedReader().readText()
} catch (e: java.io.IOException) {
    e.printStackTrace()
    null
}