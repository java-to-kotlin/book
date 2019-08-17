package chapter1.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PresenterTests {

    private Presenter nat = new Presenter("1", "Nat");
    private Presenter duncan = new Presenter("2", "Duncan");

    @Test
    void properties() {
        assertEquals("1", nat.getId());
        assertEquals("Duncan", duncan.getName());
    }

    @Test
    void equality() {
        assertEquals(nat, new Presenter("1", "Nat"));
        assertNotEquals(nat, duncan);
    }

    @Test
    void testHashCode() {
        assertEquals(nat.hashCode(), new Presenter("1", "Nat").hashCode());
        assertNotEquals(nat.hashCode(), duncan.hashCode());
    }
}