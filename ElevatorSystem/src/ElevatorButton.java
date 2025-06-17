public class ElevatorButton extends Button {

    private final int floorNumber;

    private final Elevator elevator;

    public ElevatorButton(String label, int floorNumber, Elevator elevator) {
        super(label);
        this.floorNumber = floorNumber;
        this.elevator = elevator;
    }

    @Override
    public void onPress() {
        ElevatorRequest request = new ElevatorRequest(floorNumber, null);
        elevator.addRequest(request);
    }
}