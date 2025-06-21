package cost;

import java.math.BigDecimal;

public class ChildSeatDecorator extends RentalCostDecorator {
    private static final BigDecimal CHILD_SEAT_CHARGE = new BigDecimal("15.00"); // Flat fee

    public ChildSeatDecorator(IRentalCostComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public BigDecimal calculateCost() {
        return super.calculateCost().add(CHILD_SEAT_CHARGE);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Child Seat (flat fee: " + CHILD_SEAT_CHARGE + ")";
    }
}