package chapter1.java;

import java.util.List;
import java.util.Optional;

public class Sessions {

    /// mute: stream
    public static Optional<Session> findSessionWithTitle(List<Session> sessions, String title) {
        for (Session session : sessions) {
            if (session.title.equals(title))
                return Optional.of(session);
        }
        return Optional.empty();
    }
    /// resume: stream

    /// mute: for
    public static Optional<Session> findSessionWithTitle2(List<Session> sessions, String title) {
        return sessions.stream().filter(session -> session.title.equals(title)).findFirst();
    }
    /// resume: for
}
