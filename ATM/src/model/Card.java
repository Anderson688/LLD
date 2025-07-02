package model;

public class Card {
    private final String cardNumber;
    private final BankAccount bankAccount;

    public Card(String cardNumber, BankAccount bankAccount) {
        this.cardNumber = cardNumber;
        this.bankAccount = bankAccount;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}