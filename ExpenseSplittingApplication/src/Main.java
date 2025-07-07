import enums.ExpenseType;
import models.Expense;
import models.Group;
import models.Split;
import models.User;
import services.ExpenseSplittingService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ExpenseSplittingService service = new ExpenseSplittingService();

        // Add Users
        User u1 = new User("u1", "Alice");
        User u2 = new User("u2", "Bob");
        User u3 = new User("u3", "Charlie");
        service.addUser(u1);
        service.addUser(u2);
        service.addUser(u3);

        // --- Scenario 1: Group Expense ---
        System.out.println("---- Scenario 1: Group Expense ----");
        Group goaTrip = service.createGroup("g1", "Goa Trip", Arrays.asList(u1, u2, u3));

        // Alice pays 30000 for flights, split equally among the group members.
        List<Split> equalSplits = goaTrip.getMembers().stream().map(Split::new).collect(Collectors.toList());
        Expense flightExpense = new Expense("Flights", 30000, u1, ExpenseType.EQUAL, equalSplits, goaTrip);
        service.addExpense(flightExpense);

        service.showGroupBalances("g1");
        System.out.println();

        // --- Scenario 2: Direct Expense ---
        System.out.println("---- Scenario 2: Direct Expense ----");
        // Charlie pays 1500 for dinner, only Bob owes him.
        Split dinnerSplit = new Split(u2);
        dinnerSplit.setAmount(1500); // For EXACT splits, set amount directly
        Expense dinnerExpense = new Expense("Dinner", 1500, u3, ExpenseType.EXACT, List.of(dinnerSplit), null);
        service.addExpense(dinnerExpense);

        // --- Final Balances ---
        service.showAllBalances();
    }
}