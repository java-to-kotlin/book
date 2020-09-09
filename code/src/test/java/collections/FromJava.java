package collections;

import java.util.Iterator;
import java.util.List;

public class FromJava {
    public static <T> void removeElement(List<T> list, int index) {
        list.remove(index);
    }

    public static <T> void removeElement(Iterator<T> i) {
        i.remove();
    }
}
