import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class EitherTests {
    @Test
    fun `infers types`() {

        fun toInt(p: Either<String, Int>): Int = p.fold(
            { s -> s.toInt() },
            { i -> i }
        )

        assertEquals(42, toInt("42".bindA()))
        assertEquals(99, toInt(99.bindB()))

        val string = "42".bindA()
        assertEquals(42, toInt(string))

        val int = 99.bindA()
        // doesn't compile
        // toInt(int)

        val int2 = int.swap()
        assertEquals(99, toInt(int2))
    }

    @Test
    fun properties() {
        val string = "42".bindA()
        assertEquals("42", string.a)

        // doesn't compile
        // string.b

        assertEquals("42", string.swap().b)

        val int = 99.bindB()
        assertEquals(99, int.b)

        // doesn't compile
        // int.b

        val either: Either<String, Int> = "42".bindA()

        // don't compile
        // either.a
        // either.b

        assertEquals("42", either.aOrNull())
        assertEquals(null, either.bOrNull())
    }

    @Test
    fun map1() {
        val either: Either<String, Int> = "42".bindA()
        val result: Either<Int, Int> = either.mapA { it.toInt() }
        assertEquals(42, result.value)
    }

    @Test
    fun map2() {
        val either = "42".bindA()
        val result = either.mapA { it.toInt() }
        assertEquals(42, result.a)
    }

    @Test
    fun map3() {
        val either: Either<String, Int> = 99.bindB()
        val result: Either<Int, Int> = either.mapA { it.toInt() }
        assertEquals(99, result.value)
    }

    @Test
    fun `chain maps`() {
        val bindB: Either<String, Int> = 99.bindB()
        assertEquals(99, bindB.mapA { it.toInt() }.mapB { it }.value())

        // doesn't compile, telling you that you can't mapA!
        // 99.bindB().mapA { it.toInt() }
    }


    @Test
    fun `both types being nullable is a bit suss`() {
        val defaultStringValue = -1
        val defaultIntValue = -99

        fun nullableToInt(p: Either<String?, Int?>): Int = p.fold(
            { s -> s?.toInt() ?: defaultStringValue },
            { i -> i ?: defaultIntValue }
        )

        val string: Either<String?, Int?> = null.bindA()

        assertEquals(defaultStringValue, nullableToInt(string))

        // hmmm
        assertEquals(defaultStringValue, nullableToInt(null.bindB()))
    }
}

inline class Either<out A, out B>(val value: Any?)

fun <A> A.bindA(): Either<A, Nothing> = Either(this)
fun <B> B.bindB(): Either<Nothing, B> = Either(this)

fun <A, B> Either<A, B>.swap(): Either<B, A> = this as Either<B, A>

val <A> Either<A, Nothing>.a get() = this.value
val <B> Either<Nothing, B>.b get() = this.value
fun <A> Either<A, A>.value(): A = this.value as A

inline fun <reified A> Either<A, *>.aOrNull(): A? = fold( { it }, { null } )
inline fun <reified B> Either<*, B>.bOrNull(): B? = fold( { null }, { it } ) // feels suss

inline fun <reified A, B, R> Either<A, B>.fold(
    fa: (A) -> R,
    fb: (B) -> R
) = when (this.value) {
    is A -> fa(value)
    else -> fb(value as B)
}

inline fun <reified A, B, R> Either<A, B>.mapA(f: (A) -> R): Either<R, B> =
    when (this.value) {
        is A -> f(value).bindA()
        else -> this as Either<R, B>
    }

inline fun <reified A, B, R> Either<A, B>.mapB(f: (B) -> R): Either<A, R> =
    when (this.value) {
        is A -> this as Either<A, R>
        else -> f(value as B).bindB()
    }
