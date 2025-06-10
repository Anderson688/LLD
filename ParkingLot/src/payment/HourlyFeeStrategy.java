package payment;

import tickets.Ticket;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class HourlyFeeStrategy implements FeeCalculationStrategy {
    private static final double HOURLY_RATE = 10.0;

    @Override
    public double calculateFee(Ticket ticket) {
        long hours = ChronoUnit.HOURS.between(ticket.getEntryTime(), LocalDateTime.now());
        return Math.max(1, hours) * HOURLY_RATE;
    }
}
