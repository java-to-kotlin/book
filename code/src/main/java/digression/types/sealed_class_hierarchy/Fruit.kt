@file:Suppress("unused")

package digression.types.sealed_class_hierarchy


sealed class Fruit {
    abstract val ripeness: Double
}

data class Apple(override val ripeness: Double): Fruit()
data class Orange(override val ripeness: Double): Fruit()

fun naam(f :Fruit): String {
    return when (f) {
        is Apple -> "appel"
        is Orange -> "oranje"
    }
}
