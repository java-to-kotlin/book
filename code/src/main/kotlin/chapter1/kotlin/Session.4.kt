package chapter1.kotlin4

import chapter1.java.Presenter

data class Session(
    val title: String,
    val presenters: List<Presenter>
) {
    /// begin: foo
    fun withPresenters(newLineUp: List<Presenter>): Session {
        return Session(title = title, presenters = newLineUp)
    }

    fun withTitle(newTitle: String): Session {
        return Session(title = newTitle, presenters = presenters)
    }
    /// end: foo
}