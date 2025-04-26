package dao;

import dto.Seats;
import dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SeatsDAOImplement implements SeatsDAO {
    private Connection conn;
    public SeatsDAOImplement(Connection conn) { this.conn = conn; }

    @Override
    public void insertSeats(Seats seats) {

    }

    @Override
    public void updateSeats(Seats seats) {

    }

    @Override
    public void deleteSeats(Seats seats) {

    }

    @Override
    public Seats selectSeats(Seats seats) {
        return null;
    }

    @Override
    public List<Seats> selectAllSeats() {
        return List.of();
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
