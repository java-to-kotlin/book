package chapter1.kotlin

import chapter1.java.Presenter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PresenterTests {

    private val nat = Presenter("1", "Nat")
    private val duncan = Presenter("2", "Duncan")

    @Test
    fun properties() {
        Assertions.assertEquals("1", nat.id)
        Assertions.assertEquals("Duncan", duncan.name)
    }

    @Test
    fun equality() {
        Assertions.assertEquals(nat, Presenter("1", "Nat"))
        Assertions.assertNotEquals(nat, duncan)
    }

    @Test
    fun testHashCode() {
        Assertions.assertEquals(nat.hashCode(), Presenter("1", "Nat").hashCode())
        Assertions.assertNotEquals(nat.hashCode(), duncan.hashCode())
    }
}