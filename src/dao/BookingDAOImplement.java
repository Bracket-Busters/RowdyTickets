package dao;

import dto.Booking;

import java.util.List;

import java.sql.*;

public class BookingDAOImplement implements BookingDAO {
    private Connection conn;

    public BookingDAOImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addBooking(Booking booking) {
        String sql = "INSERT INTO bookings (UserID, GameID, SeatID, Status, Date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, booking.getUser().getUserID());
            ps.setInt(2, booking.getGame().getGameID());
            ps.setInt(3, booking.getSeats().getSeatID());
            ps.setString(4, booking.getStatus());
            ps.setDate(5, new java.sql.Date(booking.getDate().getTime()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET BookingID = ?, UserID = ?, GameID = ?, SeatID = ?, Status = ?, Date = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, booking.getBookingId());
            ps.setInt(2, booking.getUser().getUserID());
            ps.setInt(3, booking.getGame().getGameID());
            ps.setInt(4, booking.getSeats().getSeatID());
            ps.setString(5, booking.getStatus());
            ps.setDate(6, new java.sql.Date(booking.getDate().getTime()));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteBooking(int id) {
        String sql = "DELETE FROM bookings WHERE BookingID = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Booking getBooking(int id) {
        String sql = "SELECT * FROM bookings WHERE BookingId = ?";
        Booking booking = null;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int bookingId = rs.getInt("BookingID");
                int userId = rs.getInt("UserID");
                int gameId = rs.getInt("GameID");
                int seatId = rs.getInt("SeatID");
                int status = rs.getInt("Status");
                int date = rs.getInt("Date");

                booking = new Booking(bookingId, userId, gameId, seatId, status, date);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return booking;
    }


    @Override
    public List<Booking> getBookings() {
        return List.of();
    }
}
