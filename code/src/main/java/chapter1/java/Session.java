package chapter1.java;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;


public class Session {
    public final String title;
    @Nullable
    public final String subtitleOrNull;
    public final Slots slots;
    public final List<Presenter> presenters;

    public Session(String title, @Nullable String subtitle, Slots slots, List<Presenter> presenters) {
        this.title = title;
        this.subtitleOrNull = subtitle;
        this.slots = slots;
        this.presenters = Collections.unmodifiableList(new ArrayList<>(presenters));
    }

    public Session(String title, @Nullable String subtitle, Slots slots, Presenter... presenters) {
        this(title, subtitle, slots, asList(presenters));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(title, session.title) &&
                Objects.equals(subtitleOrNull, session.subtitleOrNull) &&
                Objects.equals(slots, session.slots) &&
                Objects.equals(presenters, session.presenters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, subtitleOrNull, slots, presenters);
    }

    @Override
    public String toString() {
        return "chapter1.java.Session{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitleOrNull + '\'' +
                ", slots=" + slots +
                ", presenters=" + presenters +
                '}';
    }

    public Session withPresenters(List<Presenter> newLineUp) {
        return new Session(title, subtitleOrNull, slots, newLineUp);
    }

    public Session withTitle(String newTitle) {
        return new Session(newTitle, subtitleOrNull, slots, presenters);
    }

    public Session withSubtitle(@Nullable String newSubtitle) {
        return new Session(title, newSubtitle, slots, presenters);
    }
}
