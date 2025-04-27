package dao;

import dto.*;

import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class BookingDAOImplement implements BookingDAO {
    private Connection conn;

    public BookingDAOImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (UserID, GameID, SeatID, Status, Date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, booking.getUser().getUserID());
            ps.setInt(2, booking.getGame().getGameID());
            ps.setInt(3, booking.getSeats().getSeatID());
            ps.setString(4, booking.getStatus());
            ps.setDate(5, new java.sql.Date(booking.getDate().getTime()));
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    booking.setBookingId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to insert booking record, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cancelBooking(int bookingID) {
        String sql = "UPDATE bookings SET Status = ? WHERE BookingID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "Cancelled");
            ps.setInt(2, bookingID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Booking> getAllBookingsAndDetailsByUserId(int userID) {
        List<Booking> bookings = new ArrayList<Booking>();

        String sql = """
                SELECT BookingID, UserID, GameID, SeatID, Status, Date
                FROM bookings
                WHERE UserID = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()) {
                UserDAO userDAO = new UserDAOImplement(conn);
                GameDAO gameDAO = new GameDAOImplement(conn);
                SeatsDAO seatsDAO = new SeatsDAOImplement(conn);

                while (rs.next()) {
                    int bookingID = rs.getInt("BookingID");
                    int UserID = rs.getInt("UserID");
                    int GameID = rs.getInt("GameID");
                    int SeatID = rs.getInt("SeatID");
                    String Status = rs.getString("Status");
                    Date Date = rs.getDate("Date");

                    User user = userDAO.getUser(UserID);
                    Game game = gameDAO.getGame(GameID);
                    Seats seats = seatsDAO.selectSeatsById(SeatID);

                    bookings.add(new Booking(bookingID, user, game, seats, Status, Date));
                }
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }
}