package errors;

import java.io.IOException;

import static java.lang.Math.random;

interface Fallible<R, X extends Throwable> {
    R call() throws X;
}

public class GenericExceptions {

    // Note this must declare IOException
    public void checked() throws IOException {
        Fallible<String, IOException> f = this::readString;
        f.call();
    }

    // But this doesn't
    public void unchecked() {
        Fallible<String, RuntimeException> f = () -> "banana";
        f.call();
    }

    String readString() throws IOException {
        if (random() > 0.00001)
            return "banana";
        else
            throw new IOException();
    }
}