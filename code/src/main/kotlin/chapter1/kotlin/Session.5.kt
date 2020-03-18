package chapter1.kotlin5

import chapter1.java.Presenter

data class Session(
    val title: String,
    val presenters: List<Presenter>
) {
    /// begin: foo
    fun withPresenters(newLineUp: List<Presenter>): Session {
        return copy(title = title, presenters = newLineUp)
    }

    fun withTitle(newTitle: String): Session {
        return copy(title = newTitle, presenters = presenters)
    }
    /// end: foo
}