package hardware;

public abstract class DenominationHandler {
    protected DenominationHandler nextHandler;
    protected int denomination;
    protected int notesAvailable;

    public DenominationHandler(int denomination) {
        this.denomination = denomination;
        this.notesAvailable = 0;
    }

    public void setNextHandler(DenominationHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void addNotes(int count) {
        if (count > 0) {
            this.notesAvailable += count;
            System.out.println("Added " + count + " x " + denomination + " notes. Total " + denomination + " notes: " + notesAvailable);
        }
    }

    public int getNotesAvailable() {
        return notesAvailable;
    }

    public abstract boolean dispense(int amount);
}