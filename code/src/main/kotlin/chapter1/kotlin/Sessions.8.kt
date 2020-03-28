package chapter1.kotlin8

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> =
        Optional.ofNullable(sessions.firstOrNull { it.title == title })
}