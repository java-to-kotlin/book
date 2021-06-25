package book

import com.natpryce.onFailure
import java.io.File


/**
 * Works out what numbers the checkin comments should have, renames them in the book source,
 * and creates a file containing the renames to apply to the git history.
 */
fun main() {
    val renames = readRenamesFromFile(File("renames.tsv"))
    writeRenamesToAsciiDoc(renames)
    println("Now run rename-commits.sh")
}

private fun readRenamesFromFile(file: File): List<Pair<String, String>> =
    file.readLines().map { line ->
        line.split('\t').let { bits ->
            bits[0] to bits[1]
        }
    }


fun writeRenamesToAsciiDoc(renames: List<Pair<String, String>>) {
    File("./text/src/chapters").listFiles().forEach { file ->
        file.writeText(
            file.readLines()
                .replaceAll(renames)
                .joinToString("\n", postfix = "\n")
        )
    }
}

private fun List<String>.replaceAll(renames: List<Pair<String, String>>): List<String> =
    map { line ->
        renames.fold(line) { acc, (from, to) ->
            if (acc == line)
                acc.replace(
                    "tags/$from:",
                    "tags/$to:"
                )
            else acc
        }
    }



