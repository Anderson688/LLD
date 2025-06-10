package vehicles;

public class VehicleFactory {
    public static Vehicle createVehicle(String licensePlate, VehicleType type) {
        return switch (type) {
            case FOUR_WHEELER -> new FourWheeler(licensePlate);
            case TWO_WHEELER -> new TwoWheeler(licensePlate);
        };
    }
}