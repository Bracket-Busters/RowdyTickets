package dto;

public class User{
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;

    public User(int userID, String firstName, String lastName, String email, String phoneNumber, String password) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public User(String firstName, String lastName, String email, String phoneNumber, String password) {
        this(0,firstName,lastName,email,phoneNumber,password);
    }

    public int getUserID() { return userID; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    public void setUserID(int userID) { this.userID = userID; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPassword(String password) { this.password = password; }

}
