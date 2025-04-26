package dao;

import dto.Cancellation;

import java.util.List;

import java.sql.*;

public class CancellationDAOImplement implements CancellationDAO {
    private Connection conn;

    public CancellationDAOImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addCancellation(Cancellation cancellation) {
        String sql = "INSERT INTO bookings (BookingID, UserID, GameID, SeatID, Status, Date) VALUES (?, ?, ?, ?, ?, ?)";

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
    public void updateCancellation(Cancellation cancellation) {

    }

    @Override
    public void deleteCancellation(Cancellation cancellation) {

    }

    @Override
    public Cancellation getCancellation(int id) {
        return null;
    }

    @Override
    public List<Cancellation> getCancellations() {
        return List.of();
    }
}
