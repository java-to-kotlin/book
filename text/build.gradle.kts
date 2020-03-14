import kotlin.text.RegexOption.DOT_MATCHES_ALL
import kotlin.text.RegexOption.MULTILINE

tasks {
    create("build") {
        doFirst {
            processFiles(project.projectDir)
        }
    }
}

val root = project.projectDir

fun processFiles(dir: File) {
    dir.listFiles().filter { it.name.endsWith(".ad") }.forEach { file ->
        processFile(file, file)
    }
}

fun processFile(src: File, dest: File) {
    val text = src.readText()
    dest.writeText(expandCodeBlocks(text, ::lookup))
}

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

fun lookup(key: String): String {
    val file = root.resolve(key.trim())
    if (!file.isFile) error("File not found for $file")
    return FileSnippet(file).toString()
}

fun expandCodeBlocks(text: String, lookup: (String) -> String): String =
    expandedCodeBlockFinder.replace(text) { matchResult ->
        val lines = listOf(matchResult.groups["intro"]!!.value) +
            lookup(matchResult.groups["key"]!!.value) +
            matchResult.groups["outro"]!!.value
        lines.joinToString("\n")
    }

//language=RegExp
val expandedCodeBlockFinder = """^(?<intro>// \[start-insert\] <(?<key>.*?)>)(.*?)^(?<outro>// \[end-insert].*?)$""".toRegex(setOf(DOT_MATCHES_ALL, MULTILINE))

fun Iterable<String>.withoutPreamble(): List<String> = this
    .filter { !it.startsWith("import") && !it.startsWith("package") }
    .dropWhile { it.isEmpty() }
