package chapter1.kotlin4

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

        for (session in sessions) {
            if (session.title == title) return Optional.of(session)
        }
        return Optional.empty()
        /// end: foo
    }
}