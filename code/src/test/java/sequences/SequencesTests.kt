package sequences

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SequencesTests {

    @Test
    fun averageNonBlankLength() {
        val input = listOf("", "123", "         ")
        assertEquals(1.0, Lists.averageNonBlankLength(input))
        assertEquals(1.0, Sequences.averageNonBlankLength(input))
        assertEquals(1.0, Sequences2.averageNonBlankLength(input))
    }
}