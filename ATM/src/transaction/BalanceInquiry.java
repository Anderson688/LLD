package transaction;

import model.Card;
import model.User;

public class BalanceInquiry implements Transaction {
    @Override
    public void execute(Card card, int amount) {
        // The 'amount' parameter is ignored for this transaction type
        System.out.println("Current balance is: $" + card.getBankAccount().getBalance());
    }
}