package payment;

public class CoinPaymentStrategy implements PaymentStrategy {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing coin payment of $" + amount);
        return true;
    }
}