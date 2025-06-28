package booking;

import booking.concurrency.SeatLockProvider;
import booking.exception.SeatsTemporarilyHeldException;
import booking.model.Booking;
import booking.model.BookingStatus;
import catalog.model.SeatStatus;
import catalog.model.Show;
import catalog.model.ShowSeat;
import payment.PaymentGateway;
import payment.PaymentGatewayFactory;
import payment.model.Payment;
import payment.model.PaymentStatus;
import user.model.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

// Singleton Pattern
public class BookingService {
    private static volatile BookingService instance;
    private final SeatLockProvider seatLockProvider;

    private BookingService(SeatLockProvider seatLockProvider) {
        this.seatLockProvider = seatLockProvider;
    }

    public static BookingService getInstance(SeatLockProvider seatLockProvider) {
        if (instance == null) {
            synchronized (BookingService.class) {
                if (instance == null) {
                    instance = new BookingService(seatLockProvider);
                }
            }
        }
        return instance;
    }

    public Booking createBooking(User user, Show show, List<ShowSeat> selectedSeats) {
        boolean locksAcquired = false;
        try {
            // TRY TO ACQUIRE DISTRIBUTED LOCK (Non-Blocking)
            locksAcquired = seatLockProvider.tryLockSeats(selectedSeats, 500, TimeUnit.MILLISECONDS);

            if (!locksAcquired) {
                // This is the immediate failure path!
                throw new SeatsTemporarilyHeldException("Seats are currently held by another user. Please try again.");
            }

            // ----- START OF CRITICAL SECTION (if locks were acquired) -----

            // 1. Validate seats are available
            for (ShowSeat seat : selectedSeats) {
                if (seat.getStatus() != SeatStatus.AVAILABLE) {
                    throw new IllegalStateException("Seat " + seat + " is not available.");
                }
            }

            // 2. Mark seats as locked
            for (ShowSeat seat : selectedSeats) {
                seat.setStatus(SeatStatus.LOCKED);
            }

            // 3. Create a pending booking
            Booking booking = new Booking(user, show, selectedSeats);
            System.out.println("Booking PENDING with ID: " + booking.getId());

            // 4. Process Payment
            Payment payment = new Payment(booking.getTotalAmount());
            booking.setPayment(payment);
            PaymentGateway gateway = PaymentGatewayFactory.getPaymentGateway("CreditCard");
            boolean paymentSuccess = gateway.processPayment(payment.getAmount());

            // 5. Finalize or cancel booking based on payment
            if (paymentSuccess) {
                payment.setStatus(PaymentStatus.SUCCESSFUL);
                booking.setStatus(BookingStatus.CONFIRMED);
                for (ShowSeat seat : selectedSeats) {
                    seat.setStatus(SeatStatus.BOOKED);
                }
                System.out.println("Booking CONFIRMED for user " + user.getName());
                return booking;
            } else {
                payment.setStatus(PaymentStatus.FAILED);
                booking.setStatus(BookingStatus.CANCELLED);
                for (ShowSeat seat : selectedSeats) {
                    seat.setStatus(SeatStatus.AVAILABLE); // Revert status
                }
                System.out.println("Booking FAILED for user " + user.getName());
                return booking;
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Booking process was interrupted.", e);
        } finally {
            if (locksAcquired) {
                seatLockProvider.unlockSeats(selectedSeats);
            }
        }
    }
}