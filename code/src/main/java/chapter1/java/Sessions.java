package chapter1.java;

import java.util.List;
import java.util.Optional;

public class Sessions {

    public static Optional<Session> findSessionWithTitle(List<Session> sessions, String title) {
        for (Session session : sessions) {
            if (session.title.equals(title))
                return Optional.of(session);
        }
        return Optional.empty();
    }
}