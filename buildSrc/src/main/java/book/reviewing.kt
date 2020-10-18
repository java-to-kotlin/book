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
    renameAllElements("h4", "h5")
    renameAllElements("h3", "h4")
    renameAllElements("h2", "h3")
    renameNonTopLevelElements("h1", "h2")
}

private val imgRegex = """<img src="text/src/callouts/(.*?)" (.*?)>""".toRegex()

private fun List<String>.fixBrokenTags(): List<String> = map { line ->
    when {
        line.trimStart().startsWith("<link ") -> line + "</link>"
        line.trimStart().startsWith("<meta ") -> line + "</meta>"
        line.contains("<img ") -> {
            line.replace(
                imgRegex,
                """<img src="http://oneeyedmen.com/book/callouts/$1" $2/>""")
        }
        else -> line
    }
}

private fun String.toDocument(): Document =
    DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder()
        .parse(byteInputStream())

private fun Document.renameNonTopLevelElements(from: String, to: String) {
    val topLevelSections = listOf("chapter", "copyright-page", "titlepage", "preface")
    getElementsByTagName(from).toList().forEach() {
        val parentTypeAttribute = it.parentNode.attributes.getNamedItem("data-type")?.nodeValue
        if (!(parentTypeAttribute in topLevelSections))
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
    var skipWindow = false
    windowed(2, 1).forEach { window: List<String> ->
        require(window.size == 2)
        val first = window.first()
        val second = window.last()
        if (skipWindow) {
            skipWindow = false
        } else {
            when {
                // fix first line of pre being indented
                first.trimStart().startsWith("<pre data-code-language=") -> {
                    yield(first.trimStart())
                    yield(second.trimStart())
                    skipWindow = true
                }
                // fix last line of pre being blank indented
                first.endsWith("</code>") && second.trim() == "</pre>"-> {
                    yield(first + second.trim())
                    skipWindow = true
                }
                else -> {
                    yield(first)
                }
            }
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
