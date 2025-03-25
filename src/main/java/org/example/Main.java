package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        Cinema cinema = new Cinema();

        while (true) {
            try {
                System.out.print("> ");
                String line = reader.readLine();

                if (line.equals("exit")) {
                    break;
                } else if (line.equals("help")) {
                    // TODO: print help
                } else if (line.equals("movies")) {
                    for (Session session : cinema.getSessions()) {
                        System.out.printf(
                                "Name = %s Duration = %d minutes NumberOfTickets = %d AvailableSeats = %s\n",
                                session.getMovieName(),
                                session.getDurationInMinutes(),
                                session.getNumberOfTickets(),
                                session.getAvailableSeats().toString());
                    }
                } else if (line.equals("create")) {
                    System.out.print("Name: ");
                    String name = reader.readLine();
                    System.out.print("Duration: ");
                    int duration = Integer.parseInt(reader.readLine());
                    System.out.print("Number of tickets: ");
                    int numberOfTickets = Integer.parseInt(reader.readLine());

                    cinema.addSession(new Session(name, duration, numberOfTickets));
                } else if (line.equals("buy_ticket")) {
                    System.out.print("Movie name: ");
                    String name = reader.readLine();

                    try {
                        Session session = cinema.findMovieByName(name);

                        System.out.printf("Seat number [0; %d]: ", session.getNumberOfTickets() - 1);
                        int seat = Integer.parseInt(reader.readLine());

                        Ticket ticket = session.buyTicket(seat);
                        System.out.println("A ticket has been bought");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (line.equals("import")) {
                    // TODO: File not found errors

                    System.out.print(".csv file path: ");
                    String importPath = reader.readLine();

                    String csv = Files.readString(Path.of(importPath));
                    CinemaDataImporter importer = new CinemaDataImporter();
                    cinema.setSessions(importer.importSessionsFromCsv(csv, ';'));
                } else if (line.equals("export")) {
                    // TODO: File not found errors

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
                } else if (line.equals("delete")) {
                    System.out.print("Movie name: ");
                    String name = reader.readLine();

                    try {
                        Session session = cinema.findMovieByName(name);
                        cinema.removeSession(session);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (line.equals("refund")) {
                    System.out.print("Movie name: ");
                    String name = reader.readLine();

                    try {
                        Session session = cinema.findMovieByName(name);
                        cinema.removeSession(session);

                        System.out.print("Seat number: ");
                        int seat = Integer.parseInt(reader.readLine());
                        Ticket ticket = new Ticket(session, seat);

                        session.refundTicket(ticket);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (IOException e) {
                break;
            }
        }
    }
}