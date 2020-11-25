package extensionFunctions

import extensionFunctions.A.Customer

object A {
    /// begin: customerFull
    /// begin: customer
    data class Customer(
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
        val methodReference: (Customer.() -> String) = Customer::fullName
        val extensionFunctionReference: (Customer.() -> String) = Customer::nameForMarketing

        val methodAsFunctionReference: (Customer) -> String = methodReference
        val extensionAsFunctionReference: (Customer) -> String = extensionFunctionReference
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
}

/// begin: nameForMarketingExt
fun Customer.nameForMarketing() = "${familyName.toUpperCase()}, $givenName}"
/// end: nameForMarketingExt


object B {
    /// begin: postalName
    data class Customer(
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

    class TitleProvider(val title: String) {
        fun Customer.nameWithTitle() = "$title $fullName"
    }
}