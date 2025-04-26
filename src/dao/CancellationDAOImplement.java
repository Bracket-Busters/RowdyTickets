package dao;

import dto.Cancellation;

import java.sql.Connection;
import java.util.List;

import java.sql.*;

public class CancellationDAOImplement implements CancellationDAO {
    private Connection conn;
<<<<<<< HEAD

=======
>>>>>>> 17f9c4d089f41f7e0b079d325fd77beba83e2b53
    public CancellationDAOImplement(Connection conn) {
        this.conn = conn;
    }

<<<<<<< HEAD
=======

>>>>>>> 17f9c4d089f41f7e0b079d325fd77beba83e2b53
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
