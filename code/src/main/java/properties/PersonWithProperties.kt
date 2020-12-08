package properties

import java.time.LocalDate
import java.time.Period

/// begin: PwP
/// begin: excerpt
class PersonWithProperties(
    val givenName: String,
    val familyName: String,
    val dateOfBirth: LocalDate
) {
    /// mute: excerpt []
    val fullName get() = "$givenName $familyName"
    /// resume: excerpt
}
/// end: excerpt
/// end: PwP

object DoB {
    /// begin: DoB
    class PersonWithProperties(
        val givenName: String,
        val familyName: String,
        val dateOfBirth: LocalDate
    ) {
        fun age() = Period.between(dateOfBirth, LocalDate.now()).years
    }
    /// end: DoB
}

object Hash {
    /// begin: hash
    class PersonWithProperties(
        val givenName: String,
        val familyName: String,
        val dateOfBirth: LocalDate
    ) {
        val hash: ByteArray =
            someSlowHashOf(givenName, familyName, dateOfBirth.toString())
    }
    /// end: hash
}

object Hash2 {
    /// begin: hash2
    class PersonWithProperties(
        val givenName: String,
        val familyName: String,
        val dateOfBirth: LocalDate
    ) {
        val hash: ByteArray by lazy {
            someSlowHashOf(givenName, familyName, dateOfBirth.toString())
        }
    }
    /// end: hash2

}

object Hash3 {
    /// begin: hash3
    class PersonWithProperties(
        val givenName: String,
        val familyName: String,
        val dateOfBirth: LocalDate
    ) {
        private val hash: ByteArray by lazy {
            someSlowHashOf(givenName, familyName, dateOfBirth.toString())
        }
        fun hash() = hash
    }
    /// end: hash3

}

private fun someSlowHashOf(vararg strings: String): ByteArray = TODO()
