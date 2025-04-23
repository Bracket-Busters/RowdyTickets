package dao;

import java.sql.*;

public class DBTicketConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/utsa_ticket_reservation_system";
        String username = "root";
        String password = "password";
        try{
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("~.~.~.~ Connected to database successfully! ~.~.~.~\n\n");
            System.out.println("---- Welcome to RowdyTickets! -----");


            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Users");

            while (rs.next()){ System.out.println(rs.getString("FirstName")); }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
