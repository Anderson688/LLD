public class Door {

    private DoorState state;

    public Door() {
        this.state = DoorState.CLOSED;
    }

    public void open() {
        if (state == DoorState.CLOSED || state == DoorState.CLOSING) {
            state = DoorState.OPENING;
            System.out.println("Door is opening...");
            // Simulate delay
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            state = DoorState.OPEN;
            System.out.println("Door is OPEN.");
        }
    }

    public void close() {
        if (state == DoorState.OPEN || state == DoorState.OPENING) {
            state = DoorState.CLOSING;
            System.out.println("Door is closing...");
            // Simulate delay
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            state = DoorState.CLOSED;
            System.out.println("Door is CLOSED.");
        }
    }

    public DoorState getState() {
        return this.state;
    }
}