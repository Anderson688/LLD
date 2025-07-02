package hardware;

import java.util.HashMap;
import java.util.Map;

public class CashDispenser {
    private final DenominationHandler firstHandler;
    private final Map<Integer, DenominationHandler> handlersMap; // To quickly access handlers by denomination

    public CashDispenser() {
        // Initialize handlers and build the chain of responsibility
        TwoThousandHandler twoThousand = new TwoThousandHandler();
        FiveHundredHandler fiveHundred = new FiveHundredHandler();
        TwoHundredHandler twoHundred = new TwoHundredHandler();
        OneHundredHandler oneHundred = new OneHundredHandler();

        twoThousand.setNextHandler(fiveHundred);
        fiveHundred.setNextHandler(twoHundred);
        twoHundred.setNextHandler(oneHundred);

        this.firstHandler = twoThousand;

        // Store handlers in a map for easy access (e.g., for adding notes)
        handlersMap = new HashMap<>();
        handlersMap.put(2000, twoThousand);
        handlersMap.put(500, fiveHundred);
        handlersMap.put(200, twoHundred);
        handlersMap.put(100, oneHundred);
    }

    public void addNotes(int denomination, int count) {
        DenominationHandler handler = handlersMap.get(denomination);
        if (handler != null) {
            handler.addNotes(count);
        } else {
            System.out.println("Warning: No handler for denomination " + denomination);
        }
    }

    public int getTotalCashAvailable() {
        int total = 0;
        for (Map.Entry<Integer, DenominationHandler> entry : handlersMap.entrySet()) {
            total += entry.getKey() * entry.getValue().getNotesAvailable();
        }
        return total;
    }

    public boolean isAmountDispensable(int amount) {
        if (amount % 100 != 0) {
            return false; // Must be in multiples of 100
        }
        return getTotalCashAvailable() >= amount;
    }

    public boolean dispenseCash(int amount) {
        if (amount <= 0) {
            System.out.println("ERROR: Amount to dispense must be positive.");
            return false;
        }
        if (amount % 100 != 0) {
            System.out.println("ERROR: Withdrawal amount must be in multiples of 100.");
            return false;
        }

        // Before attempting to dispense, check if we even have enough total cash
        if (getTotalCashAvailable() < amount) {
            System.out.println("ERROR: ATM does not have enough total cash to dispense $" + amount + ". Available: $" + getTotalCashAvailable());
            return false;
        }

        System.out.println("--- Attempting to Dispense Cash: $" + amount + " ---");
        // The firstHandler's dispense method will propagate through the chain
        boolean success = firstHandler.dispense(amount);
        if (!success) {
            System.out.println("Failed to dispense exact amount. Please try a different amount.");
            // In a real system, if it failed mid-way, you'd need to put back dispensed notes.
        } else {
            System.out.println("Cash dispensed successfully.");
        }
        System.out.println("-------------------------------------");
        return success;
    }
}