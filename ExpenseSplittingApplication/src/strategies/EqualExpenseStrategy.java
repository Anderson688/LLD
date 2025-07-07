package strategies;

import models.Expense;
import models.Split;

public class EqualExpenseStrategy implements ExpenseStrategy {
    @Override
    public void calculateSplits(Expense expense) {
        int totalUsers = expense.getSplits().size();
        double amountPerUser = expense.getTotalAmount() / totalUsers;

        for (Split split : expense.getSplits()) {
            split.setAmount(amountPerUser);
        }
    }
}