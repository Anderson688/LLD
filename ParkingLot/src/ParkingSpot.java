public class ParkingSpot {

    private final VehicleType type;

    private Vehicle occupiedVehicle;

    public ParkingSpot(VehicleType type) {
        this.type = type;
    }

    public void occupy(Vehicle vehicle) {
        if (occupiedVehicle != null)
            throw new RuntimeException("Already Occupied!!");
        occupiedVehicle = vehicle;
    }

    public void release() {
        if (occupiedVehicle == null)
            throw new RuntimeException("Already Released!!");
        occupiedVehicle = null;
    }

    public VehicleType getType() {
        return this.type;
    }

    public boolean isEmpty() {
        return this.occupiedVehicle == null;
    }

    public int getCostPerSecond() {
        return this.type.getCostPerSecond();
    }
}