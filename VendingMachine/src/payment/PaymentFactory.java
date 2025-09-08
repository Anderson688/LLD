package payment;

public class PaymentFactory {
    public static Payment getPaymentStrategy(String strategyType) {
        if (strategyType == null) {
            return null;
        }
        if (strategyType.equalsIgnoreCase("CREDIT_CARD")) {
            return new CreditCardPayment();
        } else if (strategyType.equalsIgnoreCase("CASH")) {
            return new CashPayment();
        }
        return null;
    }
}