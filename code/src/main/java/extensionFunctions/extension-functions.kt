package extensionFunctions

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import java.time.Duration
import java.time.ZonedDateTime
import java.util.*

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
    nameForMarketing(customer) // doesn't compile <1>
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
    customer.methodAsFunctionReference()
    customer.extensionAsFunctionReference()
    /// end: invoke3
     */

}


fun delme() {
    val s = null.toString()
}

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

fun xmlUsage() {
    /// begin: xmlUsage
    val customerElement: Element? = SOME_CODE()
    val customer: Customer? = customerElement?.toCustomer()
    /// end: xmlUsage
}

fun xmlUsage2() {
    val customerElement: Element? = SOME_CODE()

    /// begin: xmlUsage2
    val customer: Customer? = customerElement.toCustomer()
    /// end: xmlUsage2
}


/// begin: jsonWriter
class JsonWriter(
    private val objectMapper: ObjectMapper,
) {
    fun Customer.toJson(): JsonNode = objectMapper.valueToTree(this)
}
/// end: jsonWriter

object Explanation {
    class JsonWriter(
        private val objectMapper: ObjectMapper,
    ) {
        /// begin: this
        fun Customer.toJson(): JsonNode =
            this@JsonWriter.objectMapper.valueToTree(this@toJson)
        /// end: this
    }
}

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
    val customer: Customer = SOME_CODE()
    val customerString: String = customer.toString()
    // Calls Customer.toString()

    val nullableCustomer: Customer? = SOME_CODE()
    val nullableCustomerString: String = nullableCustomer.toString()
    // Calls Any?.toString()
    /// end: nullableToString
}

/// begin: printed
fun <T> T.printed(): T = this.also(::println)
/// end: printed

/// begin: familyNames
fun Iterable<Customer>.familyNames(): Set<String> =
    this.map(Customer::familyName).toSet()
/// end: familyNames


object NullsAndNestedFunctionCalls {
    data class Trip(
        val departureTime: ZonedDateTime? = null
    )

    fun conditional() {
        /// begin: single_if
        val customer: Customer? = loggedInCustomer()
        val greeting: String? = when (customer) {
            null -> null
            else -> greetingForCustomer(customer)
        }
        /// end: single_if
    }

    fun single_let() {
        /// begin: single_let
        val customer: Customer? = loggedInCustomer()
        val greeting: String? = customer?.let { greetingForCustomer(it) }
        /// end: single_let
    }

    fun nesting_ifs() {
        /// begin: nested_ifs
        val customer: Customer? = loggedInCustomer()
        val currentTime: ZonedDateTime = SOME_CODE()

        val reminder: String? = if (customer != null) {
            val nextTrip: Trip? = nextTripForCustomer(customer)
            if (nextTrip != null) {
                val timeToDeparture = timeUntilDepartureOfTrip(nextTrip, currentTime)
                if (timeToDeparture != null) {
                    durationToUserFriendlyText(timeToDeparture) + " until your next trip!"
                } else null
            } else null
        } else null
        /// end: nested_ifs
    }

    fun nesting_ifs_negated() {
        val customer: Customer? = loggedInCustomer()
        val currentTime: ZonedDateTime = SOME_CODE()

        val reminder: String? = if (customer == null) null else {
            val nextTrip: Trip? = nextTripForCustomer(customer)
            if (nextTrip == null) null else {
                val timeToDeparture = timeUntilDepartureOfTrip(nextTrip, currentTime)
                if (timeToDeparture == null) null else {
                    durationToUserFriendlyText(timeToDeparture) + " until your next trip!"
                }
            }
        }
    }

    fun nested_lets() {
        /// begin: nested_lets
        val customer: Customer? = loggedInCustomer()
        val currentTime: ZonedDateTime = SOME_CODE()

        val reminder: String? = customer?.let {
            nextTripForCustomer(it)?.let {
                timeUntilDepartureOfTrip(it, currentTime)?.let {
                    durationToUserFriendlyText(it) + " until your next trip!"
                }
            }
        }
        /// end: nested_lets
    }

    fun flattened_lets() {
        val customer: Customer? = SOME_CODE()
        val currentTime: ZonedDateTime = SOME_CODE()

        /// begin: chained_lets
        val reminder: String? = customer
            ?.let { nextTripForCustomer(it) }
            ?.let { timeUntilDepartureOfTrip(it, currentTime) }
            ?.let { durationToUserFriendlyText(it) }
            ?.let { it + " until your next trip!" }
        /// end: chained_lets
    }

    fun extensions() {
        val customer: Customer? = SOME_CODE()
        val currentTime: ZonedDateTime = SOME_CODE()

        /// begin: chained_extensions
        val reminder: String? = customer
            ?.nextTrip()
            ?.timeUntilDeparture(currentTime)
            ?.toUserFriendlyText()
            ?.plus(" until your next trip!")
        /// end: chained_extensions
    }

    fun nullable_trip_extension() {
        val customer: Customer? = SOME_CODE()
        val currentTime: ZonedDateTime = SOME_CODE()

        /// begin: nullable_trip_receiver
        fun Trip?.reminderAt(currentTime: ZonedDateTime) =
            this?.timeUntilDeparture(currentTime)
                ?.toUserFriendlyText()
                ?.plus(" until your next trip!")
        /// end: nullable_trip_receiver

        /// begin: nullable_trip_reminder_usage
        val reminder: String? = customer
            ?.nextTrip()
            .reminderAt(currentTime)
        /// end: nullable_trip_reminder_usage
    }

    fun non_nullable_trip_extension() {
        val customer: Customer? = SOME_CODE()
        val currentTime: ZonedDateTime = SOME_CODE()

        fun Trip.reminderAt(currentTime: ZonedDateTime) =
            this.timeUntilDeparture(currentTime)
                ?.toUserFriendlyText()
                ?.plus(" until your next trip!")

        /// begin: non_nullable_trip_reminder_usage
        val reminder: String? = customer
            ?.nextTrip()
            ?.reminderAt(currentTime)
        /// end: non_nullable_trip_reminder_usage
    }


    private fun loggedInCustomer(): Customer? = SOME_CODE()

    private fun greetingForCustomer(customer: Customer): String? = SOME_CODE()

    private fun nextTripForCustomer(customer: Customer): Trip? = SOME_CODE()
    private fun timeUntilDepartureOfTrip(trip: Trip, now: ZonedDateTime): Duration? = SOME_CODE()
    private fun durationToUserFriendlyText(duration: Duration): String = SOME_CODE()

    private fun Customer.nextTrip(): Trip? = SOME_CODE()
    private fun Trip.timeUntilDeparture(now: ZonedDateTime): Duration? = SOME_CODE()
    private fun Duration.toUserFriendlyText(): String = SOME_CODE()
}


object NullableAsOptional {
    /// begin: as_optional
    fun <T : Any> T?.asOptional(): Optional<T> = Optional.ofNullable(this)
    /// end: as_optional
}
