@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")

package digression.gotchas

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode


private val factory = JsonNodeFactory.instance

fun objectOf(vararg properties: Pair<String, JsonNode>) =
    factory.objectNode().apply { setAll<ObjectNode>(properties.toMap()) }

infix fun String.of(propertyValue: Int?) =
    Pair(this, factory.numberNode(propertyValue))

infix fun String.of(propertyValue: String?) =
    Pair(this, factory.textNode(propertyValue))



//fun example1() {
//val someJson = objectOf(
//    "x" of 10,
//    "y" of null
//)
//}

fun example2() {
infix fun String.of(propertyValue: Nothing?) =
    Pair(this, factory.nullNode())

val someJson = objectOf(
    "x" of 10,
    "y" of null
)
}