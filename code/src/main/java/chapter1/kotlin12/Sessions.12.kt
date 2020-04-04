package chapter1.kotlin12

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun List<Session>.findSessionWithTitle(title: String) = /// change
        firstOrNull { it.title == title }.toOptional()      /// change

    private fun Session?.toOptional() = Optional.ofNullable(this)
}