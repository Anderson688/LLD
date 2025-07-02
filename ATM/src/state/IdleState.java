package state;

import model.Card;
import singleton.ATM;
import transaction.TransactionType;

public class IdleState implements ATMState {
    @Override
    public void insertCard(ATM atm, Card card) {
        atm.setCurrentCard(card);
        System.out.println("Card inserted. Please enter your PIN.");
        atm.setCurrentState(new CardInsertedState());
    }

    @Override
    public void enterPin(ATM atm, int pin) {
        System.out.println("ERROR: Please insert a card first.");
    }

    @Override
    public void selectTransaction(ATM atm, TransactionType type) {
        System.out.println("ERROR: Please insert a card first.");
    }

    @Override
    public void processTransaction(ATM atm, int amount) {
        System.out.println("ERROR: Please insert a card first.");
    }

    @Override
    public void ejectCard(ATM atm) {
        System.out.println("ERROR: No card to eject.");
    }
}