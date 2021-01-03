package reflection.callbacks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

public class CountObserverTest {
    @Test
    public void example() throws NoSuchMethodException, InterruptedException {
        StringWriter writer = new StringWriter();
        CountPrinter printer = new CountPrinter(new PrintWriter(writer));

        Counter counter = new Counter();
        counter.addObserver(new ObserverMethod(printer, "onCount", int.class));

        for (int i = 0; i < 4; i++) {
            counter.increment();
        }

        Assertions.assertEquals(
            "count is now 1\n" +
                "count is now 2\n" +
                "count is now 3\n" +
                "count is now 4\n",
            writer.toString()
        );
    }
}
