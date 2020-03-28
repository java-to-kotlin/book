package chapter1.java;

import java.util.List;
import java.util.Optional;

public class StreamSessions {

    public static Optional<Session> findSessionWithTitle(List<Session> sessions, String title) {
        return sessions.stream().filter(session -> session.title.equals(title)).findFirst();
    }
}
