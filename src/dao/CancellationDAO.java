package dao;

import dto.Cancellation;

import java.util.List;

public interface CancellationDAO {
    public List<Cancellation> getCancellationsByUserId(int userId);

}
