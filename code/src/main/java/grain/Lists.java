package grain;

import java.util.ArrayList;
import java.util.List;

public class Lists {
    public static List map(Function1 f, List l) {
        List result = new ArrayList();
        for (int i = 0; i < l.size(); i++) {
            result.add(f.apply(l.get(i)));
        }
        return result;
    }

    public static Object reduce(Monoid f, List l) {
        Object a = f.nil();
        for (int i = 0; i < l.size(); i++) {
            a = f.apply(a, l.get(i));
        }
        return a;
    }
}
