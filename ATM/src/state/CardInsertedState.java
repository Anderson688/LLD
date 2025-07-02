package state;

import model.Card;
import singleton.ATM;
import transaction.TransactionType;

public class CardInsertedState implements ATMState {

    @Override
    public void enterPin(ATM atm, int pin) {
        if (authenticate(pin)) {
            System.out.println("✅ PIN entered successfully.");
            atm.setCurrentState(new PinEnteredState());
        } else {
            System.out.println("❌ Incorrect PIN. Ejecting card.");
            // We call the ejectCard method to handle the state transition
            ejectCard(atm);
        }
    }

    @Override
    public void ejectCard(ATM atm) {
        System.out.println("Card ejected. Please take your card.");
        atm.setCurrentState(new IdleState());
    }

    @Override
    public void insertCard(ATM atm, Card card) {
        System.out.println("ERROR: A card is already inserted. Please eject the current card first.");
    }

    @Override
    public void selectTransaction(ATM atm, TransactionType type) {
        System.out.println("ERROR: Please enter your PIN first to select a transaction.");
    }

    @Override
    public void processTransaction(ATM atm, int amount) {
        System.out.println("ERROR: Please enter your PIN first.");
    }

    private boolean authenticate(int pin) {
        // For this simulation, we use a dummy PIN.
        return pin == 1234;
    }
}