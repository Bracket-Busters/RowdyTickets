package dao;

import dto.Booking;
import dto.Cancellation;
import dto.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;

public class CancellationDAOImplement implements CancellationDAO {
    private Connection conn;

    public CancellationDAOImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Cancellation> getCancellationsByUserId(int userId) {
        List<Cancellation> cancellations = new ArrayList<Cancellation>();

        String sql = "SELECT * FROM cancellations WHERE UserId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()){
                BookingDAO bookingDAO = new BookingDAOImplement(conn);
                UserDAO userDAO = new UserDAOImplement(conn);
                while (rs.next()) {
                    int cancellationId = rs.getInt("CancellationID");
                    int bookingId = rs.getInt("BookingID");
                    int user_Id = rs.getInt("UserID");
                    String refundStatus = rs.getString("RefundStatus");

                    Booking booking = bookingDAO.getBooking(bookingId);
                    User user = userDAO.getUser(user_Id);

                    cancellations.add(new Cancellation(cancellationId, booking, user, refundStatus));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cancellations;
    }

}
