package payment;

public class CreditCardPayment implements Payment {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing card payment of $" + amount);
        return true;
    }
}