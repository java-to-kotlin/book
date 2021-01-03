package reflection.callbacks;

import java.util.Observable;

public class Counter extends Observable {
    private int count = 0;

    public void increment() {
        count++;
        setChanged();
        notifyObservers(count);
    }
}
