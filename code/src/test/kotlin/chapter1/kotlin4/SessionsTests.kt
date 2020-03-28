package chapter1.kotlin4

import chapter1.kotlin8.Session
import org.junit.jupiter.api.Assertions.assertEquals
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
            sessions.firstOrNull { it.title == "Title Too" }
        )
    }
    /// end: foo


    @Test
    fun `find no session with title`() {
        assertEquals(
            null,
            sessions.firstOrNull { it.title == "No such" }
        )
    }

    @Test
    fun `find in no sessions`() {
        assertEquals(
            null,
            emptyList<Session>().firstOrNull { it.title == "No such" }
        )
    }
}