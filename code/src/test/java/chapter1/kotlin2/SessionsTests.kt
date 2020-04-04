package chapter1.kotlin2

import chapter1.kotlin14.Sessions.findWithTitle
import chapter1.kotlin14.Sessions.firstWithTitle
import chapter1.kotlin14.Sessions.toOptional
import chapter1.kotlin8.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import java.util.List

class SessionsTests {
    private val sessions = List.of(
        Session("Title", emptyList()),
        Session("Title Too", emptyList())
    )

    /// begin: foo
    @Test
    fun find_session_with_title() {
        Assertions.assertEquals(
            Optional.of(sessions[1]),
            sessions.firstWithTitle("Title Too").toOptional() /// change: foo
        )
    }
    /// end: foo
    
    /// begin: bar
    @Test
    fun `find session with title`() {
        assertEquals(
            sessions[1],                         ///  change: bar
            sessions.firstWithTitle("Title Too") ///  change: bar
        )
    }
    /// end: bar

    /// begin: baz
    @Test
    fun find_no_session_with_title() {
        Assertions.assertEquals(
            Optional.empty<Any>(),
            sessions.findWithTitle("No such")
        )
    }
    /// end: baz

    @Test
    fun find_in_no_sessions() {
        Assertions.assertEquals(
            Optional.empty<Any>(),
            emptyList<Session>().findWithTitle("No such")
        )
    }
}