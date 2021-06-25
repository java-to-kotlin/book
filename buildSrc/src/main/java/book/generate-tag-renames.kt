package book

import com.natpryce.onFailure
import java.io.File

val dir = File("../refactoring-to-kotlin-code")
val oddBranchNames = listOf("table-reader")
// can't do these in the batch with the rest for some reason

/**
 * Works out what numbers the checkin comments should have and creates a file containing the renames to apply to the git history.
 *
 * You can edit that file to fix anything else, and then run apply-tag-renames
 */
fun main() {
    val activeBranches = listActiveBranches()
    val lines = gitLog((activeBranches - oddBranchNames) - "new-baseline") + gitLog(oddBranchNames)

    val sections = lines
        .filter { it.startsWith("    ") }
        .mapNotNull { it.toComment() }
        .groupBy { it.prefix }
        .minus("projects")
        .mapValues { (key, value) ->
            value.sortedBy { it.number }
        }

    val renames = sections.values.flatMap { extractRenames(it) }
    writeRenamesToFile(renames, File("renames.tsv"))
}

private fun writeRenamesToFile(renames: List<Pair<String, String>>, to: File) {
    to.bufferedWriter().use { writer ->
        renames.forEach { (from, to) ->
            writer.appendln("$from\t$to")
        }
    }
}

private fun listActiveBranches() =
    "git show-ref --heads"
        .runCommand(workingDir = dir)
        .onFailure { throw it.reason }
        .lines()
        .filterNot { it.isBlank() }
        .map { it.split(' ')[1].replaceFirst("refs/heads/", "") }
        .filterNot { it == "dummy-master" || it.startsWith("attic") }

private fun gitLog(branchNames: List<String>) =
    "git log ${branchNames.joinToString(" ")}"
        .runCommand(workingDir = dir)
        .onFailure { throw it.reason }
        .lines()

private fun extractRenames(section: List<Comment>): List<Pair<String, String>> {
    val start = section.first().number.toInt()
    return section.mapIndexed { index, it ->
        val newIndex = index + start
        "${it.prefix}.${it.numberAsString}" to "${it.prefix}.${newIndex}"
    }
}

private fun String.toComment(): Comment? =
    when {
        !this.contains(":") -> null
        else -> {
            val (head, tail) = this.trim().split(":")
            val dotIndex = head.indexOf(".")
            val prefix = head.substring(0, dotIndex)
            val number = head.substring(dotIndex + 1).trim()
            Comment(prefix, number, tail)
        }
    }

private data class Comment(
    val prefix: String,
    val numberAsString: String,
    val text: String
) {
    val number = numberAsString.toDouble()
}