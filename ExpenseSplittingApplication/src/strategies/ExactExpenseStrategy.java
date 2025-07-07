package strategies;

import models.Expense;
import models.Split;

public class ExactExpenseStrategy implements ExpenseStrategy {
    @Override
    public void calculateSplits(Expense expense) {
        double total = 0;
        for (Split split : expense.getSplits()) {
            total += split.getAmount();
        }

        if (total != expense.getTotalAmount()) {
            throw new IllegalArgumentException("Sum of exact splits does not match total expense amount.");
        }
        // Amounts are already set, no calculation needed here.
    }
}