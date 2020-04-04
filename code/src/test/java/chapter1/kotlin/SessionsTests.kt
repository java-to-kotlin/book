package chapter1.kotlin

import chapter1.kotlin13.Sessions.findWithTitle
import chapter1.kotlin8.Session
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*
import java.util.List

class SessionsTests {
    private val sessions = List.of(
        Session("Title", emptyList()),
        Session("Title Too", emptyList())
    )

    /// begin: foo, fooo
    @Test
    fun find_session_with_title() {
        Assertions.assertEquals(
            Optional.of(sessions[1]),
            sessions.findWithTitle("Title Too") /// change: fooo
        )
    }
    /// end: foo, fooo

    /// begin: bar
    @Test
    fun find_no_session_with_title() {
        Assertions.assertEquals(
            Optional.empty<Any>(),
            sessions.findWithTitle("No such")
        )
    }
    /// end: bar

    /// begin: baz
    @Test
    fun find_in_no_sessions() {
        Assertions.assertEquals(
            Optional.empty<Any>(),
            emptyList<Session>().findWithTitle("No such")
        )
    }
    /// end: baz
}