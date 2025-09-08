package payment;

public class CashPayment implements Payment {
    @Override
    public boolean pay(double amount) {
        System.out.println("Processing coin payment of $" + amount);
        return true;
    }
}