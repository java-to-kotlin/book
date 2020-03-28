package chapter1.kotlin3

import chapter1.kotlin15.Sessions.firstWithTitle
import chapter1.kotlin8.Session
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SessionsTests {
    private val sessions = listOf(
        Session("Title", emptyList()),
        Session("Title Too", emptyList())
    )

    @Test
    fun `find session with title`() {
        assertEquals(
            sessions[1],
            sessions.firstWithTitle("Title Too")
        )
    }

    @Test
    fun `find no session with title`() {
        assertEquals(
            null,
            sessions.firstWithTitle("No such")
        )
    }

    @Test
    fun `find in no sessions`() {
        assertEquals(
            null,
            emptyList<Session>().firstWithTitle("No such")
        )
    }
}