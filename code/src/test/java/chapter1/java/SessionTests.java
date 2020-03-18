package chapter1.java;


import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionTests {

    private static Presenter alice = new Presenter("id1", "Alice");
    private static Presenter bob = new Presenter("id2", "Bob");
    private static final List<Presenter> noPresenters = Collections.emptyList();

    private Session aSession = new Session("The Title", List.of(bob, alice)); // <1>

    @Test
    public void can_change_title() {
        assertEquals(
            new Session("Another Title", List.of(bob, alice)),
            aSession.withTitle("Another Title")
        );
    }

    @Test
    public void can_change_presenters() {
        assertEquals(
            new Session("The Title", noPresenters),
            aSession.withPresenters(noPresenters)
        );
    }
}
