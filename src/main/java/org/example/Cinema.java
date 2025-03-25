package org.example;

import java.util.ArrayList;
import java.util.Objects;

public class Cinema {
    private ArrayList<Session> sessions;

    public Cinema() {
        this.sessions = new ArrayList<>();
    }

    public Cinema(ArrayList<Session> sessions) {
        this.sessions = sessions;
    }

    public Session findMovieByName(String name) throws Exception {
        for (Session session : sessions) {
            if (session.getMovieName().equals(name)) {
                return session;
            }
        }

        throw new Exception(String.format("Failed to find a movie with the name '%s'", name));
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

    public void setSessions(ArrayList<Session> sessions) {
        this.sessions = sessions;
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
