package models;

import enums.ExpenseType;

import java.util.List;

public class Expense {
    private final String description;
    private final double totalAmount;
    private final User paidByUser;
    private final ExpenseType expenseType;
    private final List<Split> splits;
    private final Group group; // Optional

    public Expense(String description, double totalAmount, User paidByUser, ExpenseType expenseType, List<Split> splits, Group group) {
        this.description = description;
        this.totalAmount = totalAmount;
        this.paidByUser = paidByUser;
        this.expenseType = expenseType;
        this.splits = splits;
        this.group = group;
    }

    // Getters
    public String getDescription() { return description; }
    public double getTotalAmount() { return totalAmount; }
    public User getPaidByUser() { return paidByUser; }
    public ExpenseType getExpenseType() { return expenseType; }
    public List<Split> getSplits() { return splits; }
    public Group getGroup() { return group; }
}