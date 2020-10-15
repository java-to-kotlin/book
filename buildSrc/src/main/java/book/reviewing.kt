package book

import org.w3c.dom.Document
import org.w3c.dom.NamedNodeMap
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun main() {
    val f = File("/Users/duncan/Downloads/3c7d4023e8a6f5c00efc53a6/content.html")
//    val t = Tidy().apply {
//        xhtml = true
//    }
//    val doc = f.inputStream().use {
//        t.parseDOM(it, null)
//    }


    val dbFactory = DocumentBuilderFactory.newInstance()
    val dBuilder =  dbFactory.newDocumentBuilder()
    val doc: Document = dBuilder.parse(f)
    doc.documentElement.normalize()
    doc.renameAllElements("h6", "h7")
    doc.renameAllElements("h5", "h6")
    doc.renameAllElements("h4", "h5")
    doc.renameAllElements("h3", "h4")
    doc.renameAllElements("h2", "h3")

    TransformerFactory.newInstance().newTransformer().transform(
        DOMSource(doc), StreamResult(File("delme.html"))
    )


//


    doc.getElementsByTagName("h1").toList().forEach() {

        println("${it.nodeName} ${it.childNodes.item(0).nodeValue}")
    }
//    chapterSections.forEach { node ->
//
//        node.replaceChild()
//        println("${node.nodeName} ${node.attributes.toMap()}")
//    }
}

private fun Document.renameAllElements(from: String, to: String) {
    getElementsByTagName(from).toList().forEach() {
        renameNode(it, null, to)
    }
}

private fun NamedNodeMap.toMap(): Map<String, Node> =
    List<Node>(this.length) { index ->
        this.item(index)
    }.map { it.nodeName to it}.toMap()



private fun NodeList.toList(): List<Node> = List<Node>(this.length) { index ->
    this.item(index)
}
