package grain.java2;

import java.util.ArrayList;
import java.util.List;

/// begin: reduce
public class Lists {
    public static Object reduce(List l, Object initial, Function2 f) {
        Object result = initial;
        for (int i = 0; i < l.size(); i++) {
            result = f.apply(result, l.get(i));
        }
        return result;
    }

    /// mute: reduce
    public static List map(List l, Function1 f) {
        List result = new ArrayList();
        for (int i = 0; i < l.size(); i++) {
            result.add(f.apply(l.get(i)));
        }
        return result;
    }

    public static List flatMap(List l, Function1 f) {
        List result = new ArrayList();
        for (int i = 0; i < l.size(); i++) {
            result.addAll((List)f.apply(l.get(i)));
        }
        return result;
    }
    /// resume: reduce
}
/// end: reduce
