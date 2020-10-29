package collections

inline fun <T, R> Iterable<T>.map(transform: (T) -> R): List<R> {
    val result = ArrayList<R>()
    for (item in this)
        result.add(transform(item))
    return result
}