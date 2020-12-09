package properties

fun accessField(person: PersonWithPublicFields): String =
    person.givenName

fun callAccessor(person: PersonWithAccessor): String =
    person.givenName

fun callKotlinAccessor(person: PersonWithProperties): String =
    person.givenName