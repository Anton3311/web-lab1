package org.example;

import java.util.Objects;

public class Ticket {
    private Session session;
    private int seatId;

    public Ticket(Session session, int seatId) {
        this.session = session;
        this.seatId = seatId;
    }

    public Session getSession() {
        return session;
    }

    public int getSeatId() {
        return seatId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != Ticket.class) {
            return false;
        }

        Ticket otherTicket = (Ticket)obj;
        return otherTicket.session == session && otherTicket.seatId == seatId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(session, seatId);
    }
}
