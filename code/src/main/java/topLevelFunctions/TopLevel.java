package topLevelFunctions;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.max;

public class TopLevel {

    private static List<Integer> list = List.of(1, 2, 3);

    public static void scopedMax() {
        /// begin: scopedMax
        var max = Collections.max(list);
        /// end: scopedMax
    }

    public static void importedMax() {
        /// begin: importedMax
        var m = max(list);
        /// end: importedMax
    }
}

