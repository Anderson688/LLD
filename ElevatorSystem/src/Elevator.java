import java.util.Comparator;
import java.util.PriorityQueue;

public class Elevator implements Runnable {

    private final int id;

    private int currentFloor;

    private Direction direction;

    private ElevatorState state;

    private PriorityQueue<Integer> upDestinations;

    private PriorityQueue<Integer> downDestinations;

    private volatile boolean running;

    private final Object lock = new Object();

    private final Door door;

    private final ElevatorSystem elevatorSystem;

    private final ElevatorButton[] panelButtons;

    public Elevator(int id, ElevatorSystem elevatorSystem) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = null;
        this.state = ElevatorState.IDLE;
        this.running = true;
        this.door = new Door();
        this.elevatorSystem = elevatorSystem;

        this.upDestinations = new PriorityQueue<>();
        this.downDestinations = new PriorityQueue<>(Comparator.reverseOrder());

        // Initialize panel buttons
        int maxFloor = elevatorSystem.getMaxFloor() + 1;
        panelButtons = new ElevatorButton[maxFloor + 1];

        System.out.println("DEBUG: Elevator " + id + " panelButtons array created with size: " + panelButtons.length);
        // You can also print the value of maxFloorNum here to be sure:
        System.out.println("DEBUG: ElevatorSystem.getMaxFloor() returned: " + maxFloor);

        panelButtons[0] = new ElevatorButton("G", 0, this);
        for (int i = 1; i <= maxFloor; i++) {
            panelButtons[i] = new ElevatorButton(Integer.toString(i), i, this);
        }
        System.out.println("DEBUG: Elevator " + id + " panelButtons array populated. Last index used: " + maxFloor);

    }

    public ElevatorState getState() {
        return this.state;
    }

    public int getId() {
        return this.id;
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void addRequest(ElevatorRequest request) {
        synchronized (lock) {
            int targetFloor = request.getTargetFloor();
            if (targetFloor > currentFloor) {
                upDestinations.add(targetFloor);
            } else if (targetFloor < currentFloor) {
                downDestinations.add(targetFloor);
            } else {
                if (direction == Direction.UP) {
                    upDestinations.add(targetFloor);
                } else {
                    downDestinations.add(targetFloor);
                }
            }
            System.out.println("Elevator " + id + ": Added request for floor " + targetFloor + ". Up: " + upDestinations + ", Down: " + downDestinations);
            state = ElevatorState.MOVING;
            lock.notifyAll();
        }
    }

    @Override
    public void run() {
        System.out.println("Elevator " + id + " started running.");
        while (running) {
            synchronized (lock) {
                try {
                    if (upDestinations.isEmpty() && downDestinations.isEmpty() && state == ElevatorState.IDLE) {
                        System.out.println("Elevator " + id + " is IDLE, waiting for requests...");
                        lock.wait();
                    }

                    System.out.println("Outside wait: Elevator " + id + " is processing requests. Current state: " + state);
                    if (direction == null) {
                        Integer nextUpTarget = upDestinations.peek();
                        Integer nextDownTarget = downDestinations.peek();

                        int chosenTarget = -1;
                        Direction chosenDirection = null;

                        if (nextUpTarget != null && nextUpTarget >= currentFloor) {
                            chosenTarget = nextUpTarget;
                            chosenDirection = Direction.UP;
                        } else if (nextDownTarget != null && nextDownTarget <= currentFloor) {
                            chosenTarget = nextDownTarget;
                            chosenDirection = Direction.DOWN;
                        } else if (nextUpTarget != null) {
                            chosenTarget = nextUpTarget;
                            chosenDirection = Direction.DOWN;
                        } else if (nextDownTarget != null) {
                            chosenTarget = nextDownTarget;
                            chosenDirection = Direction.UP;
                        }

                        if (chosenTarget != -1) {
                            this.direction = chosenDirection;
                            if (chosenTarget == currentFloor) {
                                state = ElevatorState.STOPPED;
                            } else {
                                state = ElevatorState.MOVING;
                            }
                        } else {
                            state = ElevatorState.IDLE;
                            continue;
                        }
                    }

                    if (state == ElevatorState.MOVING) {
                        move();
                    }

                    boolean shouldStop = false;
                    if (direction == Direction.UP && upDestinations.contains(currentFloor)) {
                        shouldStop = true;
                    } else if (direction == Direction.DOWN && downDestinations.contains(currentFloor)) {
                        shouldStop = true;
                    }

                    if (shouldStop) {
                        stopAndOpenDoor();
                        state = ElevatorState.IDLE;
                    } else if (state == ElevatorState.STOPPED && door.getState() == DoorState.OPEN) {
                        door.close();
                        state = ElevatorState.IDLE;
                    }

                    if (upDestinations.isEmpty() && downDestinations.isEmpty()) {
                        direction = null;
                        state = ElevatorState.IDLE;
                        System.out.println("Elevator " + id + ": No more destinations. Going IDLE.");
                    } else if (direction == Direction.UP && upDestinations.isEmpty()) {
                        direction = Direction.DOWN;
                        state = ElevatorState.MOVING;
                        System.out.println("Elevator " + id + ": Finished UP requests, switching to DOWN.");
                    } else if (direction == Direction.DOWN && downDestinations.isEmpty()) {
                        direction = Direction.UP;
                        state = ElevatorState.MOVING;
                        System.out.println("Elevator " + id + ": Finished DOWN requests, switching to UP.");
                    } else if (direction == Direction.UP && upDestinations.peek() < currentFloor) {
                        direction = Direction.DOWN;
                        state = ElevatorState.MOVING;
                        System.out.println("Elevator " + id + ": Next UP target (" + upDestinations.peek() + ") is behind us. Switching to DOWN.");
                    } else if (direction == Direction.DOWN && downDestinations.peek() > currentFloor) {
                        direction = Direction.UP;
                        state = ElevatorState.MOVING;
                        System.out.println("Elevator " + id + ": Next DOWN target (" + downDestinations.peek() + ") is above us. Switching to UP.");
                    }

                } catch (InterruptedException e) {
                    System.out.println("Elevator " + id + " was interrupted.");
                    Thread.currentThread().interrupt();
                    running = false;
                } catch (Exception e) {
                    System.err.println("Elevator " + id + " encountered an error: " + e.getMessage());
                    e.printStackTrace();
                    state = ElevatorState.BROKEN;
                    running = false;
                }

                // Small delay to simulate movement/processing time
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }
        System.out.println("Elevator " + id + " stopped running.");
    }

    private void move() {
        if (state != ElevatorState.MOVING) return;

        int nextDestination = -1;
        if (direction == Direction.UP && !upDestinations.isEmpty()) {
            nextDestination = upDestinations.peek();
        } else if (direction == Direction.DOWN && !downDestinations.isEmpty()) {
            nextDestination = downDestinations.peek();
        }

        if (nextDestination == -1) {
            state = ElevatorState.IDLE;
            direction = null;
            return;
        }

        int previousFloor = currentFloor;
        if (direction == Direction.UP && currentFloor < nextDestination && currentFloor < elevatorSystem.getMaxFloor()) {
            currentFloor++;
        } else if (direction == Direction.DOWN && currentFloor > nextDestination && currentFloor > 0) {
            currentFloor--;
        } else {
            state = ElevatorState.STOPPED;
            return;
        }

        System.out.println("Elevator " + id + ": Moving " + direction + " from floor " + previousFloor + " to " + currentFloor + ". State: " + state);
    }

    private void stopAndOpenDoor() {
        state = ElevatorState.STOPPED;
        System.out.println("Elevator " + id + ": Arrived at floor " + currentFloor + ". State: " + state);

        // Remove the current floor from both destination queues if present
        upDestinations.remove(currentFloor);
        downDestinations.remove(currentFloor);

        door.open();
        // Simulate passengers getting on/off
        System.out.println("Elevator " + id + ": Passengers exchanging at floor " + currentFloor + ".");
        // Unpress corresponding floor button if it was an external call
        if (elevatorSystem.getFloor(currentFloor).getUpButton() != null) {
            elevatorSystem.getFloor(currentFloor).getUpButton().unpress();
        }
        if (elevatorSystem.getFloor(currentFloor).getDownButton() != null) {
            elevatorSystem.getFloor(currentFloor).getDownButton().unpress();
        }
        // Unpress internal button
        if (panelButtons[currentFloor] != null) {
            panelButtons[currentFloor].unpress();
        }

        // Simulate delay for passenger exchange
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        // Door will be closed in the main run loop after processing stop logic
    }
}