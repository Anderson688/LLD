package state;

import core.VendingMachine;
import payment.Payment;

public interface VendingMachineState {
    void payAmount(VendingMachine machine, Payment paymentStrategy, double amount);
    void selectItem(VendingMachine machine, String itemCode);
    void dispenseItem(VendingMachine machine);
    void cancel(VendingMachine machine);
}