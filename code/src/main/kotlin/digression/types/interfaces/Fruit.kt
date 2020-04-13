@file:Suppress("unused")

package digression.types.interfaces


interface Ripens {
    val ripeness: Double
}


abstract class Fruit : Ripens { /* ... */ }
class Apple(override val ripeness: Double) : Fruit()
class Orange(override val ripeness: Double) : Fruit()


// Code that relies on interfaces only cares about the type of values, not the class.

fun readyForSale(item: Ripens) =
    item.ripeness > 0.75 && item.ripeness <= 1.0

fun <T: Ripens> sellable(items: Iterable<T>): List<T> = items.filter(::readyForSale)


// We can easily add new classes that implement the same type, with their own hierarchy

abstract class Cheese { /* ... */ }
class Brie(override val ripeness: Double) : Cheese(), Ripens

// In this style, class inheritance is used for inheriting implementation, not subtyping
