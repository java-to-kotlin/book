package chapter1.kotlin

import java.util.*

class Session(val title: String, presenters: List<Presenter>?) { // <1>
    val presenters: List<Presenter> // <2>

    fun withPresenters(newLineUp: List<Presenter>?): Session { // <4>
        return Session(title, newLineUp)
    }

    fun withTitle(newTitle: String): Session {
        return Session(newTitle, presenters)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val session = o as Session
        return title == session.title &&
            presenters == session.presenters
    }

    override fun hashCode(): Int {
        return Objects.hash(title, presenters)
    }

    override fun toString(): String {
        return "chapter1.java.Session{" +
            "title='" + title + '\'' +
            ", presenters=" + presenters +
            '}'
    }

    init {
        this.presenters = java.util.List.copyOf(presenters) // <3>
    }
}