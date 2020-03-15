package chapter1.java;

import java.util.List;
import java.util.Objects;


public class Session {

    public final String title;
    public final List<Presenter> presenters; // <1>

    public Session(String title, List<Presenter> presenters) {
        this.title = title;
        this.presenters = List.copyOf(presenters); // <2>
    }

    public String getTitle() {
        return title;
    }

    public List<Presenter> getPresenters() {
        return presenters;
    }

    // <3>
    public Session withPresenters(List<Presenter> newLineUp) {
        return new Session(title, newLineUp);
    }

    public Session withTitle(String newTitle) {
        return new Session(newTitle, presenters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(title, session.title) &&
            Objects.equals(presenters, session.presenters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, presenters);
    }

    @Override
    public String toString() {
        return "chapter1.java.Session{" +
            "title='" + title + '\'' +
            ", presenters=" + presenters +
            '}';
    }
}
