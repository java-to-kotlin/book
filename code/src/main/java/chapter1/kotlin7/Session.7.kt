package chapter1.kotlin7

import chapter1.kotlin.Presenter

data class Session(
    val title: String,
    val presenters: List<Presenter>
) {
    /// begin: foo
    fun withPresenters(newLineUp: List<Presenter>) = copy(presenters = newLineUp) /// change

    fun withTitle(newTitle: String) = copy(title = newTitle) /// change
    /// end: foo
}