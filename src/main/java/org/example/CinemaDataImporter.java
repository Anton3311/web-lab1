package org.example;

import java.util.ArrayList;
import java.util.HashSet;

public class CinemaDataImporter {
    public ArrayList<Session> importSessionsFromCsv(String csvString, char separator) {
        String[] lines = csvString.lines().toArray(size -> new String[size]);

        String separatorString = String.valueOf(separator);

        String headersLine = lines[0];
        String[] headers = headersLine.split(separatorString);

        int movieNameColumnIndex = -1;
        int durationColumnIndex = -1;
        int numberOfTicketsColumnIndex = -1;
        int availableSeatsColumnIndex = -1;

        for (int i = 0; i < headers.length; i++) {
            String header = headers[i];
            if (header.equals("MovieName")) {
                movieNameColumnIndex = i;
            } else if (header.equals("Duration")) {
                durationColumnIndex = i;
            } else if (header.equals("NumberOfTickets")) {
                numberOfTicketsColumnIndex = i;
            } else if (header.equals("AvailableSeats")) {
                availableSeatsColumnIndex = i;
            }
        }

        ArrayList<Session> sessions = new ArrayList<>(lines.length - 1);
        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].split(separatorString);

            String movieName = parts[movieNameColumnIndex];
            int duration = Integer.parseInt(parts[durationColumnIndex]);
            int numberOfTickets = Integer.parseInt(parts[numberOfTicketsColumnIndex]);
            HashSet<Integer> availableSeats = parseAvailableSeats(parts[availableSeatsColumnIndex]);

            sessions.add(new Session(movieName, duration, numberOfTickets, availableSeats));
        }

        return sessions;
    }

    private HashSet<Integer> parseAvailableSeats(String string) {
        String subString = string.substring(1, string.length() - 1);
        String[] elements = subString.split(", ");

        HashSet<Integer> parsedElements = new HashSet<>();
        for (String element : elements) {
            parsedElements.add(Integer.parseInt(element));
        }

        return parsedElements;
    }
}
