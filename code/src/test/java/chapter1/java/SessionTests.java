package chapter1.java;


import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SessionTests {

    private static Presenter alice = new Presenter("id1", "Alice");
    private static Presenter bob = new Presenter("id2", "Bob");
    private final List<Presenter> noPresenters = Collections.emptyList();

    private Session aSession = new Session("The Title", noPresenters);

    @Test
    public void can_change_title() {
        assertThat(
            aSession.withTitle("Another Title"),
            equalTo(new Session("Another Title", noPresenters)));
    }

    @Test
    public void can_change_presenters() {
        assertThat(
            aSession.withPresenters(asList(bob, alice)),
            equalTo(new Session("The Title", List.of(bob, alice))));
    }
}
