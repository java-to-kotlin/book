package exhaustive_when

sealed class Sealed
object SubclassA : Sealed()
object SubclassB : Sealed()
//object SubclassC : Sealed()

/// begin: exhaustive_not
operator fun Unit.not() = this

fun a(x: Sealed) {
    !when (x) {
        is SubclassA -> println("A")
        is SubclassB -> println("B")
    }
}
/// end: exhaustive_not

/// begin: exhaustive_val
val Unit.exhaustive get() = this

fun b(x: Sealed) {
    when (x) {
        is SubclassA -> println("A")
        is SubclassB -> println("B")
    }.exhaustive
}
/// end: exhaustive_val

/// begin: exhaustive_typealias
typealias exhaustive = Unit

fun c(x: Sealed) {
    when (x) {
        is SubclassA -> println("A")
        is SubclassB -> println("B")
    } as exhaustive
}
/// end: exhaustive_typealias

/// begin: exhaustive_apply
fun d(x: Sealed) {
    when (x) {
        is SubclassA -> println("A")
        is SubclassB -> println("B")
    }.apply {  }
}
/// end: exhaustive_apply

/// begin: exhaustive_single_expression_form
fun e(x: Sealed): Unit = when (x) {
    is SubclassA -> println("A")
    is SubclassB -> println("B")
}
/// end: exhaustive_single_expression_form

/// begin: exhaustive_return
fun f(x: Sealed) {
    return when (x) {
        is SubclassA -> println("A")
        is SubclassB -> println("B")
    }
}
/// end: exhaustive_return
