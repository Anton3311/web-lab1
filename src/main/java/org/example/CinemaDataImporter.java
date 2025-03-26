package org.example;

import java.util.ArrayList;
import java.util.HashSet;

public class CinemaDataImporter {
    public ArrayList<Session> importSessionsFromCsv(String csvString, char separator) throws Exception {
        String[] lines = csvString.lines().toArray(size -> new String[size]);

        String separatorString = String.valueOf(separator);

        String headersLine = lines[0];
        String[] headers = headersLine.split(separatorString);

        int movieNameColumnIndex = -1;
        int durationColumnIndex = -1;
        int numberOfTicketsColumnIndex = -1;
        int availableSeatsColumnIndex = -1;
		int ticketPriceColumnIndex = -1;

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
            } else if (header.equals("TicketPrice")) {
				ticketPriceColumnIndex = i;
			}
        }

        if (movieNameColumnIndex == -1
                || durationColumnIndex == -1
                || numberOfTicketsColumnIndex == -1
                || availableSeatsColumnIndex == -1
                || ticketPriceColumnIndex == -1) {
            throw new Exception("Not every column is present in the CSV string");
        }

        ArrayList<Session> sessions = new ArrayList<>(lines.length - 1);
        for (int i = 1; i < lines.length; i++) {
            String[] parts = lines[i].split(separatorString);

            if (parts.length != headers.length) {
                throw new Exception("Number of elements in the rows doesn't match the number of headers");
            }

            String movieName = parts[movieNameColumnIndex];
            int duration = Integer.parseInt(parts[durationColumnIndex]);
            int numberOfTickets = Integer.parseInt(parts[numberOfTicketsColumnIndex]);
			int ticketPrice = Integer.parseInt(parts[ticketPriceColumnIndex]);
            HashSet<Integer> availableSeats = parseAvailableSeats(parts[availableSeatsColumnIndex]);

            sessions.add(new Session(movieName, duration, ticketPrice, numberOfTickets, availableSeats));
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
