package book

import com.natpryce.*
import java.io.File
import kotlin.text.RegexOption.DOT_MATCHES_ALL
import kotlin.text.RegexOption.MULTILINE

private val noLogging: (String) -> Unit = {}
private val log: (String) -> Unit = noLogging // ::println

data class SourceRoots(
    val workedExample: File,
    val digressionCode: File
)

private fun SourceRoots.sourceRootFor(version: String?): File = when (version) {
    null -> digressionCode
    else -> workedExample
}

fun processFiles(
    inputRoot: File,
    outputRoot: File,
    sourceRoots: SourceRoots,
    abortOnFailure: Boolean,
    kotlinVersion: String
) {
    inputRoot.walkTopDown()
        .filter { it.name.endsWith(".ad") }
        .forEach { file ->
            processFile(
                file,
                outputRoot.resolve(file.relativeTo(inputRoot)),
                sourceRoots,
                abortOnFailure,
                kotlinVersion
            )
        }
}

fun processFile(
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
    dest.also {
        it.parentFile.mkdirs()
    }.writeText(newText)
}

private fun lookupWithRoot(
    src: File,
    roots: SourceRoots,
    abortOnFailure: Boolean,
    kotlinVersion: String
) =
    fun(key: String): String {
        val (codeFile, tag) = key.parse(roots)
        if (!codeFile.exists) {
            val message =
                "${src.canonicalPath}:\n" +
                    "inserted file $codeFile not found\n" +
                    "(${codeFile.lines.recover { it.message }})"
            if (abortOnFailure) {
                error(message)
            } else {
                log(message)
                return message
            }
        }
        return FileSnippet(codeFile, tag, kotlinVersion).rendered()
    }

private fun String.parse(roots: SourceRoots): Pair<CodeFile, String?> {
    val (file, fragment) = this.trim().split("#").let { parts ->
        parts[0] to parts.getOrNull(1)
    }
    val (path, version) = file.split(":").let { parts ->
        parts.last() to parts.first().takeIf { parts.size == 2 }
    }
    return codeFileFor(roots, path, version) to fragment
}

private fun codeFileFor(roots: SourceRoots, path: String, version: String?): CodeFile =
    when (version) {
        null -> DiskFile(
            sourceRoot = roots.sourceRootFor(version),
            relativePath = path
        )
        else -> GitFile(
            sourceRoot = roots.sourceRootFor(version),
            relativePath = path, version = version
        )
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
