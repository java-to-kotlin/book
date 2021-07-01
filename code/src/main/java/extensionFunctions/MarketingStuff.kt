package extensionFunctions

fun Customer.nameForMarketing() = "${familyName.uppercase()}, $givenName}"
