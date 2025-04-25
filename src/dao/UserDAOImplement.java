package dao;

import dto.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImplement implements UserDAO {

    private Connection conn;
    public UserDAOImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO Users (UserID, FirstName, LastName, Email, Password) VALUES (?,?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, user.getUserID());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User getUser(int id) {
        return null;
    }

    @Override
    public List<User> getUsersDetails() {
        return List.of();
    }
}
