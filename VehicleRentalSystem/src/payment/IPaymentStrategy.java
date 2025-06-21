package payment;

import java.math.BigDecimal;

public interface IPaymentStrategy {
    boolean processPayment(String bookingId, BigDecimal amount);
    String getMethodName();
}