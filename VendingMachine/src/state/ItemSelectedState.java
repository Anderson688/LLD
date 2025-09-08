package state;

import core.VendingMachine;
import payment.Payment;

public class ItemSelectedState implements VendingMachineState {

    @Override
    public void selectItem(VendingMachine vendingMachine, String itemCode) {
        if (!vendingMachine.isItemAvailable(itemCode)) {
            System.out.println("ERROR: Selected item is not available.");
            return;
        }
        System.out.println("Selected item updated to: " + itemCode);
        vendingMachine.setSelectedItemCode(itemCode);
    }

    @Override
    public void payAmount(VendingMachine vendingMachine, Payment paymentStrategy, double amount) {
        if (amount <= 0) {
            System.out.println("ERROR: Inserted amount must be positive.");
            return;
        }
        if (paymentStrategy.pay(amount)) {
            vendingMachine.setAmount(vendingMachine.getAmount() + amount);
        }
        System.out.println("Amount paid: $" + amount + ". Total amount: $" + vendingMachine.getAmount());
        if (vendingMachine.getAmount() >= vendingMachine.getInventory().getItem(vendingMachine.getSelectedItemCode()).getPrice()) {
            System.out.println("Sufficient amount paid. Dispensing item: " + vendingMachine.getSelectedItemCode());
            vendingMachine.setState(new DispensingState());
            vendingMachine.dispenseItem();
        } else {
            System.out.println("Please insert additional amount to reach the item price of $" + vendingMachine.getInventory().getItem(vendingMachine.getSelectedItemCode()).getPrice());
        }
    }

    @Override
    public void dispenseItem(VendingMachine vendingMachine) {
        System.out.println("ERROR: Please pay the required amount before dispensing the item.");
    }

    @Override
    public void cancel(VendingMachine vendingMachine) {
        System.out.println("Transaction cancelled. Returning to Idle State.");
        if (vendingMachine.getAmount() > 0) {
            System.out.println("Please collect your change: $" + String.format("%.2f", vendingMachine.getAmount()));
        }
        vendingMachine.setAmount(0);
        vendingMachine.setSelectedItemCode(null);
        vendingMachine.setState(new IdleState());
    }
}