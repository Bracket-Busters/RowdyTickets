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

    }

    @Override
    public void deleteBooking(Booking booking) {

    }

    @Override
    public Booking getBooking(int id) {
        return null;
    }

    @Override
    public List<Booking> getBookings() {
        return List.of();
    }
}
