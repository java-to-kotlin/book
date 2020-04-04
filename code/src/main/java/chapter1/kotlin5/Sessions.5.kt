package chapter1.kotlin5

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> {
        val thing: Session? = sessions.firstOrNull { it.title == title }
        if (thing != null)              /// change
            return Optional.of(thing)   /// change
        else                            /// change
            return Optional.empty()     /// change
    }
}