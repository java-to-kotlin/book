package chapter1.kotlin2

import chapter1.java.Presenter
import java.util.*

class Session(val title: String, presenters: List<Presenter>) {

    val presenters : List<Presenter> = java.util.List.copyOf(presenters)

    fun withPresenters(newLineUp: List<Presenter>): Session {
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
}