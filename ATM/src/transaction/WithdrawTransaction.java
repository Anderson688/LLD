package transaction;

import model.Card;
import singleton.ATM;

public class WithdrawTransaction implements Transaction {
    @Override
    public void execute(Card card, int amount) {
        if (amount <= 0) {
            System.out.println("ERROR: Withdrawal amount must be positive.");
            return;
        }
        if (amount % 100 != 0) {
            System.out.println("ERROR: Withdrawal amount must be in multiples of 100.");
            return;
        }

        if (card.getBankAccount().getBalance() >= amount) {
            // Check if ATM has enough cash before debiting from account
            if (ATM.getInstance().getCashDispenser().isAmountDispensable(amount)) {
                boolean dispensed = ATM.getInstance().getCashDispenser().dispenseCash(amount);
                if (dispensed) {
                    card.getBankAccount().withdraw(amount);
                    System.out.println("✅ Withdrawal of $" + amount + " successful.");
                } else {
                    System.out.println("❌ ATM does not have sufficient notes for this amount (or exact change).");
                }
            } else {
                System.out.println("❌ ATM does not have enough cash to dispense this amount.");
            }
        } else {
            System.out.println("❌ Insufficient funds in your bank account for withdrawal.");
        }
    }
}