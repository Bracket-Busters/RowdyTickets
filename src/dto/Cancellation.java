package dto;

public class Cancellation {
    private int cancellationID;
    private Booking booking;
    private User user;

    public Cancellation(int cancellationID, Booking booking, User user) {
        this.cancellationID = cancellationID;
        this.booking = booking;
        this.user = user;
    }

    public int getCancellationID() { return cancellationID;}
    public Booking getBooking() { return booking;}
    public User getUser() { return user;}
    public void setCancellationID(int cancellationID) { this.cancellationID = cancellationID;}
    public void setBooking(Booking booking) { this.booking = booking;}
    public void setUser(User user) { this.user = user;}
}
