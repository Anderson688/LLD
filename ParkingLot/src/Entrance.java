import java.util.List;

public class Entrance {

    private final List<ParkingSpot> parkingSpots;

    public Entrance(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public Ticket assignParkingSpot(Vehicle vehicle) {
        for (ParkingSpot parkingSpot : parkingSpots) {
            if (parkingSpot.getType().equals(vehicle.getType()) && parkingSpot.isEmpty()) {
                parkingSpot.occupy(vehicle);
                return new Ticket(parkingSpot);
            }
        }
        return null;
    }
}