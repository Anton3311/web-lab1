import org.example.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DataImportExportTests {
    @Test
    public void exportToCsv() {
        Cinema cinema = mock(Cinema.class);

        ArrayList<Session> sessions = new ArrayList<>();
        sessions.add(new Session("Movie 1", 100, 200, 3));
        sessions.add(new Session("Movie 2", 200, 200, 3));
        sessions.add(new Session("Movie 3", 150, 200, 3));

        when(cinema.getSessions()).thenReturn(sessions);

        // MovieName;Duration;TicketPrice;NumberOfTickets;AvailableSeats;
        // Movie 1;100;200;3;[0, 1, 2];
        // Movie 2;200;200;3;[0, 1, 2];
        // Movie 3;150;200;3;[0, 1, 2];

        CinemaDataExporter exporter = new CinemaDataExporter();
        String exportedCsv = exporter.exportToCsvString(cinema, SortCriteria.None);

        String expectedCsv = """
        MovieName;Duration;TicketPrice;NumberOfTickets;AvailableSeats;
        Movie 1;100;200;3;[0, 1, 2];
        Movie 2;200;200;3;[0, 1, 2];
        Movie 3;150;200;3;[0, 1, 2];""";

        assertEquals(expectedCsv, exportedCsv);
    }

    @Test
    public void importFromCsv() {
        String csv = """
        MovieName;Duration;TicketPrice;NumberOfTickets;AvailableSeats;
        Movie 1;100;200;3;[0, 1, 2];
        Movie 2;200;200;3;[0, 1, 2];
        Movie 3;150;200;3;[0, 1, 2];""";

        CinemaDataImporter importer = new CinemaDataImporter();
        ArrayList<Session> sessions = importer.importSessionsFromCsv(csv, ';');

        ArrayList<Session> expectedMovies = new ArrayList<>();
        expectedMovies.add(new Session("Movie 1", 100, 200, 3));
        expectedMovies.add(new Session("Movie 2", 200, 200, 3));
        expectedMovies.add(new Session("Movie 3", 150, 200, 3));

        assertEquals(expectedMovies, sessions);
    }
}
