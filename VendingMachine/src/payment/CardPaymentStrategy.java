package payment;

public class CardPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing card payment of $" + amount);
        return true;
    }
}