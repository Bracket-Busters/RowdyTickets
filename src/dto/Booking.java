package dto;

import java.util.Date;


public class Booking {
    private int bookingId;
    private User user;
    private Game game;
    private Seats seats;
    private String status;
    private Date date; 

    public Booking(int bookingId, User user, Game game, Seats seats, String status, Date date) {
        this.bookingId = bookingId;
        this.user = user;
        this.game = game;
        this.seats = seats;
        this.status = status;
        this.date = date;
    }

    public Booking(User user, Game game, Seats seats, String status, Date date) {
        this(0,user,game,seats,status,date);
    }

    public int getBookingId() { return bookingId; }
    public User getUser() { return user; }
    public Game getGame() { return game; }
    public Seats getSeats() { return seats; }
    public String getStatus() { return status; }
    public Date getDate() { return date; } 
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setUser(User user) { this.user = user; }
    public void setGame(Game game) { this.game = game; }
    public void setSeats(Seats seats) { this.seats = seats; }
    public void setStatus(String status) { this.status = status; }
    public void setDate(Date date) { this.date = date; }
}
