import dao.*;
import dto.*;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Driver {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/utsa_ticket_reservation_system";
        String username = "root";
        String password = "password";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Scanner scan = new Scanner(System.in))
        {

            System.out.println("~.~.~.~ Connected to database successfully! ~.~.~.~\n\n");
            System.out.println("---- Welcome to RowdyTickets! -----");

            BookingDAO bookingDAO = new BookingDAOImplement(conn);
            CancellationDAO cancellationDAO = new CancellationDAOImplement(conn);
            GameDAO gameDAO = new GameDAOImplement(conn);
            SeatsDAO seatsDAO = new SeatsDAOImplement(conn);
            UserDAO userDAO = new UserDAOImplement(conn);

            User currentUser = null;
            while (currentUser == null){
                System.out.println("Please choose an option: ");
                System.out.println(" 1) Login");
                System.out.println(" 2) Create new account");
                System.out.println(" 3) Exit");
                System.out.print("Enter choice (1-3): ");

                String authChoice = scan.nextLine().trim();
                switch (authChoice){
                    case "1":
                        System.out.print("Enter email: ");
                        String email = scan.nextLine().trim();
                        System.out.print("Enter password: ");
                        String userPassword = scan.nextLine().trim();
                        currentUser = userDAO.login(email, userPassword);
                        if (currentUser == null){
                            System.out.println("Invalid email or password. Please try again. \n");
                        } else {
                            System.out.println("Logged in as " + currentUser.getFirstName() + " " + currentUser.getLastName() + "\n");
                        }
                        break;

                    case "2":
                        System.out.print("Enter First Name: ");
                        String newFirstName = scan.nextLine().trim();
                        System.out.print("Enter Last Name: ");
                        String newLastName = scan.nextLine().trim();
                        System.out.print("Enter Email: ");
                        String newEmail = scan.nextLine().trim();
                        System.out.print("Enter Phone Number (ex. 999-999-9999): ");
                        String newPhoneNumber = scan.nextLine().trim();
                        System.out.print("Enter Password: ");
                        String newPassword = scan.nextLine().trim();
                        User newUser = new User(newFirstName, newLastName, newEmail,newPhoneNumber, newPassword);
                        userDAO.addUser(newUser);
                        System.out.println("Account Created! You can now login! \n");
                        break;

                    case "3":
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again. \n");
                }
            }

            List<Game> allGames = gameDAO.getAllGames();
            Map<Integer, Game> gameCache = allGames.stream().collect(Collectors.toMap(Game::getGameID, g -> g));


            boolean input = true;
            while (input){
                System.out.println("Please choose one of the following options:");
                System.out.println("1. Purchase a Booking");
                System.out.println("2. View all Bookings Made");
                System.out.println("3. Cancel a Booking");
                System.out.println("4. View all Cancellations");
                System.out.println("5. View all Games");
                System.out.println("6. View Available Seats for a Game");
                System.out.println("7. Exit");
                System.out.print("Enter your choice (1-7): ");

                String line = scan.nextLine().trim();
                int choice;
                try {
                    choice = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid choice (1-7): ");
                    continue;
                }

                switch(choice){
                    case 1:
                        // --- Purchase ---
                        // 1. show games
                        System.out.println("\n--- Games ---");
                        allGames.forEach(g -> {
                            System.out.println("Game ID: " + g.getGameID() +
                                                "\n Team 1: " + g.getTeamOne() +
                                                "\n Team 2: " + g.getTeamTwo() +
                                                "\n Date of Game: " + g.getDate() +
                                                "\n Number of Seats Available: " + g.getAvailableSeats() + "\n"
                            );
                        });
                        // 2. pick a game
                        System.out.print("Enter Game ID: ");
                        int gameID = Integer.parseInt(scan.nextLine().trim());
                        Game game = gameCache.get(gameID);
                        if (game == null){
                            System.out.println("Invalid game ID. Please try again. \n");
                            break;
                        }

                        // 3. show available seats
                        List<Seats> available = seatsDAO.selectAvailableSeatsByGameId(gameID);
                        if (available.isEmpty()) {
                            System.out.println("No seats available for that game.");
                            break;
                        }
                        System.out.println("\n--- Available Seats ---");
                        available.forEach(s -> {
                            System.out.println("Seat ID: " + s.getSeatID() + " | Seat Number: " + s.getSeatNumber() + " | Section: " + s.getSeatRow() + "\n");
                        });

                        // 4. pick a seat
                        System.out.print("Enter Seat ID: ");
                        int seatID = Integer.parseInt(scan.nextLine().trim());
                        Seats seat = seatsDAO.selectSeatsById(seatID);
                        if (seat == null) {
                            System.out.println("Invalid Seat ID.");
                            break;
                        }

                        Booking newBooking = new Booking(currentUser, game, seat, "Confirmed", new java.util.Date());
                        bookingDAO.addBooking(newBooking);
                        System.out.println("Booking Created! Booking ID: " + newBooking.getBookingId());
                        break;
                    case 2:
                        // --- View All Bookings Made By User ---
                        List<Booking> myBookings = bookingDAO.getAllBookingsAndDetailsByUserId(currentUser.getUserID());
                        System.out.println("\n ----- " + currentUser.getFirstName() + " " + currentUser.getLastName() + " Booking Details ------");
                        if (myBookings.isEmpty()){
                            System.out.println("You have no bookings yet. Please try again later.\n.");
                        } else {
                            myBookings.forEach( b -> {
                                System.out.println("Booking ID: " + b.getBookingId() +
                                                    "\n Game ID: " + b.getGame().getGameID() +
                                                    "\n Matchup: " + b.getGame().getTeamOne() + " vs " + b.getGame().getTeamTwo() +
                                                    "\n Status: " + b.getStatus() +
                                                    "\n Seat Location: " + b.getSeats().getSeatNumber() +
                                                    "\n Date Purchased: " +b.getDate() +"\n"
                                );
                            });
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        System.out.println("\n--- All Games ----");
                        for(Game g : allGames){
                            System.out.println("Game ID: " + g.getGameID() +
                                    "\n Team 1: " + g.getTeamOne() +
                                    "\n Team 2: " + g.getTeamTwo() +
                                    "\n Date of Game: " + g.getDate() +
                                    "\n Number of Seats Available: " + g.getAvailableSeats() + "\n"
                            );
                        }
                        break;
                    case 6:
                        System.out.print("Provide a Game ID: ");
                        int gameId = Integer.parseInt(scan.nextLine().trim());
                        List<Seats> seatsAvailableByGame = seatsDAO.selectAvailableSeatsByGameId(gameId);
                        System.out.println("\n--- Available Seats ----");
                        for (Seats s : seatsAvailableByGame){
                            System.out.println("Seat ID: " + s.getSeatID() + " | Seat Number: " + s.getSeatNumber() + " | Section: " + s.getSeatRow() + "\n");
                        }
                        break;
                    case 7:
                        input = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again. \n");
                        break;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
