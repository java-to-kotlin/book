package extensionFunctions

fun Customer.nameForMarketing() = "${familyName.toUpperCase()}, $givenName}"
