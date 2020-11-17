package topLevelFunctions

fun topLevelFun() {}

val topLevelVal = 42

class Scope(private val thing: Int) {

    fun `can see private state of other objects`() {
        val s2 = Scope(42)
        s2.thing
    }

    companion object {
        fun util(scope: Scope) {
            scope.thing
        }
    }
}

fun topLevelUtil(scope: Scope) {
    // scope.thing
}