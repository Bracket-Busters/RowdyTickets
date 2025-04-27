package dao;

import dto.Seats;

import java.util.List;

public interface SeatsDAO {
    public Seats getSeats();
    public List<Seats> selectAvailableSeatsByGameId(int gameId);
    public Seats selectSeatsById(int id);
}
