package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final String exitCommandName = "exit";
    private static final String sessionsCommandName = "movies";
    private static final String createCommandName = "create";
    private static final String buyTicketCommandName = "buy_ticket";
    private static final String importCommandName = "import";
    private static final String exportCommandName = "export";
    private static final String deleteCommandName = "delete";
    private static final String refundCommandName = "refund";
    private static final String ticketStatsCommandName = "ticket_stats";

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Cinema cinema = new Cinema();

        printCommands();

        while (true) {
            try {
                System.out.print("> ");
                String command = reader.readLine();

                if (command.equals(exitCommandName)) {
                    break;
                } else if (command.equals(sessionsCommandName)) {
                    for (Session session : cinema.getSessions()) {
                        System.out.printf(
                                "Name = %s Duration = %d TicketPrice = %d NumberOfTickets = %d AvailableSeats = %s\n",
                                session.getMovieName(),
                                session.getDurationInMinutes(),
                                session.getTicketPrice(),
                                session.getNumberOfTickets(),
                                session.getAvailableSeats().toString());
                    }
                } else if (command.equals(createCommandName)) {
                    System.out.print("Name: ");
                    String name = reader.readLine();
                    System.out.print("Duration: ");
                    int duration = Integer.parseInt(reader.readLine());
                    System.out.print("Number of tickets: ");
                    int numberOfTickets = Integer.parseInt(reader.readLine());
                    System.out.print("Ticket price: ");
					int ticketPrice = Integer.parseInt(reader.readLine());

                    cinema.addSession(new Session(name, duration, ticketPrice, numberOfTickets));
                } else if (command.equals(buyTicketCommandName)) {
                    System.out.print("Movie name: ");
                    String name = reader.readLine();

                    try {
                        Session session = cinema.findMovieByName(name);

                        System.out.printf("Seat number [0; %d]: ", session.getNumberOfTickets() - 1);
                        int seat = Integer.parseInt(reader.readLine());

                        session.buyTicket(seat);
                        System.out.println("A ticket has been bought");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (command.equals(importCommandName)) {
                    System.out.print(".csv file path: ");
                    Path importPath = Path.of(reader.readLine());

                    if (!Files.exists(importPath)) {
                        System.out.printf("File '%s' doesn't exist\n", importPath);
                        continue;
                    }

                    String csv = Files.readString(importPath);
                    CinemaDataImporter importer = new CinemaDataImporter();

                    try {
                        cinema.setSessions(importer.importSessionsFromCsv(csv, ';'));
                    } catch (Exception e) {
                        System.out.printf("Error: %s\n", e.getMessage());
                    }
                } else if (command.equals(exportCommandName)) {
                    System.out.print("Output file path: ");
                    String outputFilePath = reader.readLine();

                    SortCriteria sortCriteria;

                    {
                        System.out.print("Sort criteria (None, Name, Duration): ");
                        String sortCriteriaString = reader.readLine();
                        if (sortCriteriaString.equals("None")) {
                            sortCriteria = SortCriteria.None;
                        } else if (sortCriteriaString.equals("Name")) {
                            sortCriteria = SortCriteria.Name;
                        } else if (sortCriteriaString.equals("Duration")) {
                            sortCriteria = SortCriteria.Duration;
                        } else {
                            sortCriteria = SortCriteria.None;
                            System.out.println("Invalid sort criteria");
                        }
                    }

                    CinemaDataExporter exporter = new CinemaDataExporter();
                    Files.writeString(Path.of(outputFilePath), exporter.exportToCsvString(cinema, sortCriteria));
                } else if (command.equals(deleteCommandName)) {
                    System.out.print("Movie name: ");
                    String name = reader.readLine();

                    try {
                        Session session = cinema.findMovieByName(name);
                        cinema.removeSession(session);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (command.equals(refundCommandName)) {
                    System.out.print("Movie name: ");
                    String name = reader.readLine();

                    try {
                        Session session = cinema.findMovieByName(name);

                        System.out.print("Seat number: ");
                        int seat = Integer.parseInt(reader.readLine());
                        Ticket ticket = new Ticket(session, seat);

                        session.refundTicket(ticket);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (command.equals(ticketStatsCommandName)) {
                    for (Session session : cinema.getSessions()) {
                        int totalSum = session.getTicketsSold() * session.getTicketPrice();

                        System.out.printf("Movie Name = %s Ticket Sold = %d Total Income = %d\n",
                                session.getMovieName(),
                                session.getTicketsSold(),
                                totalSum);
                    }
                } else {
                    System.out.printf("Unknown command '%s'\n", command);
                }
            } catch (IOException e) {
                break;
            }
        }
    }

    private static void printCommands() {
        System.out.println("Available commands:");

        System.out.print(exitCommandName);
        System.out.println(" - to exit the program");
        System.out.print(sessionsCommandName);
        System.out.println(" - display the list of sessions");
        System.out.print(createCommandName);
        System.out.println(" - create a session");
        System.out.print(deleteCommandName);
        System.out.println(" - delete a session");
        System.out.print(buyTicketCommandName);
        System.out.println(" - buy a ticket");
        System.out.print(refundCommandName);
        System.out.println(" - refund a ticket");
        System.out.print(importCommandName);
        System.out.println(" - import sessions from a csv file");
        System.out.print(exportCommandName);
        System.out.println(" - export sessions to a csv file");
    }
}
