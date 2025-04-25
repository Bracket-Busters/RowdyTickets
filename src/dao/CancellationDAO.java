package dao;

import dto.Cancellation;

import java.util.List;

public interface CancellationDAO {
    public void addCancellation(Cancellation cancellation);
    public void updateCancellation(Cancellation cancellation);
    public void deleteCancellation(Cancellation cancellation);
    public Cancellation getCancellation(int id);
    public List<Cancellation> getCancellations();
}
