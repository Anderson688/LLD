package payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentTransaction {
    private final String transactionId;
    private final String bookingId;
    private final BigDecimal amount;
    private final String paymentMethod;
    private final boolean success;
    private final LocalDateTime transactionTime;

    public PaymentTransaction(String bookingId, BigDecimal amount, String paymentMethod, boolean success) {
        this.transactionId = UUID.randomUUID().toString();
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.success = success;
        this.transactionTime = LocalDateTime.now();
    }

    // Getters
    public String getTransactionId() { return transactionId; }
    public String getBookingId() { return bookingId; }
    public BigDecimal getAmount() { return amount; }
    public String getPaymentMethod() { return paymentMethod; }
    public boolean isSuccess() { return success; }
    public LocalDateTime getTransactionTime() { return transactionTime; }

    @Override
    public String toString() {
        return "Transaction{" + "id='" + transactionId + '\'' + ", amount=" + amount + ", success=" + success + '}';
    }
}