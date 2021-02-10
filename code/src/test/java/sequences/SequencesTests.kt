package sequences

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalStateException

class SequencesTests {

    val input = listOf("", "123", "   ", " 1", " ")

    @Test
    fun averageNonBlankLength() {
        assertEquals(1.0, Loop.averageNonBlankLength(input))
        assertEquals(1.0, Loop2.averageNonBlankLength(input))
        assertEquals(1.0, StreamEx.averageNonBlankLength(input))
        assertEquals(1.0, StreamEx2.averageNonBlankLength(input))
        assertEquals(1.0, Lists.averageNonBlankLength(input))
        assertEquals(1.0, Sequences.averageNonBlankLength(input))
        assertEquals(1.0, Sequences2.averageNonBlankLength(input))
        assertEquals(1.0, Sequences3.averageNonBlankLength(input))
        assertEquals(1.0, Sequences6.averageNonBlankLength(input.asSequence().constrainOnce()))
        assertEquals(1.0, Sequences7.averageNonBlankLength(input.asSequence().constrainOnce()))
    }

    @Test
    fun consumingStream() {
        assertThrows<IllegalStateException> {
            assertEquals(1.0, ConsumingTwice.averageNonBlankLength(input))
        }
    }

    @Test
    fun consumingSequence() {
        assertEquals(1.0, Sequences3.averageNonBlankLength(input))
    }

    @Test
    fun consumingSequence2() {
        assertThrows<IllegalStateException> {
            assertEquals(
                1.0, Sequences3.averageNonBlankLength(
                    input.iterator()
                )
            )
        }
    }

    @Test
    fun consumingSequence3() {
        assertEquals(
            1.0,
            Sequences4.averageNonBlankLength(input.iterator().asSequence())
        )
    }

    @Test
    fun consumingSequence4() {
        assertEquals(
            1.0,
            Sequences5.averageNonBlankLength(input.iterator().asSequence())
        )
    }

    @Test
    fun take() {
        val processed = mutableListOf<Int>()
        val input = (1..5)
        val output = input.asSequence()
            .onEach {
                processed.add(it)
            }.take(3).toList()
        assertEquals(listOf(1, 2, 3), output)
        assertEquals(listOf(1, 2, 3), processed)
    }
}