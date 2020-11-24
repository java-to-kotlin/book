@file:Suppress("unused", "NonAsciiCharacters")
package digression.types.interfaces


interface Ripens {
    val ripeness: Double
}

abstract class Fruit : Ripens { /* ... */ }
class Apple(override val ripeness: Double) : Fruit()
class Banana(override val ripeness: Double) : Fruit()

// Code that depends on interfaces only cares about the type of values, not the class.

fun Ripens.isReadyForSale() =
    ripeness > 0.75 && ripeness <= 1.0

fun <T: Ripens> readyForSale(items: Iterable<T>): List<T> =
    items.filter { it.isReadyForSale() }


// We can add new classes that implement the same type, with their own hierarchy

abstract class Cheese { /* ... */ }
class Brie(override val ripeness: Double) : Cheese(), Ripens
class Comté(override val ripeness: Double) : Cheese(), Ripens

// In this style, class inheritance is used for inheriting implementation, not subtyping
