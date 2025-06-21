package payment;

import booking.Booking;
import core.RentalSystemManager;

import java.math.BigDecimal;
import java.util.Optional;

public class PaymentService {
    private final RentalSystemManager manager;

    public PaymentService(RentalSystemManager manager) {
        this.manager = manager;
    }

    // Uses Strategy Pattern
    public PaymentTransaction processPayment(String bookingId, BigDecimal amount, IPaymentStrategy paymentStrategy) {
        Optional<Booking> bookingOpt = manager.getBookingById(bookingId);
        if (bookingOpt.isEmpty()) {
            throw new IllegalArgumentException("Booking not found: " + bookingId);
        }

        boolean success = paymentStrategy.processPayment(bookingId, amount);
        PaymentTransaction transaction = new PaymentTransaction(bookingId, amount, paymentStrategy.getMethodName(), success);
        manager.addPayment(transaction);

        if (success) {
            System.out.println("Payment for booking " + bookingId + " successful.");
        } else {
            System.out.println("Payment for booking " + bookingId + " failed.");
        }
        return transaction;
    }
}