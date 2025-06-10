package payment;

import tickets.Ticket;

public interface FeeCalculationStrategy {
    double calculateFee(Ticket ticket);
}