package grain.java5;

import java.util.ArrayList;
import java.util.List;

/// begin: fold
public class Lists {
    public static <A,R> R reduce(List<A> l, R initial, Function2<R, A, R> f) {
        R result = initial;
        for (A a : l) {
            result = f.apply(result, a);
        }
        return result;
    }

    /// mute: fold
    public static <A,R> List<R> map(List<A> l, Function1<A, R> f) {
        List<R> result = new ArrayList<R>();
        for (A a : l) {
            result.add(f.apply(a));
        }
        return result;
    }

    public static <A,R> List<R> flatMap(List<A> l, Function1<A, List<R>> f) {
        List<R> result = new ArrayList<R>();
        for (A a : l) {
            result.addAll(f.apply(a));
        }
        return result;
    }
    /// resume: fold
}
/// end: fold
