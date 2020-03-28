package chapter1.java;


import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionsTests {

    private List<Session> sessions = List.of(
        new Session("Title", emptyList()),
        new Session("Title Too", emptyList())
        );

    @Test
    public void find_session_with_title() {
        assertEquals(
            Optional.of(sessions.get(1)),
            Sessions.findSessionWithTitle(sessions, "Title Too")
        );
    }

    @Test
    public void find_no_session_with_title() {
        assertEquals(
            Optional.empty(),
            Sessions.findSessionWithTitle(sessions, "No such")
        );
    }

    @Test
    public void find_in_no_sessions() {
        assertEquals(
            Optional.empty(),
            Sessions.findSessionWithTitle(emptyList(), "No such")
        );
    }
}
