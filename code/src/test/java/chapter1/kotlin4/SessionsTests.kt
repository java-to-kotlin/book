package chapter1.kotlin4

import chapter1.kotlin8.Session
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/// begin: foo
class SessionsTests {
    private val sessions = listOf(
        Session("Title", emptyList()),
        Session("Title Too", emptyList())
    )

    @Test
    fun `find session with title`() {
        assertEquals(
            sessions[1],
            sessions.firstOrNull { it.title == "Title Too" } /// change
        )
    }


    @Test
    fun `find no session with title`() {
        assertEquals(
            null,
            sessions.firstOrNull { it.title == "No such" } /// change
        )
    }

    @Test
    fun `find in no sessions`() {
        assertEquals(
            null,
            emptyList<Session>().firstOrNull { it.title == "No such" } /// change
        )
    }
}
/// end: foo
