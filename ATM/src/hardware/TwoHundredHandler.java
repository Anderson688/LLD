package hardware;

public class TwoHundredHandler extends DenominationHandler {
    public TwoHundredHandler() {
        super(200);
    }

    @Override
    public boolean dispense(int amount) {
        int numNotesToDispense = amount / denomination;
        int notesCanDispense = Math.min(numNotesToDispense, notesAvailable);

        if (notesCanDispense > 0) {
            System.out.println("Dispensing " + notesCanDispense + " x " + denomination + " note(s).");
            notesAvailable -= notesCanDispense;
        }

        int remainingAmount = amount - (notesCanDispense * denomination);

        if (remainingAmount > 0) {
            if (nextHandler != null) {
                return nextHandler.dispense(remainingAmount);
            } else {
                System.out.println("ERROR: Cannot dispense exact amount. Remaining: " + remainingAmount);
                return false;
            }
        }
        return true;
    }
}