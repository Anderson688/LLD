package state;

import core.VendingMachine;

public class NoCoinInsertedState implements VendingMachineState {
    @Override
    public void insertMoney(VendingMachine machine, double amount) {
        System.out.println("$" + amount + " inserted.");
        machine.setAmount(machine.getAmount() + amount);
        machine.setState(new CoinInsertedState());
    }

    @Override
    public void selectItem(VendingMachine machine, String itemCode) {
        System.out.println("Please insert money first.");
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