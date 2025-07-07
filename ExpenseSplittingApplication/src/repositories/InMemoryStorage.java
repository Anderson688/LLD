package repositories;

import models.Expense;
import models.Group;
import models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryStorage {
    private static final InMemoryStorage INSTANCE = new InMemoryStorage();

    private final Map<String, User> userMap = new HashMap<>();
    private final Map<String, Group> groupMap = new HashMap<>();
    private final List<Expense> expenseList = new ArrayList<>();
    private final Map<User, Map<User, Double>> balanceSheet = new HashMap<>();

    private InMemoryStorage() {}

    public static InMemoryStorage getInstance() {
        return INSTANCE;
    }

    public void addUser(User user) {
        userMap.put(user.getId(), user);
        balanceSheet.put(user, new HashMap<>());
    }

    public void addGroup(Group group) {
        groupMap.put(group.getId(), group);
    }

    public Group getGroup(String groupId) {
        return groupMap.get(groupId);
    }

    public void addExpense(Expense expense) {
        expenseList.add(expense);
    }

    public List<Expense> getExpenses() {
        return expenseList;
    }

    public Map<User, Map<User, Double>> getBalanceSheet() {
        return balanceSheet;
    }
}