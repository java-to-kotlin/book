package chapter1.kotlin11

import chapter1.java.Session
import java.util.*

object Sessions {
    /// begin: foo
    @JvmStatic
    fun findSessionWithTitle(sessions: List<Session>, title: String) = /// change
        sessions.firstOrNull { it.title == title }.toOptional()        /// change

    private fun Session?.toOptional() = Optional.ofNullable(this) /// change
    /// end: foo
}