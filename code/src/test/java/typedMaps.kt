import HasFruit.asA
import HasFruit.fruit
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

inline class TypedMap<P, K, V>(val value: Map<K, V>) : Map<K, V> {
    override val entries get() = value.entries
    override val keys get() = value.keys
    override val size get() = value.size
    override val values get() = value.values
    override fun containsKey(key: K): Boolean = value.containsKey(key)
    override fun get(key: K): V? = value.get(key)
    override fun isEmpty() = value.isEmpty()
    override fun containsValue(value: V) = this.value.containsValue(value)
}

operator fun <P, K, V> TypedMap<P, out K, V>.plus(pair: Pair<K, V>): TypedMap<P, K, V> =
    TypedMap((this as Map<K, V>).plus(pair))


object HasFruit {
    @Suppress("UNCHECKED_CAST")
    fun <V> Map<String, V>.asA(dummy: HasFruit): MapWithFruit<String, V>? = when {
        this.containsKey("fruit") -> TypedMap(this)
        else -> null
    }

    val <V> MapWithFruit<String, V>.fruit: V
        get() =
            this["fruit"] ?: throw IllegalArgumentException("No fruit in map")
}

typealias MapWithFruit<K, V> = TypedMap<HasFruit ,K, V>

class TypedMapsTests {
    @Test
    fun parses() {
        val fromOutside = mapOf("fruit" to "banana", "foo" to "bar")
        val validated = fromOutside.asA(HasFruit)

        // doesn't compile
        // validated.fruit

        validated ?: error("parse failure")

        assertEquals("banana", validated.fruit)
    }

    @Test
    fun `doesnt parse`() {
        val fromOutside = mapOf("foo" to "bar")
        assertNull(fromOutside.asA(HasFruit))
    }

    @Test
    fun `adding keys`() {
        val fromOutside = mapOf("fruit" to "banana", "foo" to "bar")
        val validated = fromOutside.asA(HasFruit)!!

        val addSomeKeys = validated.plus("key" to "value")

        assertEquals("banana", addSomeKeys.fruit)
    }
}