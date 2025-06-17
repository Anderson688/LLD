public class ElevatorRequest {

    private final int targetFloor;

    private final Direction direction;

    private final long timestamp;

    public ElevatorRequest(int targetFloor, Direction direction) {
        this.targetFloor = targetFloor;
        this.direction = direction;
        this.timestamp = System.currentTimeMillis();
    }

    public int getTargetFloor() {
        return this.targetFloor;
    }

    public Direction getDirection() {
        return this.direction;
    }
}