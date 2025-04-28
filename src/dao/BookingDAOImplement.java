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
    public boolean cancelBooking(int bookingID, int userID) {
        String sql = "{CALL CancelBooking(?,?)}";

        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, bookingID);
            cs.setInt(2, userID);
            int rowsAffected = cs.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Booking getBooking(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE bookingID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                UserDAO userDAO = new UserDAOImplement(conn);
                GameDAO gameDAO = new GameDAOImplement(conn);
                SeatsDAO seatsDAO = new SeatsDAOImplement(conn);

                if (rs.next()) {
                    int returnedUserID = rs.getInt("UserId");
                    int returnedGameID = rs.getInt("GameID");
                    int returnedSeatID = rs.getInt("SeatID");
                    String returnedStatus = rs.getString("Status");
                    Date date = rs.getDate("Date");

                    User user = userDAO.getUser(returnedUserID);
                    Game game = gameDAO.getGame(returnedGameID);
                    Seats seat = seatsDAO.selectSeatsById(returnedSeatID);
                    return new Booking(bookingId, user, game, seat, returnedStatus, date);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
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