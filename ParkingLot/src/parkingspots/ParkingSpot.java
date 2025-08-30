package parkingspots;

import vehicles.Vehicle;

import java.util.concurrent.atomic.AtomicInteger;

public class ParkingSpot {

    private static final AtomicInteger idCounter = new AtomicInteger();

    private final int id;

    private Vehicle assignedVehicle;

    private final ParkingSpotType type;

    public ParkingSpot(ParkingSpotType parkingSpotType) {
        this.id = idCounter.incrementAndGet();
        this.type = parkingSpotType;
    }

    public boolean canFitVehicle(Vehicle vehicle) {
        return assignedVehicle == null && this.type.canAccommodate(vehicle.getType());
    }

    public void assignVehicle(Vehicle vehicle) {
        this.assignedVehicle = vehicle;
    }

    public void removeVehicle() {
        this.assignedVehicle = null;
    }

    public int getId() { return id; }
    public ParkingSpotType getType() { return type; }
}