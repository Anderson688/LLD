//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        int numElevators = 2;
        int numFloors = 10;

        System.out.println("Initializing Elevator System with " + numElevators + " elevators and " + numFloors + " floors.");
        ElevatorSystem elevatorSystem = new ElevatorSystem(numElevators, numFloors);

        // Start the ElevatorSystem in a separate thread
        Thread systemThread = new Thread(elevatorSystem);
        systemThread.start();

        System.out.println("\n--- Initial System State ---");
        for (Elevator el : elevatorSystem.getElevators()) {
            System.out.println("Elevator " + el.getId() + " at Floor: " + el.getCurrentFloor() + ", State: " + el.getState());
        }

        // --- Simulate Requests ---

        // Passenger 1: Floor 2 -> 7
        elevatorSystem.simulatePassengerRequest(1, 2, 7);
        Thread.sleep(5000); // Give some time for processing

        // Passenger 2: Floor 9 -> 3
        elevatorSystem.simulatePassengerRequest(2, 9, 3);
        Thread.sleep(5000);

        // Passenger 3: Floor 1 -> 5 (if elevator is free)
        elevatorSystem.simulatePassengerRequest(3, 1, 5);
        Thread.sleep(5000);

        // Passenger 4: Floor 6 -> 2
        elevatorSystem.simulatePassengerRequest(4, 6, 2);
        Thread.sleep(5000);


        // Allow the system to run for a while
        System.out.println("\nSimulations complete. Letting system run for a bit...");
        Thread.sleep(10000); // Run for 10 more seconds

        // Shut down the system
        System.out.println("\nShutting down elevator system...");
        elevatorSystem.stopSystem();
        systemThread.join(); // Wait for the system thread to finish
        System.out.println("System shut down successfully.");
    }
}