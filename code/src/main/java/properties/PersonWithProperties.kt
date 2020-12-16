package properties

import java.time.LocalDate
import java.time.Period

/// begin: PwP
/// begin: excerpt
data class PersonWithProperties(
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
    data class PersonWithProperties(
        val givenName: String,
        val familyName: String,
        val dateOfBirth: LocalDate
    ) {
        fun age() = Period.between(dateOfBirth, LocalDate.now()).years
    }
    /// end: DoB
}

object Hash0 {
    /// begin: hash0
    data class PersonWithProperties(
        val givenName: String,
        val familyName: String,
        val dateOfBirth: LocalDate
    ) {
        fun computeHash(): ByteArray =
            someSlowHashOf(givenName, familyName, dateOfBirth.toString())
    }
    /// end: hash0
}

object Hash {
    /// begin: hash
    data class PersonWithProperties(
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
    data class PersonWithProperties(
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
    data class PersonWithProperties(
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
