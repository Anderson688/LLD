package payment;

public interface PaymentGateway {
    boolean processPayment(double amount);
}