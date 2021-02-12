package sequences

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.util.stream.Collectors.toList

@Disabled
class PerformanceTests {

    val items = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten");

    @Test
    fun list() {
        repeat(10000000) {
            items.sorted()
        }
    }

    @Test
    fun stream() {
        repeat(10000000) {
            items.stream().sorted().collect(toList())
        }
    }
}