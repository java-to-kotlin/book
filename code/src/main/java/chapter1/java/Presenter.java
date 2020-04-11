package chapter1.java;

import java.util.Objects;

/// begin: replaced_by_primary_constructor
public class Presenter {
    private final String id;   // <1>
    private final String name; // <1>

    public Presenter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // <2>
    public String getId() {
        return id;
    }

    // <2>
    public String getName() {
        return name;
    }

    /// note: replaced_by_primary_constructor [...]
    /// end: replaced_by_primary_constructor
    // <3>
    @Override
    public String toString() {
        return "chapter1.java.Presenter{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }

    // <3>
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presenter presenter = (Presenter) o;
        return id.equals(presenter.id) && // <3>
            name.equals(presenter.name);
    }

    // <3>
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
