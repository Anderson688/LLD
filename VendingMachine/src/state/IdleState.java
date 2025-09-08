package state;

import core.VendingMachine;
import payment.Payment;

public class IdleState implements VendingMachineState {
    @Override
    public void payAmount(VendingMachine machine, Payment paymentStrategy, double amount) {
//        System.out.println("$" + amount + " inserted.");
//        machine.setAmount(machine.getAmount() + amount);
//        machine.setState(new CoinInsertedState());
        System.out.println("Please select an item first.");
    }

    @Override
    public void selectItem(VendingMachine machine, String itemCode) {
//        System.out.println("Please insert money first.");
        System.out.println("Item " + itemCode + " selected.");
        if (!machine.isItemAvailable(itemCode)) {
            System.out.println("Item " + itemCode + " is out of stock. Please select another item.");
            return;
        }
        machine.setSelectedItemCode(itemCode);
        machine.setState(new ItemSelectedState());
    }

    @Override
    public void dispenseItem(VendingMachine machine) {
        System.out.println("Invalid operation: No item selected.");
    }

    @Override
    public void cancel(VendingMachine machine) {
        System.out.println("Invalid operation: Nothing to cancel.");
    }
}