package collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SomeJavaCode {
    public static <T> void removeElement(List<T> list, int index) {
        list.remove(index);
    }

    public static <T> void replaceElement(List<T> list, int index, T value) {
        list.set(index, value);
    }

    public static <T> void removeElement(Iterator<T> i) {
        i.remove();
    }

    public static <T> void setElement(List<T> list, int index, T newValue) {
        list.set(index, newValue);
    }

    public static <T> void needsAList(List<T> list) {

    }

    public static List<String> mutableListOfStrings(String... strings) {
        return new ArrayList<>(List.of(strings));
    }
}
