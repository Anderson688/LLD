package payment;

import java.math.BigDecimal;

public class CreditCardPaymentStrategy implements IPaymentStrategy {
    private final String cardNumber;

    public CreditCardPaymentStrategy(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean processPayment(String bookingId, BigDecimal amount) {
        System.out.println("Processing credit card payment for booking " + bookingId + " of " + amount + " via card: " + cardNumber);
        // Simulate external gateway interaction
        return Math.random() > 0.1; // 90% success rate
    }

    @Override
    public String getMethodName() {
        return "Credit Card";
    }
}