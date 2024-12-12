package my.server;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    private List<Session> sessions = new ArrayList<>();

    synchronized void add(Session session) {
        sessions.add(session);
    }

    synchronized void remove(Session session) {
        sessions.remove(session);
    }

    synchronized void closeAll() {
        for (Session session: sessions) {
            session.close();
        }
        sessions.clear();
    }

    synchronized void sendAll(String message) {
        for (Session session: sessions) {
            session.send(message);
        }
    }

    public synchronized List<String> getNames() {
        return sessions.stream()
                .map(Session::getName)
                .toList();
    }
}
