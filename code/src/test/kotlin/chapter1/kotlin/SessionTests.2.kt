package chapter1.kotlin2

import chapter1.kotlin.Presenter
import chapter1.kotlin3.Session
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SessionTests {

    private val alice = Presenter("id1", "Alice")
    private val bob = Presenter("id2", "Bob")
    private val noPresenters: List<Presenter> = emptyList()

    private val aSession = Session(
        "The Title",
        listOf(bob, alice)
    )

    @Test
    fun `can change title`() {
        assertEquals(
            Session(
                "Another Title",
                listOf(bob, alice)
            ),
            aSession.withTitle("Another Title")
        )
    }

    @Test
    fun `can change presenters`() {
        assertEquals(
            Session("The Title", noPresenters),
            aSession.withPresenters(noPresenters)
        )
    }
}