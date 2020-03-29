package chapter1.kotlin

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> {
        for (session in sessions) {
            if (session.title == title) return Optional.of(session)
        }
        return Optional.empty()
    }
}