package properties

fun print(person: PersonWithPublicFields) {
    println(person.givenName) // <1>
}

fun print(person: PersonWithAccessor) {
    println(person.givenName) // <2>
}

fun print(person: PersonWithProperties) {
    println(person.givenName) // <3>
}