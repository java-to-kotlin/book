@file:Suppress("RedundantUnitReturnType", "RedundantUnitExpression")

package digression.types

object ImpliedUnit {
/// begin: implied_unit
fun example() {
    println("Hello, KotlinConf")
}
/// end: implied_unit
}


object SingleExprFun {
/// begin: single_unit_expr
fun example() = println("Hello, KotlinConf")
/// end: single_unit_expr
}

object ExplicitUnit {
///begin: explicit_unit
fun example(): Unit {
    println("Hello, KotlinConf")
    return Unit
}
///end: explicit_unit
}
