package booking;

public interface IBookingObserver {
    void update(BookingEvent event, Booking booking);
}