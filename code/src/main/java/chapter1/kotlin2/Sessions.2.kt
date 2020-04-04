package chapter1.kotlin2

import chapter1.java.Session
import java.util.*

object Sessions {
    /// begin: foo
    @JvmStatic
    fun findSessionWithTitle(
        sessions: List<Session>,
        title: String
    ): Optional<Session> {
        return sessions                         /// change
            .firstOrNull { it.title == title }  /// change
            ?.let { Optional.of(it) }           /// change
            ?: Optional.empty()                 /// change
    }
    /// end: foo
}