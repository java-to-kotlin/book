package chapter1.kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SessionTests {
    private val aSession = Session(
        "The Title",
        java.util.List.of(bob, alice)
    )

    @Test
    fun can_change_title() {
        assertEquals( // <1>
            Session(
                "Another Title",
                java.util.List.of(bob, alice)
            ),
            aSession.withTitle("Another Title")
        )
    }

    @Test
    fun can_change_presenters() {
        assertEquals(
            Session("The Title", noPresenters),
            aSession.withPresenters(noPresenters)
        )
    }

    companion object {
        private val alice = Presenter("id1", "Alice")
        private val bob = Presenter("id2", "Bob")
        private val noPresenters: List<Presenter> = emptyList()
    }
}