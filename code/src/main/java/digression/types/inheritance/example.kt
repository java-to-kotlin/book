@file:Suppress("unused")

package digression.types.inheritance

/// begin: inheritance
abstract class Fruit {
    abstract val ripeness: Double
}

class Banana(override val ripeness: Double, val bendiness: Double): Fruit()
class Peach(override val ripeness: Double, val fuzziness: Double): Fruit()
/// end: inheritance
