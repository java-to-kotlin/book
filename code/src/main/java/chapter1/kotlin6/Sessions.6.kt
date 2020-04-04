package chapter1.kotlin6

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
        return if (thing != null) /// change
            Optional.of(thing)    /// change
        else                      /// change
            Optional.empty()      /// change
        /// end: foo
    }
}