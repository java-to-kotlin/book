package chapter1.kotlin3

import chapter1.kotlin15.Sessions.firstWithTitle
import chapter1.kotlin8.Session
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class SessionsTests {
    private val sessions = listOf(
        Session("Title", emptyList()),
        Session("Title Too", emptyList())
    )

    /// begin: foo
    @Test
    fun `find session with title`() {
        assertEquals(
            sessions[1],
            sessions.firstWithTitle("Title Too") /// change: foo
        )
    }

    @Test
    fun `find no session with title`() {
        assertNull(sessions.firstWithTitle("No such")) /// change
    }

    @Test
    fun `find in no sessions`() {
        assertNull(emptyList<Session>().firstWithTitle("No such")) /// change
    }
    /// end: foo
}