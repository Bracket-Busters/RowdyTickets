import dao.*;
import dto.*;

import java.sql.*;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/utsa_ticket_reservation_system";
        String username = "root";
        String password = "password";
        try{
            Connection conn = DriverManager.getConnection(url, username, password);
            Scanner scan = new Scanner(System.in);
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
                        System.out.print("Enter Password: ");
                        String newPassword = scan.nextLine().trim();
                        User newUser = new User(newFirstName, newLastName, newEmail, newPassword);
                        userDAO.addUser(newUser);
                        System.out.println("Account Created! Logged in as " + newUser.getFirstName() + " " + newUser.getLastName() + "\n");
                        break;

                    case "3":
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again. \n");
                }
            }


            boolean input = true;
            while (input != false){
                System.out.println("Please choose one of the following options:");
                System.out.println("1. Purchase a Booking");
                System.out.println("2. View all Bookings Made");
                System.out.println("3. Cancel a Booking");
                System.out.println("4. View Games");
                System.out.println("5. View Available Seats for a Game");
                System.out.println("6. Exit");
                System.out.print("Enter your choice (1-6): ");

                String line = scan.nextLine().trim();
                int choice;
                try {
                    choice = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid choice (1-6): ");
                    continue;
                }

                switch(choice){
                    case 1:
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
