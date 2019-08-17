package chapter1.java;

import java.util.Objects;

public class Presenter {

    private final String id;
    private final String name;

    public Presenter(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Presenter{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Presenter presenter = (Presenter) o;
        return id.equals(presenter.id) &&
                name.equals(presenter.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
