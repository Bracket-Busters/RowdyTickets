package dao;

import dto.Booking;

import java.util.List;

public interface BookingDAO {
    public void addBooking(Booking booking);
    public void updateBooking(Booking booking);
    public void deleteBooking(int id);
    public Booking getBooking(int id);
    public List<Booking> getBookings();
}
