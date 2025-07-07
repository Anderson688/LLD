package strategies;

import models.Expense;

public interface ExpenseStrategy {
    void calculateSplits(Expense expense);
}