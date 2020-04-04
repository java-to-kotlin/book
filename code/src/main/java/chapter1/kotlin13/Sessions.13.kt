package chapter1.kotlin13

import chapter1.kotlin8.Session
import java.util.*

object Sessions {
    /// begin: foo, bar
    @JvmStatic
    fun List<Session>.findWithTitle(title: String) =  /// change: foo
        firstOrNull { it.title == title }.toOptional()
    /// end: foo, bar

    private fun Session?.toOptional() = Optional.ofNullable(this)
}