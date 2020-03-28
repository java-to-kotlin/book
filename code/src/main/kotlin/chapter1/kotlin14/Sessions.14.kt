package chapter1.kotlin14

import chapter1.kotlin8.Session
import java.util.*

object Sessions {
    /// begin: foo
    @JvmStatic
    fun List<Session>.findWithTitle(title: String) =
        firstWithTitle(title).toOptional()

    fun List<Session>.firstWithTitle(title: String) =
        firstOrNull { it.title == title }

    fun Session?.toOptional() = Optional.ofNullable(this)
    /// end: foo
}