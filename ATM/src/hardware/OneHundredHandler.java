package hardware;

public class OneHundredHandler extends DenominationHandler {
    public OneHundredHandler() {
        super(100);
    }

    @Override
    public boolean dispense(int amount) {
        int numNotesToDispense = amount / denomination;
        int notesCanDispense = Math.min(numNotesToDispense, notesAvailable);

        if (numNotesToDispense > 0) { // Only dispense if notes are actually needed for this denomination
            if (notesCanDispense > 0) { // Check if we have notes to dispense
                System.out.println("Dispensing " + notesCanDispense + " x " + denomination + " note(s).");
                notesAvailable -= notesCanDispense;
            } else {
                // We need notes of this denomination but don't have any.
                // This means the amount cannot be completely dispensed by this handler.
                // The remainingAmount check below will handle passing it if there's a next handler,
                // but since this is the last one, it will fail.
            }
        }

        int remainingAmount = amount - (notesCanDispense * denomination);

        if (remainingAmount > 0) {
            if (nextHandler != null) { // Should not happen for OneHundredHandler as it's typically the last
                return nextHandler.dispense(remainingAmount);
            } else {
                System.out.println("ERROR: ATM cannot dispense remaining amount: $" + remainingAmount + ". Please check ATM cash availability.");
                return false; // Could not dispense exact amount
            }
        }
        return true; // Successfully dispensed this part of the amount
    }
}