@file:Suppress("UNREACHABLE_CODE", "UNUSED_VARIABLE")

package digression.types.generics


interface Hopper<T> {
    fun takeOne(): T
}

class AutomaticJuicer(val source: Hopper<Fruit>)

//fun this_should_not_compile() {
//val apples: Hopper<Apple> = example()
//val juicer = AutomaticJuicer(apples)
//}
