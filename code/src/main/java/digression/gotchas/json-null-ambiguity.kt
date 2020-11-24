@file:Suppress("UNUSED_PARAMETER", "UNUSED_VARIABLE")

package digression.gotchas

/// begin: null-ambiguity
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory.instance as json


fun objectOf(vararg properties: Pair<String, JsonNode>): ObjectNode =
    ObjectNode(json, properties.toMap())

infix fun String.of(propertyValue: Int?) =
    Pair(this, json.numberNode(propertyValue))

infix fun String.of(propertyValue: String?) =
    Pair(this, json.textNode(propertyValue))
/// end: null-ambiguity

/// begin: fix-null-ambiguity
infix fun String.of(propertyValue: Nothing?) =
    Pair(this, json.nullNode())
/// end: fix-null-ambiguity



//fun example1() {
//val someJson = objectOf(
//    "x" of 10,
//    "y" of null
//)
//}

fun example2() {
infix fun String.of(propertyValue: Nothing?) =
    Pair(this, json.nullNode())

val someJson = objectOf(
    "x" of 10,
    "y" of null
)
}