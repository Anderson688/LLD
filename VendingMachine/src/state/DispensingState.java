package state;

import core.VendingMachine;
import inventory.Item;

public class DispensingState implements VendingMachineState {
    @Override
    public void dispenseItem(VendingMachine machine) {
        Item item = machine.getInventory().getItem(machine.getSelectedItemCode());
        System.out.println("Dispensing " + item.getName() + "...");

        double change = machine.getAmount() - item.getPrice();
        if (change > 0) {
            System.out.println("Please collect your change: $" + String.format("%.2f", change));
        }

        machine.getInventory().updateStock(item.getItemCode(), item.getQuantity() - 1);
        machine.setAmount(0);
        machine.setSelectedItemCode(null);
        machine.setState(new NoCoinInsertedState());
        System.out.println("Thank you!");
    }

    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("Invalid operation: Currently dispensing an item.");
    }

    @Override
    public void selectItem(VendingMachine machine, String itemCode) {
        System.out.println("Invalid operation: Currently dispensing an item.");
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("Invalid operation: Cannot cancel while dispensing.");
    }
}