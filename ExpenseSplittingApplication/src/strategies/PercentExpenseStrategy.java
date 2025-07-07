package strategies;

import models.Expense;
import models.Split;

public class PercentExpenseStrategy implements ExpenseStrategy {
    @Override
    public void calculateSplits(Expense expense) {
        double totalPercentage = 0;
        for (Split split : expense.getSplits()) {
            totalPercentage += split.getPercentage();
        }

        if (totalPercentage != 100) {
            throw new IllegalArgumentException("Sum of percentages does not equal 100.");
        }

        for (Split split : expense.getSplits()) {
            double amount = (split.getPercentage() / 100.0) * expense.getTotalAmount();
            split.setAmount(amount);
        }
    }
}