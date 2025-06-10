package parkingspots;

public class ParkingSpotFactory {
    public static ParkingSpot createParkingSpot(ParkingSpotType type) {
        return new ParkingSpot(type);
    }
}