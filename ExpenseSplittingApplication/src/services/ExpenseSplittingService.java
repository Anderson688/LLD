package services;

import factories.ExpenseStrategyFactory;
import models.Expense;
import models.Group;
import models.Split;
import models.User;
import repositories.InMemoryStorage;
import strategies.ExpenseStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseSplittingService {

    private final InMemoryStorage storage = InMemoryStorage.getInstance();

    public void addUser(User user) { storage.addUser(user); }

    public Group createGroup(String id, String name, List<User> members) {
        Group group = new Group(id, name, members);
        storage.addGroup(group);
        return group;
    }

    public void addExpense(Expense expense) {
        // Direct instantiation of strategy
        ExpenseStrategy strategy = ExpenseStrategyFactory.getStrategy(expense.getExpenseType());
        strategy.calculateSplits(expense);
        storage.addExpense(expense);
        updateBalances(expense);
    }

    private void updateBalances(Expense expense) {
        User paidByUser = expense.getPaidByUser();
        Map<User, Map<User, Double>> balanceSheet = storage.getBalanceSheet();

        for (Split split : expense.getSplits()) {
            User owedByUser = split.getUser();
            double amount = split.getAmount();

            if (!owedByUser.equals(paidByUser)) {
                // Paid by user is owed money
                balanceSheet.get(paidByUser).put(owedByUser, balanceSheet.get(paidByUser).getOrDefault(owedByUser, 0.0) + amount);
                // Owed by user owes money
                balanceSheet.get(owedByUser).put(paidByUser, balanceSheet.get(owedByUser).getOrDefault(paidByUser, 0.0) - amount);
            }
        }
    }

    public void showAllBalances() {
        System.out.println("--- Consolidated Balances ---");
        printBalances(storage.getBalanceSheet());
    }

    public void showGroupBalances(String groupId) {
        Group group = storage.getGroup(groupId);
        System.out.println("--- Balances for Group: " + group.getName() + " ---");

        List<Expense> groupExpenses = storage.getExpenses().stream()
                .filter(e -> e.getGroup() != null && groupId.equals(e.getGroup().getId()))
                .toList();

        // Create a temporary balance sheet for this group
        Map<User, Map<User, Double>> groupBalanceSheet = new HashMap<>();
        group.getMembers().forEach(member -> groupBalanceSheet.put(member, new HashMap<>()));

        for (Expense expense : groupExpenses) {
            User paidByUser = expense.getPaidByUser();
            for (Split split : expense.getSplits()) {
                User owedByUser = split.getUser();
                if (!owedByUser.equals(paidByUser)) {
                    double amount = split.getAmount();
                    groupBalanceSheet.get(paidByUser).put(owedByUser, groupBalanceSheet.get(paidByUser).getOrDefault(owedByUser, 0.0) + amount);
                    groupBalanceSheet.get(owedByUser).put(paidByUser, groupBalanceSheet.get(owedByUser).getOrDefault(paidByUser, 0.0) - amount);
                }
            }
        }
        printBalances(groupBalanceSheet);
    }

    private void printBalances(Map<User, Map<User, Double>> balanceSheet) {
        boolean noBalances = true;
        for (Map.Entry<User, Map<User, Double>> entry : balanceSheet.entrySet()) {
            for (Map.Entry<User, Double> userBalance : entry.getValue().entrySet()) {
                if (userBalance.getValue() > 0) {
                    noBalances = false;
                    System.out.printf("%s owes %s: Rs. %.2f%n", userBalance.getKey().getName(), entry.getKey().getName(), userBalance.getValue());
                }
            }
        }
        if (noBalances) {
            System.out.println("No balances to show.");
        }
    }
}