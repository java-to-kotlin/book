package extensionFunctions

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.ObjectWriter
import java.io.OutputStream

/// begin: customerFull
/// begin: customer
data class Customer(
    val id: String,
    val givenName: String,
    val familyName: String
) {
    /// mute: customer
    val fullName get() = "$givenName $familyName"
    /// resume: customer
}
/// end: customer
/// end: customerFull

/// begin: fullName
fun fullName(customer: Customer) =
    "$customer.givenName $customer.familyName"
/// end: fullName

fun calling(customer: Customer) {
    /// begin: callingExt
    val s = customer.nameForMarketing()
    /// end: callingExt

    /*
    /// begin: error
    nameForMarketing(customer) // <1> doesn't compile
    /// end: error
     */
    /// begin: references
    val methodReference: (Customer.() -> String) =
        Customer::fullName
    val extensionFunctionReference: (Customer.() -> String) =
        Customer::nameForMarketing

    val methodAsFunctionReference: (Customer) -> String =
        methodReference
    val extensionAsFunctionReference: (Customer) -> String =
        extensionFunctionReference
    /// end: references

    /// begin: invoke1
    customer.methodReference()
    customer.extensionFunctionReference()

    methodAsFunctionReference(customer)
    extensionAsFunctionReference(customer)
    /// end: invoke1

    /// begin: invoke2
    methodReference(customer)
    extensionFunctionReference(customer)
    /// end: invoke2

    /*
    /// begin: invoke3
    customer.methodAsFunctionReference() // <1> doesn't compile
    customer.extensionAsFunctionReference() // doesn't compile
    /// end: invoke3
     */

}


fun delme() {
    val s = null.toString()
}

/// begin: nameForMarketingExt
fun Customer.nameForMarketing() = "${familyName.toUpperCase()}, $givenName}"
/// end: nameForMarketingExt


object Chaining {
    val jsonNode: JsonNode = SOME_CODE()

    /// begin: chaining
    fun JsonNode.toCustomer(): Customer = SOME_CODE()

    /// begin: printed2
    val marketingLength = jsonNode.toCustomer().nameForMarketing().length
    /// end: printed2
    /// end: chaining

    fun withoutPrinted() {
        /// begin: printed3
        val customer = jsonNode.toCustomer()
        println(customer)
        val marketingLength = customer.nameForMarketing().length
        /// end: printed3
    }

    fun withPrinted() {
        /// begin: printed4
        val marketingLength = jsonNode.toCustomer().printed().nameForMarketing().length
        /// end: printed4
    }

}

class TitleProvider(val title: String) {
    fun Customer.nameWithTitle() = "$title $fullName"
}

class Element {
    fun attribute(s: String): String = SOME_CODE()
}

/// begin: xml
fun Element?.toCustomer(): Customer? = this?.let { element ->
    Customer(
        element.attribute("id"),
        element.attribute("first-name"),
        element.attribute("last-name")
    )
}
/// end: xml


/// begin: jsonWriter
class JsonWriter(
    private val objectMapper: ObjectMapper,
    private val outputStream: OutputStream
) {
    fun writeCustomer(customer: Customer) {
        objectMapper.writer().writeValue(outputStream, customer.toJson())
    }

    fun asJsonString(customer: Customer) =
        objectMapper.writeValueAsString(customer.toJson())

    private fun Customer.toJson(): JsonNode = objectMapper.valueToTree(this)
}
/// end: jsonWriter

object CounterFactuals {
    /// begin: postalName
    data class Customer(
        val id: String,
        val givenName: String,
        val familyName: String
    ) {
        val fullName get() = "$givenName $familyName"
        fun nameForMarketing() = "${familyName.toUpperCase()}, $givenName}"
    }
    /// end: postalName

    /// begin: nameForMarketing
    fun nameForMarketing(customer: Customer) =
        "${customer.familyName.toUpperCase()}, $customer.givenName}"
    /// end: nameForMarketing

    val Customer.fullName get() = "$givenName $familyName"

    fun methodIsPreferred(customer: Customer) {
        customer.fullName
    }

    /// begin: nameForMarketingProp
    val Customer.nameForMarketing get() = "${familyName.toUpperCase()}, $givenName}"
    /// end: nameForMarketingProp

}

fun <T> SOME_CODE(): T = TODO()

fun nullableToString() {
    /// begin: nullableToString
    val customer: Customer? = SOME_CODE()
    val s: String = customer.toString()
    /// end: nullableToString
}

/// begin: printed
fun <T> T.printed(): T = this.also(::println)
/// end: printed

/// begin: familyNames
fun Iterable<Customer>.familyNames(): Set<String> =
    this.map(Customer::familyName).toSet()
/// end: familyNames



