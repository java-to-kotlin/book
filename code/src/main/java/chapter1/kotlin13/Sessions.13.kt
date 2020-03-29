package chapter1.kotlin13

import chapter1.kotlin8.Session
import java.util.*

object Sessions {
    /// begin: foo
    @JvmStatic
    fun List<Session>.findWithTitle(title: String) =
        firstOrNull { it.title == title }.toOptional()
    /// end: foo

    private fun Session?.toOptional() = Optional.ofNullable(this)
}