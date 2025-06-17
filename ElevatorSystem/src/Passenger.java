public class Passenger {
    private final int id;
    private int currentFloor;
    private int destinationFloor;

    public Passenger(int id, int currentFloor, int destinationFloor) {
        this.id = id;
        this.currentFloor = currentFloor;
        this.destinationFloor = destinationFloor;
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    @Override
    public String toString() {
        return "Passenger " + id + " (from " + currentFloor + " to " + destinationFloor + ")";
    }
}