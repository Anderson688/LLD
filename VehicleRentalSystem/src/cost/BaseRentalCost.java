package cost;

import java.math.BigDecimal;

public class BaseRentalCost implements IRentalCostComponent {
    private final BigDecimal baseRate;
    private final long numberOfDays;

    public BaseRentalCost(BigDecimal baseRate, long numberOfDays) {
        this.baseRate = baseRate;
        this.numberOfDays = numberOfDays;
    }

    @Override
    public BigDecimal calculateCost() {
        return baseRate.multiply(new BigDecimal(numberOfDays));
    }

    @Override
    public String getDescription() {
        return "Base rental cost (" + numberOfDays + " days)";
    }
}