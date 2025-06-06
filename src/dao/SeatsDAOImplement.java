package dao;

import dto.Seats;
import dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatsDAOImplement implements SeatsDAO {
    private Connection conn;
    public SeatsDAOImplement(Connection conn) { this.conn = conn; }

    @Override
    public Seats getSeats() {
        String sql = "SELECT * FROM Seats";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new Seats(
                            rs.getInt("SeatID"),
                            rs.getString("SeatNumber"),
                            rs.getString("seatRow"),
                            rs.getString("Avialability")
                    );
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Seats> selectAvailableSeatsByGameId(int gameId) {
        List<Seats> seatsByGame = new ArrayList<Seats>();
        String sql = """
                SELECT
                  s.SeatID,
                  s.SeatNumber,
                  s.SeatRow
                FROM Seats s
                LEFT JOIN Bookings b
                  ON  b.SeatID   = s.SeatID
                  AND b.GameID   = ?
                  AND b.Status   = 'Confirmed'
                WHERE s.Availability = 'Available'
                  AND b.BookingID IS NULL;
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, gameId);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    Seats seats = new Seats(
                            rs.getInt("SeatID"),
                            rs.getString("SeatNumber"),
                            rs.getString("seatRow"),
                            "Available"
                    );

                    seatsByGame.add(seats);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seatsByGame;
    }

    @Override
    public Seats selectSeatsById(int seatID) {
        String sql = "SELECT * FROM Seats WHERE SeatID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, seatID);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new Seats(
                            rs.getInt("SeatID"),
                            rs.getString("SeatNumber"),
                            rs.getString("seatRow"),
                            rs.getString("Availability")
                    );
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
