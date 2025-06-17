import java.util.List;

public interface ElevatorDispatcher {

    Elevator assignElevator(ElevatorRequest request, List<Elevator> elevators);
}