package dao;

import dto.User;

import java.sql.*;
import java.util.List;

public class UserDAOImplement implements UserDAO {

    private Connection conn;
    public UserDAOImplement(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addUser(User user) {
        String sql = "INSERT INTO Users (FirstName, LastName, Email, Password) VALUES (?,?,?,?,?)";
        try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User getUser(int userID) {
        String sql = "SELECT * FROM Users WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, userID);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new User(
                            rs.getInt("UserID"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Email"),
                            rs.getString("Password")
                    );
                }
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUsersDetails() {
        return List.of();
    }

    @Override
    public User login(String email, String password) {
        String sql = "SELECT UserID, FirstName, LastName, Email, Password FROM Users WHERE Email = ? AND Password = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()){
                if (rs.next()){
                    return new User(
                            rs.getInt("UserId"),
                            rs.getString("FirstName"),
                            rs.getString("LastName"),
                            rs.getString("Email"),
                            rs.getString("Password")
                    );
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
