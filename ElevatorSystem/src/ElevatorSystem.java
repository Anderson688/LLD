import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorSystem implements Runnable {

    private final List<Elevator> elevators;

    private final List<Floor> floors;

    private final Queue<ElevatorRequest> requestQueue;

    private ElevatorDispatcher dispatcher;

    private final int numFloors;

    private final int numElevators;

    private volatile boolean running = true;

    public ElevatorSystem(int numElevators, int numFloors) {
        this.elevators = new ArrayList<>();
        this.floors = new ArrayList<>();
        this.requestQueue = new ConcurrentLinkedQueue<>();
        this.dispatcher = new FCFSDispatcher();
        this.numElevators = numElevators;
        this.numFloors = numFloors;

        initializeFloors();
        initializeElevators();
    }

    private void initializeElevators() {
        for (int i = 0; i < numElevators; i++) {
            elevators.add(new Elevator(i, this));
        }
    }

    private void initializeFloors() {
        for (int i = 0; i <= numFloors; i++) {
            floors.add(new Floor(i, this));
        }
    }

    public void setDispatcher(ElevatorDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Floor getFloor(int floorNumber) {
        if (floorNumber >= 0 && floorNumber < floors.size())
            return floors.get(floorNumber);
        return null;
    }

    public int getMaxFloor() {
        return this.floors.size() - 1;
    }

    public void addRequest(ElevatorRequest request) {
        requestQueue.offer(request); // Add to the request queue
        System.out.println("ElevatorSystem: Added new request: " + request);
    }

    @Override
    public void run() {
        System.out.println("ElevatorSystem started running.");
        // Start elevator threads
        ExecutorService elevatorExecutor = Executors.newFixedThreadPool(numElevators);
        for (Elevator elevator : elevators) {
            elevatorExecutor.submit(elevator);
        }

        // Process requests continuously
        while (running) {
            if (!requestQueue.isEmpty()) {
                ElevatorRequest request = requestQueue.poll(); // Get the next request
                if (request != null) {
                    Elevator assignedElevator = dispatcher.assignElevator(request, elevators);
                    if (assignedElevator != null) {
                        assignedElevator.addRequest(request);
                    } else {
                        System.err.println("ElevatorSystem: No elevator found to assign request: " + request);
                        requestQueue.offer(request); // Put it back if no elevator can take it right now
                    }
                }
            }

            // Small delay to prevent busy-waiting
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
        System.out.println("ElevatorSystem stopping...");
        elevatorExecutor.shutdownNow(); // Stop all elevator threads
        System.out.println("ElevatorSystem stopped.");
    }

    public void stopSystem() {
        this.running = false;
    }

    // Method to simulate initial passenger interaction
    public void simulatePassengerRequest(int passengerId, int currentFloor, int destinationFloor) {
        System.out.println("\n--- Simulating Passenger " + passengerId + " Request ---");
        Passenger p = new Passenger(passengerId, currentFloor, destinationFloor);
        System.out.println(p);

        Floor floor = getFloor(currentFloor);
        if (floor != null) {
            if (destinationFloor > currentFloor) {
                floor.getUpButton().press();
            } else if (destinationFloor < currentFloor) {
                floor.getDownButton().press();
            } else {
                System.out.println("Passenger " + passengerId + " is already at destination floor " + currentFloor + ".");
            }
        }
    }

    public List<Elevator> getElevators() {
        return this.elevators;
    }
}