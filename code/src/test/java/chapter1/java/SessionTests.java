package chapter1.java;


import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SessionTests {

    static Presenter alice = new Presenter("id1", "Alice");
    static Presenter bob = new Presenter("id2", "Bob");
    static Presenter carol = new Presenter("id3", "Carol");

    Session aSession = new Session("The Title", null);

    @Test
    public void can_change_presenters() {
        assertThat(
            aSession.withPresenters(asList(bob, carol)),
            equalTo(new Session("The Title", null, bob, carol)));
    }

    @Test
    public void can_change_title() {
        assertThat(
            aSession.withTitle("Another Title"),
            equalTo(new Session("Another Title", null, alice)));
    }

    @Test
    public void can_change_subtitle() {
        assertThat(
            aSession.withSubtitle("The Subtitle"),
            equalTo(new Session("The Title", "The Subtitle", alice)));
    }

    @Test
    public void can_remove_subtitle() {
        assertThat(
            aSession.withSubtitle("The Subtitle").withSubtitle(null),
            equalTo(new Session("The Title", null, alice)));
    }
}
