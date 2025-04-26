package dao;

import dto.Cancellation;

import java.sql.Connection;
import java.util.List;

public class CancellationDAOImplement implements CancellationDAO {
    private Connection conn;
    public CancellationDAOImplement(Connection conn) {
        this.conn = conn;
    }


    @Override
    public void addCancellation(Cancellation cancellation) {

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
