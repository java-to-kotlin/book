package book

import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import java.io.StringWriter
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun main() {
    val inFile = File("/Users/duncan/Downloads/a337be3acd1c47b0e117f650/book.html")
    val cssFile = File("buildSrc/src/main/java/book/html.css")
    val outDir = File("./build/gdocs-import")

    render(inFile, cssFile, outDir)
}

fun render(inFile: File, cssFile: File, outDir: File) {
    val xml = inFile.readLines(Charsets.UTF_8).fixBrokenTags()
    val doc = xml.joinToString("\n").toDocument().fixupHeadings()
    val newContentLines = doc.rendered().lines().fixCodeIndent()

    outDir.parentFile.mkdirs()
    outDir.resolve("book.html").writeText(
        newContentLines.joinToString("\n")
    )
    cssFile.copyTo(
        outDir.resolve("theme/html/html.css").apply {
            parentFile.mkdirs()
        },
        overwrite = true
    )
}

private fun Document.fixupHeadings() = apply {
    renameAllElements("h6", "h7")
    renameAllElements("h5", "h6")
    renameAllElements("h4", "h5")
    renameAllElements("h3", "h4")
    renameAllElements("h2", "h3")
    renameNonChapterElements("h1", "h2")
}

private fun List<String>.fixBrokenTags(): List<String> = map { line ->
    when {
        line.trimStart().startsWith("<link ") -> line + "</link>"
        line.trimStart().startsWith("<meta ") -> line + "</meta>"
        line.contains("<img ") -> line.replace("<img (.*?)>".toRegex(), "<img $1 />")
        else -> line
    }
}

private fun String.toDocument(): Document =
    DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder()
        .parse(byteInputStream())

private fun Document.renameNonChapterElements(from: String, to: String) {
    getElementsByTagName(from).toList().forEach() {
        val parentTypeAttribute = it.parentNode.attributes.getNamedItem("data-type")?.nodeValue
        if (parentTypeAttribute != "chapter")
            renameNode(it, null, to)
    }
}

private fun Document.rendered(): String {
    val result = StringWriter()
    TransformerFactory.newInstance().newTransformer().transform(
        DOMSource(this), StreamResult(result)
    )
    return result.toString()
}

private fun List<String>.fixCodeIndent(): List<String> = sequence {
    val iterator = iterator()
    while (iterator.hasNext()) {
        val line = iterator.next()
        when {
            line.trimStart().startsWith("<pre data-code-language=") -> {
                yield(line.trimStart())
                yield(iterator.next().trimStart())
            }
            else -> yield(line)
        }
    }
}.toList()

private fun Document.renameAllElements(from: String, to: String) {
    getElementsByTagName(from).toList().forEach() {
        renameNode(it, null, to)
    }
}

private fun NodeList.toList(): List<Node> = List<Node>(this.length) { index ->
    this.item(index)
}
