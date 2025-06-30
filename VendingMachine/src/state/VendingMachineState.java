package state;

import core.VendingMachine;

public interface VendingMachineState {
    void insertMoney(VendingMachine machine, double amount);
    void selectItem(VendingMachine machine, String itemCode);
    void dispenseItem(VendingMachine machine);
    void cancel(VendingMachine machine);
}