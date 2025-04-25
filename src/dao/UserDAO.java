package dao;

import dto.User;

import java.util.List;

public interface UserDAO {
    public void addUser(User user);
    public void updateUser(User user);
    public void deleteUser(User user);
    public User getUser(int id);
    List<User> getUsersDetails();
}
