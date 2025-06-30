package core;

import inventory.Inventory;
import payment.CoinPaymentStrategy;
import payment.PaymentStrategy;
import state.NoCoinInsertedState;
import state.VendingMachineState;

public class VendingMachine {
    private VendingMachineState state;
    private final Inventory inventory;
    private PaymentStrategy paymentStrategy;
    private double amount;
    private String selectedItemCode;

    public VendingMachine() {
        this.inventory = new Inventory();
        this.state = new NoCoinInsertedState();
        this.paymentStrategy = new CoinPaymentStrategy(); // Default payment strategy
    }

    public void insertMoney(double amount) {
        if (paymentStrategy.pay(amount)) {
            state.insertMoney(this, amount);
        }
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
    public void setPaymentStrategy(PaymentStrategy strategy) { this.paymentStrategy = strategy; }
    public Inventory getInventory() { return inventory; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getSelectedItemCode() { return selectedItemCode; }
    public void setSelectedItemCode(String code) { this.selectedItemCode = code; }
}