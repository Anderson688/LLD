public class Floor {

    private final int floorNumber;

    private final Button upButton;

    private final Button downButton;

    public Floor(int floorNumber, ElevatorSystem elevatorSystem) {
        this.floorNumber = floorNumber;
        this.upButton = new FloorButton("Up", floorNumber, Direction.UP, elevatorSystem);
        this.downButton = new FloorButton("Down", floorNumber, Direction.DOWN, elevatorSystem);
    }

    public Button getUpButton() {
        return this.upButton;
    }

    public Button getDownButton() {
        return this.downButton;
    }
}