package extensionFunctions;

import static extensionFunctions.A.*;

public class SomeJava {

    public static void code(Customer customer) {
        /// begin: callingExt
        var s = Extension_functionsKt.nameForMarketing(customer); // <1>
        /// end: callingExt
    }
}
