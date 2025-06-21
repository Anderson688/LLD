package cost;

import java.math.BigDecimal;

public interface IRentalCostComponent {
    BigDecimal calculateCost();
    String getDescription();
}