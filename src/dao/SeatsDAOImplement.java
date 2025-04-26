package dao;

import dto.Seats;

import java.sql.Connection;
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
    public Seats selectSeatsById(int id) {
        return null;
    }
}
