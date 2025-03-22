package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Cinema {
    private ArrayList<Session> sessions;

    public Cinema() {
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public ArrayList<Session> getSessions() {
        return sessions;
    }

    public boolean removeSession(Session session) {
        return sessions.remove(session);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cinema cinema = (Cinema) o;
        return Objects.equals(sessions, cinema.sessions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sessions);
    }
}
