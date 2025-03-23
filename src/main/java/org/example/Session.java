package org.example;

import java.util.HashSet;
import java.util.Objects;

public class Session {
    private final String movieName;
    private final int durationInMinutes;
    private final int numberOfTickets;
    private final HashSet<Integer> availableSeats;

    public Session(String movieName, int durationInMinutes, int numberOfTickets) {
        this.movieName = movieName;
        this.durationInMinutes = durationInMinutes;
        this.numberOfTickets = numberOfTickets;
        this.availableSeats = new HashSet<>();

        for (int i = 0; i < numberOfTickets; i++) {
            availableSeats.add(i);
        }
    }

    public Session(String movieName, int durationInMinutes, int numberOfTickets, HashSet<Integer> availableSeats) {
        this.movieName = movieName;
        this.durationInMinutes = durationInMinutes;
        this.numberOfTickets = numberOfTickets;
        this.availableSeats = availableSeats;
    }

    public HashSet<Integer> getAvailableSeats() {
        return availableSeats;
    }

    public int getTicketsSold() {
        return numberOfTickets - availableSeats.size();
    }

    public Ticket buyTicket(int seat) throws Exception {
        if (availableSeats.isEmpty()) {
            throw new Exception("No more tickets available");
        }

        if (!availableSeats.contains(seat)) {
            throw new Exception("The seat is taken");
        }

        Ticket ticket = new Ticket(this, seat);
        availableSeats.remove(seat);

        return ticket;
    }

    public boolean isTicketValid(Ticket ticket) {
        return ticket.getSession() == this && ticket.getSeatId() < numberOfTickets && !availableSeats.contains(ticket.getSeatId());
    }

    public void refundTicket(Ticket ticket) throws Exception {
        if (!isTicketValid(ticket)) {
            throw new Exception("Invalid ticket");
        }

        availableSeats.add(ticket.getSeatId());
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public String getMovieName() {
        return movieName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != Session.class) {
            return false;
        }

        Session otherSession = (Session)obj;
        return otherSession.availableSeats.equals(availableSeats)
                && otherSession.numberOfTickets == numberOfTickets
                && otherSession.durationInMinutes == durationInMinutes
                && otherSession.movieName.equals(movieName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieName, durationInMinutes, numberOfTickets, availableSeats);
    }
}
