package org.example;

import java.util.ArrayList;

public class CinemaDataExporter {
    public String exportToCsvString(Cinema cinema, SortCriteria sortCriteria) {
        ArrayList<Session> sessions;

        if (sortCriteria == SortCriteria.None) {
            sessions = cinema.getSessions();
        } else {
            sessions = new ArrayList<>(cinema.getSessions().size());

            for (Session session : cinema.getSessions()) {
                sessions.add(session);
            }

            switch (sortCriteria) {
                case SortCriteria.None:
                    break;
                case SortCriteria.Duration:
                    sessions.sort((a, b) -> Integer.compare(a.getDurationInMinutes(), b.getDurationInMinutes()));
                    break;
                case SortCriteria.Name:
                    sessions.sort((a, b) -> a.getMovieName().compareTo(b.getMovieName()));
                    break;
            }
        }

        StringBuilder stringBuilder = new StringBuilder();

        char separator = ';';

        stringBuilder.append("MovieName");
        stringBuilder.append(separator);
        stringBuilder.append("Duration");
        stringBuilder.append(separator);
        stringBuilder.append("NumberOfTickets");
        stringBuilder.append(separator);
        stringBuilder.append("AvailableSeats");
        stringBuilder.append(separator);
        stringBuilder.append('\n');

        for (int sessionIndex = 0; sessionIndex < sessions.size(); sessionIndex++) {
            Session session = sessions.get(sessionIndex);

            stringBuilder.append(session.getMovieName());
            stringBuilder.append(separator);
            stringBuilder.append(session.getDurationInMinutes());
            stringBuilder.append(separator);
            stringBuilder.append(session.getNumberOfTickets());
            stringBuilder.append(separator);
            stringBuilder.append(session.getAvailableSeats());
            stringBuilder.append(separator);

            if (sessionIndex < sessions.size() - 1) {
                stringBuilder.append('\n');
            }
        }

        return stringBuilder.toString();
    }
}
