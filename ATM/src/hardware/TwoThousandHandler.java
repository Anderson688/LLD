package hardware;

public class TwoThousandHandler extends DenominationHandler {
    public TwoThousandHandler() {
        super(2000);
    }

    @Override
    public boolean dispense(int amount) {
        int numNotesToDispense = amount / denomination;
        int notesCanDispense = Math.min(numNotesToDispense, notesAvailable); // Can only dispense what's available

        if (notesCanDispense > 0) {
            System.out.println("Dispensing " + notesCanDispense + " x " + denomination + " note(s).");
            notesAvailable -= notesCanDispense; // Decrement available notes
        }

        int remainingAmount = amount - (notesCanDispense * denomination);

        if (remainingAmount > 0) {
            if (nextHandler != null) {
                return nextHandler.dispense(remainingAmount);
            } else {
                System.out.println("ERROR: Cannot dispense exact amount. Remaining: " + remainingAmount);
                return false; // Could not dispense exact amount
            }
        }
        return true;
    }
}