@file:Suppress("unused", "UNUSED_VARIABLE")

package digression.types.generics.split_variance

import digression.example
import digression.types.generics.Apple
import digression.types.generics.Fruit
import digression.types.generics.Orange

interface Source<out T> {
    fun isEmpty(): Boolean
    fun take(): T
}

interface Store<in T> {
    fun put(item: T)
}

interface Box<T>: Source<T>, Store<T>


fun foo() {
val box : Box<Apple> = example()
val apples : Store<Apple> = box
val fruit : Source<Fruit> = box

//    should not not compile
//    val oranges : Source<Orange> = box
}