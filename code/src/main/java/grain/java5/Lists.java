package grain.java5;

import java.util.ArrayList;
import java.util.List;

/// begin: fold
public class Lists {
    public static <A,R> R fold(Function2<R,A,R> f, R initial, List<A> l) {
        R result = initial;
        for (A a : l) {
            result = f.apply(result, a);
        }
        return result;
    }

    /// mute: fold
    public static <A,R> List<R> map(Function1<A,R> f, List<A> l) {
        List<R> result = new ArrayList<R>();
        for (A a : l) {
            result.add(f.apply(a));
        }
        return result;
    }

    public static <A,R> List<R> flatMap(Function1<A,List<R>> f, List<A> l) {
        List<R> result = new ArrayList<R>();
        for (A a : l) {
            result.addAll(f.apply(a));
        }
        return result;
    }
    /// resume: fold
}
/// end: fold
