package chapter1.kotlin2

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> {
        return sessions
            .firstOrNull { it.title == title }
            ?.let { Optional.of(it) }
            ?: Optional.empty()
    }
}