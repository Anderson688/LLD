package booking;

public interface IBookingSubject {
    void addObserver(IBookingObserver observer);
    void removeObserver(IBookingObserver observer);
    void notifyObservers(BookingEvent event, Booking booking);
}