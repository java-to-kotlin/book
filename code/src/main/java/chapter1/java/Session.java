package chapter1.java;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;


public class Session {

    public final String title;
    @Nullable // <1>
    public final String subtitleOrNull;
    public final List<Presenter> presenters; // <2>

    public Session(String title, @Nullable String subtitle, List<Presenter> presenters) {
        this.title = title;
        this.subtitleOrNull = subtitle;
        this.presenters = Collections.unmodifiableList(new ArrayList<>(presenters)); // <3>
    }

    public String getTitle() {
        return title;
    }

    @Nullable
    public String getSubtitleOrNull() {
        return subtitleOrNull;
    }

    public List<Presenter> getPresenters() {
        return presenters;
    }

    // <4>
    public Session withPresenters(List<Presenter> newLineUp) {
        return new Session(title, subtitleOrNull, newLineUp);
    }

    public Session withTitle(String newTitle) {
        return new Session(newTitle, subtitleOrNull, presenters);
    }

    public Session withSubtitle(@Nullable String newSubtitle) {
        return new Session(title, newSubtitle, presenters);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(title, session.title) &&
                Objects.equals(subtitleOrNull, session.subtitleOrNull) &&
                Objects.equals(presenters, session.presenters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, subtitleOrNull, presenters);
    }

    @Override
    public String toString() {
        return "chapter1.java.Session{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitleOrNull + '\'' +
                ", presenters=" + presenters +
                '}';
    }
}
