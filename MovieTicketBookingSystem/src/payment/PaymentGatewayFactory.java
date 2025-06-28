package payment;

import payment.impl.CreditCardGateway;
import payment.impl.PayPalGateway;

public class PaymentGatewayFactory {
    public static PaymentGateway getPaymentGateway(String type) {
        if ("CreditCard".equalsIgnoreCase(type)) {
            return new CreditCardGateway();
        } else if ("PayPal".equalsIgnoreCase(type)) {
            return new PayPalGateway();
        }
        throw new IllegalArgumentException("Unknown payment gateway type");
    }
}