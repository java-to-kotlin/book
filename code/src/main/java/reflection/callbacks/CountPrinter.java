package reflection.callbacks;

import java.io.PrintWriter;

public class CountPrinter {
    private final PrintWriter out;

    public CountPrinter() {
        this(new PrintWriter(System.out, true));
    }

    public CountPrinter(PrintWriter out) {
        this.out = out;
    }

    public void onCount(int i) {
        out.println("count is now " + i);
    }
}
