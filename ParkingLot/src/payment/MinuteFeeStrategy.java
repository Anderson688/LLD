package payment;

import tickets.Ticket;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MinuteFeeStrategy implements FeeCalculationStrategy {
    private final double ratePerMinute;

    public MinuteFeeStrategy(double rate) { this.ratePerMinute = rate; }

    @Override
    public double calculateFee(Ticket ticket) {
        long minutes = ChronoUnit.MINUTES.between(ticket.getEntryTime(), LocalDateTime.now());
        return Math.max(1, minutes) * ratePerMinute;
    }
}