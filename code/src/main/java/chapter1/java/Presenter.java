package chapter1.java;

import java.util.Objects;

/// begin: replaced_by_primary_constructor
public class Presenter {
    private final String id;   /// note: !replaced_by_primary_constructor [// <1>]
    private final String name; /// note: !replaced_by_primary_constructor [// <1>]

    public Presenter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { /// note: !replaced_by_primary_constructor [// <2>]
        return id;
    }

    public String getName() { /// note: !replaced_by_primary_constructor [// <2>]
        return name;
    }

    /// note: replaced_by_primary_constructor [...]
    /// end: replaced_by_primary_constructor
    @Override  /// note: !replaced_by_primary_constructor [// <3>]
    public String toString() {
        return "chapter1.java.Presenter{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }

    @Override  /// note: !replaced_by_primary_constructor [// <3>]
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presenter presenter = (Presenter) o;
        return id.equals(presenter.id) && // <3>
            name.equals(presenter.name);
    }

    @Override  /// note: !replaced_by_primary_constructor [// <3>]
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
