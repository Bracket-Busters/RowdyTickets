package dto;

public class Cancellation {
    private int cancellationID;
    private Booking booking;
    private User user;
    private String refundStatus;

    public Cancellation(int cancellationID, Booking booking, User user, String refundStatus) {
        this.cancellationID = cancellationID;
        this.booking = booking;
        this.user = user;
        this.refundStatus = refundStatus;
    }

    public int getCancellationID() { return cancellationID;}
    public Booking getBooking() { return booking;}
    public User getUser() { return user;}
    public String getRefundStatus() { return refundStatus;}
    public void setCancellationID(int cancellationID) { this.cancellationID = cancellationID;}
    public void setBooking(Booking booking) { this.booking = booking;}
    public void setUser(User user) { this.user = user;}
    public void setRefundStatus(String refundStatus) { this.refundStatus = refundStatus;}
}
