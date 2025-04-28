package dao;

import dto.Booking;

import java.util.List;

public interface BookingDAO {
    public void addBooking(Booking booking);
    public boolean cancelBooking(int bookingId, int userId);
    public Booking getBooking(int bookingId);
    List<Booking> getAllBookingsAndDetailsByUserId(int userId);
}
