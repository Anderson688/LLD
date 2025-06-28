package payment.model;

import java.util.UUID;

public class Payment {
    private final String id;
    private final double amount;
    private PaymentStatus status;

    public Payment(double amount) {
        this.id = UUID.randomUUID().toString();
        this.amount = amount;
        this.status = PaymentStatus.PENDING;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public double getAmount() {
        return amount;
    }
}