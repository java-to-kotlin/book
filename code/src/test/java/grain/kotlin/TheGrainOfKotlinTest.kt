package grain.kotlin

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TheGrainOfKotlinTest {
    @Test
    fun `test it`() {
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7)

        /// begin: reduce
        val sum = numbers.fold(0, Int::plus)
        /// end: reduce

        assertEquals(28, sum)
    }
}