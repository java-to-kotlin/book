package chapter1.kotlin5

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> {
        /// begin: foo
        val thing: Session? = sessions.firstOrNull { it.title == title }
        if (thing != null)
            return Optional.of(thing)
        else
            return Optional.empty()
        /// end: foo
    }
}