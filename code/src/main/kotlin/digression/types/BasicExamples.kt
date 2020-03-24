@file:Suppress("UNUSED_VARIABLE")

package example



interface Fruit {
    val ripeness: Double
}

fun canSell(fruit: Fruit) =
    fruit.ripeness in (0.5..1.0)

