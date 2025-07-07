package factories;

import enums.ExpenseType;
import strategies.EqualExpenseStrategy;
import strategies.ExactExpenseStrategy;
import strategies.ExpenseStrategy;
import strategies.PercentExpenseStrategy;

public class ExpenseStrategyFactory {
    public static ExpenseStrategy getStrategy(ExpenseType type) {
        return switch (type) {
            case EQUAL -> new EqualExpenseStrategy();
            case EXACT -> new ExactExpenseStrategy();
            case PERCENT -> new PercentExpenseStrategy();
            default -> throw new IllegalArgumentException("Invalid expense type provided.");
        };
    }
}