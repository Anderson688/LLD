import java.util.List;

public class FCFSDispatcher implements ElevatorDispatcher {

    @Override
    public Elevator assignElevator(ElevatorRequest request, List<Elevator> elevators) {
        for (Elevator elevator : elevators) {
            if (elevator.getState() == ElevatorState.IDLE) {
                System.out.println("Dispatcher: Assigned " + request + " to IDLE Elevator " + elevator.getId());
                return elevator;
            }
        }

        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;

        for (Elevator elevator : elevators) {
            int distance = Math.abs(elevator.getCurrentFloor() - request.getTargetFloor());

            boolean canPickUp = false;
            if (elevator.getDirection() == request.getDirection() && request.getDirection() != null) {
                if (request.getDirection() == Direction.UP && request.getTargetFloor() >= elevator.getCurrentFloor()) {
                    canPickUp = true;
                } else if (request.getDirection() == Direction.DOWN && request.getTargetFloor() <= elevator.getCurrentFloor()) {
                    canPickUp = true;
                }
            }
            else if (elevator.getState() == ElevatorState.IDLE) {
                canPickUp = true;
            }

            if (canPickUp && distance < minDistance) {
                minDistance = distance;
                bestElevator = elevator;
            }
        }

        if (bestElevator != null) {
            System.out.println("Dispatcher: Assigned " + request + " to Elevator " + bestElevator.getId() + " (closest/in-direction).");
            return bestElevator;
        }

        System.out.println("Dispatcher: No ideal elevator found for " + request + ", assigning to first available.");
        return elevators.getFirst();
    }
}