package chapter1.kotlin3

import chapter1.java.Presenter

data class Session(
    val title: String,
    val presenters: List<Presenter>
) {

    fun withPresenters(newLineUp: List<Presenter>): Session {
        return Session(title, newLineUp)
    }

    fun withTitle(newTitle: String): Session {
        return Session(newTitle, presenters)
    }
}