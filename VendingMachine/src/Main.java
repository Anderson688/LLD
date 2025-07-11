import core.VendingMachine;
import inventory.Item;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        VendingMachine machine = new VendingMachine();

        // Load items into inventory
        Item coke = new Item("C01", "Coke", 1.50, 5);
        Item chips = new Item("P01", "Chips", 2.00, 3);
        machine.getInventory().addItem(coke);
        machine.getInventory().addItem(chips);

        System.out.println("--- Vending Machine Initialized ---");

        // Scenario 1: Successful purchase
        System.out.println("\n--- Scenario 1: Successful Purchase ---");
        machine.insertMoney(2.00);
        machine.selectItem("C01");

        // Scenario 2: Insufficient funds
        System.out.println("\n--- Scenario 2: Insufficient Funds ---");
        machine.insertMoney(1.00);
        machine.selectItem("P01");

        // Scenario 3: Cancel request
        System.out.println("\n--- Scenario 3: Cancel Request ---");
        machine.insertMoney(5.00);
        machine.cancel();

        // Scenario 4: Item sold out
        System.out.println("\n--- Scenario 4: Item Sold Out (let's buy all chips first) ---");
        for (int i = 0; i < 3; i++) {
            machine.insertMoney(2.00);
            machine.selectItem("P01");
        }
        System.out.println("\nTrying to buy one more bag of chips...");
        machine.insertMoney(2.00);
        machine.selectItem("P01");
    }
}