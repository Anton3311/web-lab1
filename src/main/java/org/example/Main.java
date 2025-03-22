package org.example;

public class Main {
    public static void main(String[] args) {
        Cinema cinema = new Cinema();

        cinema.addSession(new Session("Dune", 2 * 60, 40));
        cinema.addSession(new Session("Dune 2", 2 * 60, 40));
        cinema.addSession(new Session("Oppenheimer", 2 * 60, 40));

        try {
            Ticket duneTicket = cinema.getSessions().getFirst().buyTicket(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}