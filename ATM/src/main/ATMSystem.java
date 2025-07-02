package main;

import model.BankAccount;
import model.Card;
import singleton.ATM;
import transaction.TransactionType;

public class ATMSystem {

    public static void main(String[] args) {
        System.out.println("🏧 Welcome to the ATM System! 🏧\n");

        // --- Setup ---
        ATM atm = ATM.getInstance();
        BankAccount account = new BankAccount(25000); // User starts with 25000
        Card card = new Card("1234-5678-9012-3456", account);
        atm.getCashDispenser().addNotes(2000, 10);
        atm.getCashDispenser().addNotes(500, 5);
        atm.getCashDispenser().addNotes(200, 5);
        atm.getCashDispenser().addNotes(100, 5);

        // --- Simulation Flow ---

        // Scenario 1: Successful Withdrawal within limits and cash available
        System.out.println("\n--- Scenario 1: Successful Withdrawal (5800) ---");
        atm.insertCard(card);
        atm.enterPin(1234);
        atm.selectTransaction(TransactionType.WITHDRAWAL);
        atm.processTransaction(5800); // 2x2000, 3x500, 1x200, 1x100
        atm.selectTransaction(TransactionType.BALANCE_INQUIRY); // Check balance
        atm.ejectCard();
        System.out.println("\nATM Total Cash Available: $" + atm.getCashDispenser().getTotalCashAvailable());
        System.out.println("-----------------------------------------\n");

        // Scenario 2: Insufficient Funds in Bank Account
        System.out.println("\n--- Scenario 2: Insufficient Funds in Bank Account (50000) ---");
        atm.insertCard(card);
        atm.enterPin(1234);
        atm.selectTransaction(TransactionType.WITHDRAWAL);
        atm.processTransaction(50000); // More than balance
        atm.ejectCard();
        System.out.println("\nATM Total Cash Available: $" + atm.getCashDispenser().getTotalCashAvailable());
        System.out.println("-----------------------------------------\n");

        // Scenario 3: Deposit and Balance Check
        System.out.println("\n--- Scenario 3: Deposit and Balance Check (1500) ---");
        atm.insertCard(card);
        atm.enterPin(1234);
        atm.selectTransaction(TransactionType.DEPOSIT);
        atm.processTransaction(1500);
        atm.selectTransaction(TransactionType.BALANCE_INQUIRY);
        atm.ejectCard();
        System.out.println("\nATM Total Cash Available: $" + atm.getCashDispenser().getTotalCashAvailable());
        System.out.println("-----------------------------------------\n");

        // Scenario 4: ATM runs out of a specific denomination or total cash
        System.out.println("\n--- Scenario 4: ATM Cash Depletion (Attempting 20000, then 1000) ---");
        atm.insertCard(card);
        atm.enterPin(1234);
        atm.selectTransaction(TransactionType.WITHDRAWAL);
        atm.processTransaction(10000); // This might deplete some 2000s or 500s
        atm.selectTransaction(TransactionType.BALANCE_INQUIRY);

        atm.selectTransaction(TransactionType.WITHDRAWAL);
        atm.processTransaction(10000); // Now try another withdrawal
        atm.selectTransaction(TransactionType.BALANCE_INQUIRY);
        atm.ejectCard();
        System.out.println("\nATM Total Cash Available: $" + atm.getCashDispenser().getTotalCashAvailable());
        System.out.println("-----------------------------------------\n");

        // Scenario 5: Attempting an amount not multiple of 100
        System.out.println("\n--- Scenario 5: Invalid Withdrawal Amount (1250) ---");
        atm.insertCard(card);
        atm.enterPin(1234);
        atm.selectTransaction(TransactionType.WITHDRAWAL);
        atm.processTransaction(1250); // Not a multiple of 100
        atm.ejectCard();
        System.out.println("\nATM Total Cash Available: $" + atm.getCashDispenser().getTotalCashAvailable());
        System.out.println("-----------------------------------------\n");
    }
}