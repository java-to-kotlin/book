package chapter1.kotlin3

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> {
        val thing = sessions.firstOrNull { it.title == title } /// insert

        for (session in sessions) {
            if (session.title == title) return Optional.of(session)
        }
        return Optional.empty()
    }
}