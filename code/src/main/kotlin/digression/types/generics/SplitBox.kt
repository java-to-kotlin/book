package digression.types.generics


interface Source<out T> {
    fun get(): T
}

interface Store<in T> {
    fun put(item: T)
}

class Box<T>: Source<T>, Store<T> {
    private val items = mutableListOf<T>()
    
    override fun get(): T {
        val item = items.last()
        items.removeAt(items.size-1)
        return item
    }
    
    override fun put(item: T) {
        items.add(item)
    }
}


//fun foo() {
//    val box = Box<Apple>()
//    val apples : Store<Apple> = box
//    val oranges : Source<Orange> = box
//}