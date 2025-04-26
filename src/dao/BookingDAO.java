package dao;

import dto.Booking;

import java.util.List;

public interface BookingDAO {
    public void addBooking(Booking booking);
    public void cancelBooking(int bookingId);
    List<Booking> getAllBookingsAndDetailsByUserId(int userId);
}
