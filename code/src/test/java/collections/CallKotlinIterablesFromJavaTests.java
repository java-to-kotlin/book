package collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CallKotlinIterablesFromJavaTests {

    @Test public void test() {
        Iterable<Integer> list = List.of(1, 2, 3);
        assertEquals(
            6,
            kotlin.collections.CollectionsKt.sumOfInt(list)
        );
        assertEquals(
            List.of(2, 4, 6),
            kotlin.collections.CollectionsKt.map(list, (it) -> 2 * it)
        );
    }
}
