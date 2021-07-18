package book

import com.natpryce.onFailure

data class FileSnippet(
    val codeFile: CodeFile,
    val fragment: String?,
    val kotlinVersion: String
) {
    fun rendered() =
        listOf(
            "[source,$sourceType]",
            "----",
            codeLines().joinToString("\n"),
            "----",
            codeFile.toTag()
        ).filterNotNull().joinToString("\n")

    private fun codeLines() =
        codeFile.lines
            .onFailure { throw it.reason }
            .snipped(fragment, kotlinVersion)
            .also {
                if (it.isEmpty()) {
                    error("tag $fragment not found in $codeFile")
                }
            }

    private val sourceType = when (codeFile.extension) {
        "kt", "ktx",
        "kts" -> "kotlin"
        "java" -> "java"
        "gradle", "groovy" -> "groovy"
        else -> "text"
    }
}

private fun CodeFile.toTag(): String? =
    when (this) {
        is GitFile -> """
            ++++
            <div class="coderef">
                <a class="orm:hideurl" href="https://github.com/java-to-kotlin/code/blob/$version/$relativePath">
                    $version:$relativePath
                </a>
            </div>
            ++++""".trimIndent()
        else -> null
    }
