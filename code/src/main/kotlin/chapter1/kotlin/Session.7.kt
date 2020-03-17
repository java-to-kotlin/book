package chapter1.kotlin7

import chapter1.kotlin.Presenter

data class Session(
    val title: String,
    val presenters: List<Presenter>
) {

    fun withPresenters(newLineUp: List<Presenter>) = copy(presenters = newLineUp)

    fun withTitle(newTitle: String) = copy(title = newTitle)
}