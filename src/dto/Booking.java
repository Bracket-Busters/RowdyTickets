package dto;


public class Booking {
    private int bookingId;
    private User user;
    private Game game;
    private Seats seats;
    private String status;

    public Booking(int bookingId, User user, Game game, Seats seats, String status) {
        this.bookingId = bookingId;
        this.user = user;
        this.game = game;
        this.seats = seats;
        this.status = status;
    }
    public int getBookingId() { return bookingId; }
    public User getUser() { return user; }
    public Game getGame() { return game; }
    public Seats getSeats() { return seats; }
    public String getStatus() { return status; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public void setUser(User user) { this.user = user; }
    public void setGame(Game game) { this.game = game; }
    public void setSeats(Seats seats) { this.seats = seats; }
    public void setStatus(String status) { this.status = status; }
}
