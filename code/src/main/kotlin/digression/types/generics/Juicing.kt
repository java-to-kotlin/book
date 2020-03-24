@file:Suppress("UNREACHABLE_CODE", "UNUSED_VARIABLE")

package digression.types.generics



abstract class Fruit
class Apple : Fruit()
class Orange: Fruit()

class Liquid

interface Juicer<in SOLID> {
    fun juice(s: SOLID): Liquid
}

interface DomesticJuicer : Juicer<Fruit>

interface CateringOrangeSqueezer: Juicer<Orange>

val example: Nothing get() = TODO()

fun `this works`() {

val fruitJuicer: Juicer<Fruit> = TODO()
val orangeJuicer: Juicer<Orange> = fruitJuicer

}

//fun `this does not compile`() {
//
//val orangeJuicer: Juicer<Orange> = example
//val fruitJuicer: Juicer<Fruit> = orangeJuicer
//
//}
