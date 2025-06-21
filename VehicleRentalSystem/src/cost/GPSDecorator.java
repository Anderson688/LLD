package cost;

import java.math.BigDecimal;

public class GPSDecorator extends RentalCostDecorator {
    private static final BigDecimal GPS_DAILY_CHARGE = new BigDecimal("5.00");
    private long numberOfDays;

    public GPSDecorator(IRentalCostComponent wrappedComponent, long numberOfDays) {
        super(wrappedComponent);
        this.numberOfDays = numberOfDays;
    }

    @Override
    public BigDecimal calculateCost() {
        return super.calculateCost().add(GPS_DAILY_CHARGE.multiply(new BigDecimal(numberOfDays)));
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", GPS (" + numberOfDays + " days @ " + GPS_DAILY_CHARGE + "/day)";
    }
}