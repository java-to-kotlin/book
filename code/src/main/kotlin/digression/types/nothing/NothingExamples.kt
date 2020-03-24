package digression.types.nothing


inline fun forever(block: ()->Unit): Nothing {
    while(true) {
        block()
    }
}


fun forever_example() {
forever {
    val m = receiveMessage()
    dispatchMessage(m)
}
println("finished")
}




fun dispatchMessage(m: Any): Unit {
}

fun receiveMessage(): Unit {
}



val x: Nothing? = null

class NoGlassesSold : Exception()


fun calculateIt(orangesUsed: Int, glassesSold: Int) {
    val fruitPerGlass: Int =
        if (glassesSold > 0) orangesUsed/glassesSold else throw NoGlassesSold()
}

fun generateReport(orangesUsed: Int, glassesSold: Int): String {
    val fruitPerGlass = if (glassesSold > 0) orangesUsed/glassesSold else return "no juices sold"
    
    TODO()
}
