package booking.model;

import catalog.model.Show;
import catalog.model.ShowSeat;
import payment.model.Payment;
import user.model.User;

import java.util.List;
import java.util.UUID;

public class Booking {
    private final String id;
    private final User user;
    private final Show show;
    private final List<ShowSeat> bookedSeats;
    private BookingStatus status;
    private Payment payment;

    public Booking(User user, Show show, List<ShowSeat> bookedSeats) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.show = show;
        this.bookedSeats = bookedSeats;
        this.status = BookingStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<ShowSeat> getBookedSeats() {
        return bookedSeats;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getTotalAmount() {
        return bookedSeats.stream().mapToDouble(ShowSeat::getPrice).sum();
    }
}