package grain.java1;

public class StringToUpperCase implements Function1 {
    public Object apply(Object arg) {
        return ((String) arg).toUpperCase();
    }
}
