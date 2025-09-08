package core;

import inventory.Inventory;
import payment.Payment;
import state.IdleState;
import state.VendingMachineState;

public class VendingMachine {
    private VendingMachineState state;
    private final Inventory inventory;
    private double amount;
    private String selectedItemCode;

    public VendingMachine() {
        this.inventory = new Inventory();
        this.state = new IdleState();
    }

    public void payAmount(Payment payment, double amount) {
        state.payAmount(this, payment, amount);
    }

    public void selectItem(String itemCode) {
        state.selectItem(this, itemCode);
    }

    public void dispenseItem() {
        state.dispenseItem(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public void setState(VendingMachineState state) { this.state = state; }
    public Inventory getInventory() { return inventory; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getSelectedItemCode() { return selectedItemCode; }
    public void setSelectedItemCode(String code) { this.selectedItemCode = code; }

    public boolean isItemAvailable(String itemCode) {
        return inventory.isItemAvailable(itemCode);
    }
}