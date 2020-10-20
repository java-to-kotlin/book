package book

import com.natpryce.*
import java.io.File
import kotlin.text.RegexOption.DOT_MATCHES_ALL
import kotlin.text.RegexOption.MULTILINE

private val noLogging: (String) -> Unit = {}
private val log: (String) -> Unit = noLogging // ::println


data class VersionedFile(
    val sourceRoot: File,
    val relativePath: String,
    val version: String?
) {
    val file = sourceRoot.resolve(relativePath)

    override fun toString() =
        (version?.let { "$it:" } ?: "") + sourceRoot.resolve(relativePath).canonicalPath

    val linesOrError: Result<List<String>, Exception> by lazy {
        when (version) {
            null -> readUnversioned()
            else -> readVersioned()
        }
    }

    val lines: List<String>
        get() =
            linesOrError.recover { throw it }

    private fun readUnversioned() = resultOf {
        log("Reading $file")
        file.readLines()
    }

    private fun readVersioned(): Result<List<String>, Exception> {
        log("Reading $file")
        return "git show $version:${relativePath}"
            .runCommand(workingDir = sourceRoot)
            .map { it.lines() }
    }

    fun exists(): Boolean = linesOrError is Success
}

private data class SourceRoots(
    val workedExample: File,
    val digressionCode: File
)

private fun SourceRoots.sourceRootFor(version: String?): File = when (version) {
    null -> digressionCode
    else -> workedExample
}

fun processFiles(
    textRoot: File,
    workedExampleSrcRoot: File,
    digressionSrcRoot: File,
    abortOnFailure: Boolean,
    kotlinVersion: String
) {
    processFiles(
        textRoot,
        SourceRoots(workedExampleSrcRoot, digressionSrcRoot),
        abortOnFailure,
        kotlinVersion
    )
}

private fun processFiles(textRoot: File, sourceRoots: SourceRoots, abortOnFailure: Boolean, kotlinVersion: String) {
    textRoot.walkTopDown()
        .filter { it.name.endsWith(".ad") }
        .forEach { file ->
            processFile(file, file, sourceRoots, abortOnFailure, kotlinVersion)
        }
}

private fun processFile(
    src: File,
    dest: File,
    roots: SourceRoots,
    abortOnFailure: Boolean,
    kotlinVersion: String
) {
    log("Processing $src")
    val text = src.readText()
    val newText = expandCodeBlocks(
        text,
        lookupWithRoot(src, roots, abortOnFailure, kotlinVersion)
    )
    if (newText != text)
        dest.writeText(newText)
}

private fun lookupWithRoot(src: File, roots: SourceRoots, abortOnFailure: Boolean, kotlinVersion: String) =
    fun(key: String): String {
        val (versionedFile, tag) = key.parse(roots)
        if (!versionedFile.exists()) {
            val message = "${src.canonicalPath}: inserted file $versionedFile not found (${versionedFile.linesOrError.recover { it.message }})"
            if (abortOnFailure) {
                error(message)
            } else {
                log(message)
                return message
            }
        }
        return FileSnippet(versionedFile, tag, kotlinVersion).rendered()
    }

private fun String.parse(roots: SourceRoots): Pair<VersionedFile, String?> {
    val (file, fragment) = this.trim().split("#").let { parts -> parts[0] to parts.getOrNull(1) }
    val (path, version) = file.split(":").let { parts -> parts.last() to parts.first().takeIf { parts.size == 2 } }
    val versionedFile = VersionedFile(
        sourceRoot = roots.sourceRootFor(version),
        relativePath = path,
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

data class FileSnippet(val versionedFile: VersionedFile, val fragment: String?, val kotlinVersion: String) {
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
        lines
            .snipped(fragment, kotlinVersion)
            .also {
                if (it.isEmpty()) {
                    error("tag $fragment not found in $versionedFile")
                }
            }
}


fun <R> resultOf(f: () -> R): Result<R, Exception> =
    try {
        Success(f())
    } catch (e: Exception) {
        Failure(e)
    }