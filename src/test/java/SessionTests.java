import org.example.Session;
import org.example.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTests {

    @Test
    public void buyTicket() {
        Session session = new Session("Test movie", 10, 200, 10);

        int seatId = 0;
        Ticket ticket = assertDoesNotThrow(() -> session.buyTicket(seatId));

        assertEquals(ticket, new Ticket(session, 0));
    }

    @Test
    public void buyTicketWithNoAvailableSeats() {
        Session session = new Session("Test movie", 10, 200, 0);

        assertThrows(Exception.class, () -> session.buyTicket(0));
    }

    @Test
    public void buyTicketForInvalidSeat() {
        Session session = new Session("Test movie", 10, 200, 40);

        assertThrows(Exception.class, () -> session.buyTicket(100));
    }

    @Test
    public void refundTicket() {
        Session session = new Session("Test movie", 10, 200, 40);

        Ticket ticket = assertDoesNotThrow(() -> session.buyTicket((0)));
        assertDoesNotThrow(() -> session.refundTicket(ticket));
    }

    @Test
    public void refundInvalidTicket() {
        Session session = new Session("Test movie", 10, 200, 40);

        Ticket invalidTicket = new Ticket(session, 100);
        assertThrows(Exception.class, () -> session.refundTicket(invalidTicket));
    }
}
