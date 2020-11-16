package single_expression_functions

/// begin: add
fun add(a: Int, b: Int): Int {
    return a + b
}
/// end: add

/// begin: addToo
fun addToo(a: Int, b: Int): Int = a + b
/// end: addToo

/// begin: max
fun max(a: Int, b: Int): Int =
    when {
        a > b -> a
        else -> b
    }
/// end: max

/// begin: printTwice
fun printTwice(s: String): Unit = println("$s\n$s")
/// begin: printTwice