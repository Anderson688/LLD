package payment.impl;

import payment.PaymentGateway;

public class CreditCardGateway implements PaymentGateway {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
        // Simulate API call to a real gateway
        return true;
    }
}