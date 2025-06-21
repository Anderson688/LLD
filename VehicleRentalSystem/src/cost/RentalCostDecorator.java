package cost;

import java.math.BigDecimal;

public abstract class RentalCostDecorator implements IRentalCostComponent {
    protected IRentalCostComponent wrappedComponent;

    public RentalCostDecorator(IRentalCostComponent wrappedComponent) {
        this.wrappedComponent = wrappedComponent;
    }

    @Override
    public BigDecimal calculateCost() {
        return wrappedComponent.calculateCost();
    }

    @Override
    public String getDescription() {
        return wrappedComponent.getDescription();
    }
}