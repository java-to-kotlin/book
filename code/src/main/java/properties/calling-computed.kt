package properties

fun print2(person: PersonWithPublicFields) {
    println(
        person.givenName + " " +
        person.fullName
    )
}
