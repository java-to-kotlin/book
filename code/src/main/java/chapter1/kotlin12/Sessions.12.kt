package chapter1.kotlin12

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun List<Session>.findSessionWithTitle(title: String) =
        firstOrNull { it.title == title }.toOptional()

    private fun Session?.toOptional() = Optional.ofNullable(this)
}