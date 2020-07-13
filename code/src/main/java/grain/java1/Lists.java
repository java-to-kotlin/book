package grain.java1;

import java.util.ArrayList;
import java.util.List;

/// begin: fold
public class Lists {
    public static Object fold(Function2 f, Object initial, List l) {
        Object result = initial;
        for (int i = 0; i < l.size(); i++) {
            result = f.apply(result, l.get(i));
        }
        return result;
    }

    /// mute: fold
    public static List map(Function1 f, List l) {
        List result = new ArrayList();
        for (int i = 0; i < l.size(); i++) {
            result.add(f.apply(l.get(i)));
        }
        return result;
    }

    public static List flatMap(Function1 f, List l) {
        List result = new ArrayList();
        for (int i = 0; i < l.size(); i++) {
            result.addAll((List)f.apply(l.get(i)));
        }
        return result;
    }
    /// resume: fold
}
/// end: fold
