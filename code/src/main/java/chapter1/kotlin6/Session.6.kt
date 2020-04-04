package chapter1.kotlin6

import chapter1.java.Presenter

data class Session(
    val title: String,
    val presenters: List<Presenter>
) {
    /// begin: foo
    fun withPresenters(newLineUp: List<Presenter>): Session {
        return copy(presenters = newLineUp) /// change
    }

    fun withTitle(newTitle: String): Session {
        return copy(title = newTitle) /// change
    }
    /// end: foo
}