package transaction;

import model.Card;

public class DepositTransaction implements Transaction {
    @Override
    public void execute(Card card, int amount) {
        if (amount <= 0) {
            System.out.println("ERROR: Deposit amount must be positive.");
            return;
        }
        // In a real ATM, this would involve a cash acceptor to verify notes
        card.getBankAccount().deposit(amount);
        System.out.println("✅ Deposit of $" + amount + " successful.");
    }
}