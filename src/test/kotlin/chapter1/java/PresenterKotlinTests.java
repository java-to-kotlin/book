package chapter1.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import chapter1.B.Presenter;

class PresenterKotlinTests {

    private Presenter nat = new Presenter("1", "Nat");

    @Test
    void copying() {
        var renamedNat = nat.copy(nat.getId(), nat.getName());
        assertEquals(new Presenter(nat.getId(), "Nathaniel"), renamedNat);

        var differentPresenterSameName = nat.copy("3", nat.getName());
        assertEquals(new Presenter("3", "Nat"), differentPresenterSameName);

        var allChange = nat.copy("4", "Bob");
        assertEquals(new Presenter("4", "Bob"), allChange);
    }
}