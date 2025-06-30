package state;

import core.VendingMachine;
import inventory.Item;

public class CoinInsertedState implements VendingMachineState {
    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        machine.setAmount(machine.getAmount() + amount);
        System.out.println("Added $" + amount + ". Total: $" + machine.getAmount());
    }

    @Override
    public void selectItem(VendingMachine machine, String itemCode) {
        Item item = machine.getInventory().getItem(itemCode);
        if (item == null) {
            System.out.println("Invalid item code.");
            return;
        }
        if (item.getQuantity() == 0) {
            System.out.println(item.getName() + " is sold out.");
            return;
        }
        if (machine.getAmount() < item.getPrice()) {
            System.out.println("Insufficient funds for " + item.getName() + ". Price: $" + item.getPrice());
            return;
        }
        machine.setSelectedItemCode(itemCode);
        machine.setState(new DispensingState());
        machine.dispenseItem();
    }

    @Override
    public void dispenseItem(VendingMachine machine) {
        System.out.println("Invalid operation: Please select an item first.");
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("Request cancelled. Returning $" + machine.getAmount());
        machine.setAmount(0);
        machine.setState(new NoCoinInsertedState());
    }
}