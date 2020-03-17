package chapter1.kotlin6

import chapter1.java.Presenter

data class Session(
    val title: String,
    val presenters: List<Presenter>
) {

    fun withPresenters(newLineUp: List<Presenter>): Session {
        return copy(presenters = newLineUp)
    }

    fun withTitle(newTitle: String): Session {
        return copy(title = newTitle)
    }
}