package dao;

import dto.User;

import java.util.List;

public interface UserDAO {
    public void addUser(User user);
    public void deleteUser(User user);
    public User getUser(int userId);
    List<User> getUsersDetails();
    User login(String username, String password);
}
