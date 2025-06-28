package payment.impl;

import payment.PaymentGateway;

public class PayPalGateway implements PaymentGateway {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
        // Simulate API call
        return true;
    }
}