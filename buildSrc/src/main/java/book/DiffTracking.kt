package book

import java.io.File

class DiffTracking(
    asciiDocFile: File
) {
    private val linkUrl = "https://java-to-kotlin.dev/code-links.html"
    private val chapterNumber = asciiDocFile.name.split("-")[0].toIntOrNull() ?: 0
    private val versions = mutableListOf<GitFile>()

    fun record(codeFile: GitFile): String {
        versions.add(codeFile)
        return codeFile.toTag("$chapterNumber.${versions.size}")
    }

    private fun GitFile.toTag(exampleNumber: String): String = """
        ++++
        <div class="coderef">
            <a class="orm:hideurl" href="$linkUrl?ref=$exampleNumber">Example $exampleNumber [$version:$relativePath]</a> <a class="orm:hideurl print-hide" href="$linkUrl?diff=$exampleNumber">(diff)</a> 
        </div>
        ++++""".trimIndent()

    fun toJson(): List<String> {
        val previousVersionLookup = mutableMapOf<String, GitFile>()
        return versions
            .mapIndexed { index, gitFile ->
                jsonFor(index + 1, gitFile, previousVersionLookup[gitFile.relativePath]?.version)
                    .also {
                        previousVersionLookup[gitFile.relativePath] = gitFile
                    }
            }
    }

    private fun jsonFor(index: Int, gitFile: GitFile, previousVersion: String?) = """
        "$chapterNumber.$index" : { 
            "path" : ${quoted(gitFile.relativePath)}, 
            "previousVersion" : ${quoted(previousVersion)}, 
            "version" : ${quoted(gitFile.version)}                    
        }""".trimIndent()
}


fun Iterable<DiffTracking>.writeTo(file: File) {
    file.bufferedWriter().use { writer ->
        writer.append(
            this.flatMap { diffTracking -> diffTracking.toJson() }
                .joinToString(prefix = "{\n", separator = ",\n", postfix = "\n}")
        )
    }
}

private fun quoted(s: String?) = s?.let { '\"' + it + '\"' } ?: "null"