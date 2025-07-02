package state;

import model.Card;
import singleton.ATM;
import transaction.*;

public class PinEnteredState implements ATMState {

    private Transaction selectedTransaction;

    @Override
    public void selectTransaction(ATM atm, TransactionType type) {
        switch (type) {
            case BALANCE_INQUIRY:
                // Balance inquiry executes immediately
                System.out.println("-> Balance Inquiry selected.");
                new BalanceInquiry().execute(atm.getCurrentCard(), 0);
                break;
            case WITHDRAWAL:
                System.out.println("-> Withdrawal selected. Please specify amount.");
                this.selectedTransaction = new WithdrawTransaction();
                break;
            case DEPOSIT:
                System.out.println("-> Deposit selected. Please specify amount.");
                this.selectedTransaction = new DepositTransaction();
                break;
            default:
                System.out.println("ERROR: Invalid transaction type.");
        }
    }

    @Override
    public void processTransaction(ATM atm, int amount) {
        if (selectedTransaction != null) {
            selectedTransaction.execute(atm.getCurrentCard(), amount);
            this.selectedTransaction = null; // Reset after execution
        } else {
            System.out.println("ERROR: Please select a transaction type (Withdrawal/Deposit) first.");
        }
    }


    @Override
    public void ejectCard(ATM atm) {
        System.out.println("Card ejected. Thank you for using our ATM.");
        atm.clearCurrentCard();
        atm.setCurrentState(new IdleState());
    }

    @Override
    public void insertCard(ATM atm, Card card) {
        System.out.println("ERROR: A session is already active.");
    }

    @Override
    public void enterPin(ATM atm, int pin) {
        System.out.println("ERROR: PIN has already been entered.");
    }
}