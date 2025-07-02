package state;

import model.Card;
import singleton.ATM;
import transaction.TransactionType;

public interface ATMState {
    void insertCard(ATM atm, Card card);
    void enterPin(ATM atm, int pin);
    void selectTransaction(ATM atm, TransactionType type);
    void processTransaction(ATM atm, int amount);
    void ejectCard(ATM atm);
}