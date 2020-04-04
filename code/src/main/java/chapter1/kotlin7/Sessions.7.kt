package chapter1.kotlin7

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> {
        /// begin: foo, bar
        val thing: Session? = sessions.firstOrNull { it.title == title }
        return Optional.ofNullable(thing) /// change: foo
        /// end: foo, bar
    }
}