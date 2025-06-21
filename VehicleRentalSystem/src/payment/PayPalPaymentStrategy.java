package payment;

import java.math.BigDecimal;

public class PayPalPaymentStrategy implements IPaymentStrategy {
    private final String paypalEmail;

    public PayPalPaymentStrategy(String paypalEmail) {
        this.paypalEmail = paypalEmail;
    }

    @Override
    public boolean processPayment(String bookingId, BigDecimal amount) {
        System.out.println("Processing PayPal payment for booking " + bookingId + " of " + amount + " via email: " + paypalEmail);
        // Simulate external gateway interaction
        return Math.random() > 0.05; // 95% success rate
    }

    @Override
    public String getMethodName() {
        return "PayPal";
    }
}