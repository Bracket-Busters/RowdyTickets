package dao;

import dto.Seats;

import java.util.List;

public interface SeatsDAO {
    public void insertSeats(Seats seats);
    public void updateSeats(Seats seats);
    public void deleteSeats(Seats seats);
    public Seats selectSeats(Seats seats);
    public List<Seats> selectAvailableSeatsByGameId(int gameId);
    public Seats selectSeatsById(int id);
}
