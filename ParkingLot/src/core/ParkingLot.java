package core;

import floors.Floor;
import gates.Entrance;
import gates.Exit;
import parkingspots.ParkingSpot;
import parkingspots.strategy.FirstAvailableStrategy;
import parkingspots.strategy.SpotAssignmentStrategy;
import payment.FeeCalculationStrategy;
import payment.HourlyFeeStrategy;
import tickets.Ticket;
import vehicles.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ParkingLot {
    private static final ParkingLot INSTANCE = new ParkingLot();
    private final List<Floor> floors;
    private final List<Entrance> entrances;
    private final List<Exit> exits;
    private final Map<Integer, Ticket> activeTickets;
    private SpotAssignmentStrategy spotAssignmentStrategy;
    private FeeCalculationStrategy feeCalculationStrategy;

    private ParkingLot() {
        this.floors = new CopyOnWriteArrayList<>();
        this.entrances = new CopyOnWriteArrayList<>();
        this.exits = new CopyOnWriteArrayList<>();
        this.activeTickets = new ConcurrentHashMap<>();
        this.spotAssignmentStrategy = new FirstAvailableStrategy();
        this.feeCalculationStrategy = new HourlyFeeStrategy();
    }

    public static ParkingLot getInstance() {
        return INSTANCE;
    }

    public void addFloor(Floor floor) {
        floors.add(floor);
    }

    public void addEntrance(Entrance entrance) {
        entrances.add(entrance);
    }

    public void addExit(Exit exit) {
        exits.add(exit);
    }

    public void setSpotAssignmentStrategy(SpotAssignmentStrategy spotAssignmentStrategy) {
        this.spotAssignmentStrategy = spotAssignmentStrategy;
    }

    public void setFeeCalculationStrategy(FeeCalculationStrategy feeCalculationStrategy) {
        this.feeCalculationStrategy = feeCalculationStrategy;
    }

    public Optional<ParkingSpot> findAndAssignSpot(Vehicle vehicle, Entrance entranceGate) {
        Optional<ParkingSpot> spot = spotAssignmentStrategy.findSpot(this.floors, vehicle);
        spot.ifPresent(s -> s.assignVehicle(vehicle));
        return spot;
    }

    public void releaseSpot(ParkingSpot spot) {
        spot.removeVehicle();
        System.out.println("Spot " + spot.getId() + " has been released.");
    }

    public double calculateFee(Ticket ticket) {
        return feeCalculationStrategy.calculateFee(ticket);
    }

    public void addActiveTicket(Ticket ticket) {
        activeTickets.put(ticket.getId(), ticket);
    }

    public void closeTicket(Ticket ticket) {
        activeTickets.remove(ticket.getId());
        releaseSpot(ticket.getParkingSpot());
    }
}