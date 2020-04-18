package digression.types.generics


interface Box<T> {
    fun isEmpty(): Boolean
    fun take(): T
    fun put(element: T)
}
