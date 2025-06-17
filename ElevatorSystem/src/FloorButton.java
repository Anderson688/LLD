public class FloorButton extends Button {

    private final int floorNumber;

    private final Direction direction;

    private final ElevatorSystem elevatorSystem;

    public FloorButton(String label, int floorNumber, Direction direction, ElevatorSystem elevatorSystem) {
        super(label);
        this.floorNumber = floorNumber;
        this.direction = direction;
        this.elevatorSystem = elevatorSystem;
    }

    @Override
    public void onPress() {
        System.out.println(this.direction + " pressed on floor " + floorNumber + ".");
        ElevatorRequest request = new ElevatorRequest(floorNumber, direction);
        elevatorSystem.addRequest(request);
    }
}