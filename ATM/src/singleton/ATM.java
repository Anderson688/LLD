package singleton;

import hardware.CashDispenser;
import model.Card;
import state.ATMState;
import state.IdleState;
import transaction.TransactionType;

public class ATM {
    private static final ATM instance = new ATM();
    private ATMState currentState;
    private Card currentCard;
    private final CashDispenser cashDispenser;

    private ATM() {
        this.currentState = new IdleState();
        this.cashDispenser = new CashDispenser();
    }

    public static ATM getInstance() {
        return instance;
    }

    public void setCurrentState(ATMState newState) {
        this.currentState = newState;
    }

    public void setCurrentCard(Card card) {
        this.currentCard = card;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public void clearCurrentCard() {
        this.currentCard = null;
    }

    public CashDispenser getCashDispenser() {
        return cashDispenser;
    }

    public void insertCard(Card card) {
        currentState.insertCard(this, card);
    }

    public void enterPin(int pin) {
        currentState.enterPin(this, pin);
    }

    public void selectTransaction(TransactionType type) {
        currentState.selectTransaction(this, type);
    }

    public void processTransaction(int amount) {
        currentState.processTransaction(this, amount);
    }

    public void ejectCard() {
        currentState.ejectCard(this);
    }
}