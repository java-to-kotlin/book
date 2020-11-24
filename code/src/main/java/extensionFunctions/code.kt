package extensionFunctions

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
}

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