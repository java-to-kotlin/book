@file:Suppress("unused")

package digression.types


/// begin: user_defined_type
class Fruit(val ripeness: Double)
/// end: user_defined_type

fun canSell(fruit: Fruit) =
    fruit.ripeness in (0.5..1.0)

