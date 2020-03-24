@file:Suppress("RedundantUnitReturnType", "RedundantUnitExpression")

package example

object ImpliedUnit {
fun example() {
    println("Hello, KotlinConf")
}
}


object SingleExprFun {
fun example() = println("Hello, KotlinConf")
}

object ExplicitUnit {
fun example(): Unit {
    println("Hello, KotlinConf")
    return Unit
}
}
