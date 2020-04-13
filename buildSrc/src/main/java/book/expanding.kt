package book

import com.natpryce.*
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.text.RegexOption.DOT_MATCHES_ALL
import kotlin.text.RegexOption.MULTILINE

private const val abortOnFailure = false

private data class SourceRoots(
    val workedExample: File,
    val digressionCode: File
)

private fun SourceRoots.sourceRootFor(version: String?): File {
    return when (version) {
        null -> digressionCode
        else -> workedExample
    }
}

fun processFiles(dir: File, workedExampleSrcRoot: File, digressionSrcRoot: File) {
    processFiles(dir, SourceRoots(workedExampleSrcRoot, digressionSrcRoot))
}

private fun processFiles(dir: File, sourceRoots: SourceRoots) {
    dir.walkTopDown().filter { it.name.endsWith(".ad") }.forEach { file ->
        processFile(file, file, sourceRoots)
    }
}

private fun processFile(src: File, dest: File, srcRoot: SourceRoots) {
    val text = src.readText()
    val newText = expandCodeBlocks(text, lookupWithRoot(srcRoot))
    if (newText != text)
        dest.writeText(newText)
}

private fun lookupWithRoot(sourceRoots: SourceRoots) =
    fun(key: String): String {
        val (versionedFile, tag) = key.parse(sourceRoots)
        if (!versionedFile.exists()) {
            val message = "File not found for $versionedFile (${versionedFile._lines.recover { it.message }})"
            if (abortOnFailure) {
                error(message)
            } else {
                return message
            }
        }
        return FileSnippet(versionedFile, tag).rendered()
    }

private fun String.parse(sourceRoots: SourceRoots): Pair<VersionedFile, String?> {
    val (file, fragment) = this.trim().split("#").let { parts -> parts[0] to parts.getOrNull(1) }
    val (path, version) = file.split(":").let { parts -> parts.last() to parts.first().takeIf { parts.size == 2 } }

    val versionedFile = VersionedFile(
        file = sourceRoots.sourceRootFor(version).resolve(path).canonicalFile,
        version = version
    )
    return versionedFile to fragment
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

data class FileSnippet(val versionedFile: VersionedFile, val fragment: String?) {
    fun rendered() = (listOf(
        "[source,$sourceType]",
        "----"
    ) + filter(versionedFile.lines) +
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
                    error("$versionedFile is empty after filtering with $fragment")
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

    val _lines: Result<List<String>, Exception> by lazy {
        when (version) {
            null -> resultOf { file.readLines() }
            else -> readVersioned(file, version)
        }
    }

    val lines: List<String> get() = _lines.recover { throw it }

    private fun readVersioned(file: File, version: String): Result<List<String>, Exception> =
        "git show $version:./${file.name}".runCommand(workingDir = file.parentFile).map { it.lines() }

    fun exists(): Boolean = _lines is Success
}

fun String.runCommand(
    workingDir: File = File("."),
    timeoutAmount: Long = 2,
    timeoutUnit: TimeUnit = TimeUnit.SECONDS
): Result<String, Exception> = resultOf {
    val completedProcess = ProcessBuilder(split("\\s".toRegex()))
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start().apply { waitFor(timeoutAmount, timeoutUnit) }
    if (completedProcess.exitValue() == 0)
        completedProcess.inputStream.bufferedReader().readText()
    else
        error(completedProcess.errorStream.bufferedReader().readText())
}

fun <R> resultOf(f: () -> R): Result<R, Exception> =
    try {
        Success(f())
    } catch (e: Exception) {
        Failure(e)
    }