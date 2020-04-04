
package chapter1.kotlin4

import chapter1.java.Session
import java.util.*

object Sessions {
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> {
        val thing: Session? = sessions.firstOrNull { it.title == title } /// change

        for (session in sessions) {
            if (session.title == title) return Optional.of(session)
        }
        return Optional.empty()
    }
}