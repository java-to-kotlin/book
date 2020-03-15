package chapter1.kotlin

import chapter1.java.Presenter
import java.util.*

class Session(
    val title: String,
    val subtitleOrNull: String?,
    presenters: List<Presenter>
) {

    val presenters : List<Presenter>

    constructor(title: String, subtitle: String?, vararg presenters: Presenter) : this(
        title,
        subtitle,
        presenters.asList()
    )

    fun withPresenters(newLineUp: List<Presenter>): Session {
        return Session(title, subtitleOrNull, newLineUp)
    }

    fun withTitle(newTitle: String): Session {
        return Session(newTitle, subtitleOrNull, presenters)
    }

    fun withSubtitle(newSubtitle: String?): Session {
        return Session(title, newSubtitle, presenters)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val session = o as Session
        return title == session.title &&
            subtitleOrNull == session.subtitleOrNull &&
            presenters == session.presenters
    }

    override fun hashCode(): Int {
        return Objects.hash(title, subtitleOrNull, presenters)
    }

    override fun toString(): String {
        return "chapter1.java.Session{" +
            "title='" + title + '\'' +
            ", subtitle='" + subtitleOrNull + '\'' +
            ", presenters=" + presenters +
            '}'
    }

    init {
        this.presenters = Collections.unmodifiableList(ArrayList(presenters))
    }
}