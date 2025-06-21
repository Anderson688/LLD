package cost;

import java.math.BigDecimal;

public class InsuranceDecorator extends RentalCostDecorator {
    private static final BigDecimal INSURANCE_DAILY_CHARGE_PERCENTAGE = new BigDecimal("0.10"); // 10% of base

    public InsuranceDecorator(IRentalCostComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public BigDecimal calculateCost() {
        BigDecimal base = ((BaseRentalCost) wrappedComponent).calculateCost(); // Access base cost for percentage
        BigDecimal insuranceCost = base.multiply(INSURANCE_DAILY_CHARGE_PERCENTAGE);
        return super.calculateCost().add(insuranceCost);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Basic Insurance (10% of base)";
    }
}