package grain.java1;

import java.util.Vector;

/// begin: fold
public class Vectors {
    public static Object reduce(Vector l, Object initial, Function2 f) {
        Object result = initial;
        for (int i = 0; i < l.size(); i++) {
            result = f.apply(result, l.get(i));
        }
        return result;
    }

    /// mute: fold
    public static Vector map(Vector l, Function1 f) {
        Vector result = new Vector();
        for (int i = 0; i < l.size(); i++) {
            result.add(f.apply(l.get(i)));
        }
        return result;
    }

    public static Vector flatMap(Vector l, Function1 f) {
        Vector result = new Vector();
        for (int i = 0; i < l.size(); i++) {
            result.addAll((Vector)f.apply(l.get(i)));
        }
        return result;
    }
    /// resume: fold
}
/// end: fold
