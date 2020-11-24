@file:Suppress("UNREACHABLE_CODE", "unused")

package digression.types.generics.no_variance

import digression.types.generics.Fruit

/// begin: missing_variance
interface Source<T> {
    fun take(): T
}

/// note: missing_variance [...]

class AutomaticJuicer(val source: Source<Fruit>)

/// mute: missing_variance [...]

/*
fun this_should_not_compile() {
/// resume: missing_variance
val apples: Hopper<Apple> = example()
val juicer = AutomaticJuicer(apples)
/// end: missing_variance
}
*/
