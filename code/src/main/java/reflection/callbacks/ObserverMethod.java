package reflection.callbacks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

public class ObserverMethod implements Observer {
    private final Object target;
    private final Method method;

    public ObserverMethod(Object target, String methodName, Class argumentType) throws NoSuchMethodException {
        this(
            target,
            target.getClass().getMethod(
                methodName,
                new Class[]{argumentType}));
    }

    public ObserverMethod(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            method.invoke(target, new Object[]{arg});
        } catch (IllegalAccessException e) {
            throw new UnsupportedOperationException("cannot invoke observer method", e);
        } catch (InvocationTargetException e) {
            throw new UnsupportedOperationException("error thrown by observer method", e.getTargetException());
        }
    }
}
