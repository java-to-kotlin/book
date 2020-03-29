package chapter1.kotlin11

import chapter1.java.Session
import java.util.*

object Sessions {
    /// begin: foo
    @JvmStatic
    fun findSessionWithTitle(sessions: List<Session>, title: String) =
        sessions.firstOrNull { it.title == title }.toOptional()

    private fun Session?.toOptional() = Optional.ofNullable(this)
    /// end: foo
}