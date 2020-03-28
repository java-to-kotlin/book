package chapter1.kotlin9

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
        return optional(thing)
    }

    private fun optional(thing: Session?) = Optional.ofNullable(thing)
    /// end: foo
}