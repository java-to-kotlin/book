package chapter1.kotlin10

import chapter1.java.Session
import java.util.*

object Sessions {
    /// begin: foo
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> {
        val thing: Session? = sessions.firstOrNull { it.title == title }
        return thing.optional()
    }

    private fun Session?.optional() = Optional.ofNullable(this) /// change
    /// end: foo
}