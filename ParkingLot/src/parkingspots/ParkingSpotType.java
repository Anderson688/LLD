package parkingspots;

import vehicles.VehicleType;

import java.util.List;

public enum ParkingSpotType {
    TWO_WHEELER(VehicleType.TWO_WHEELER),
    FOUR_WHEELER(VehicleType.FOUR_WHEELER);

    private final List<VehicleType> allowedVehicles;

    ParkingSpotType(VehicleType... types) {
        this.allowedVehicles = List.of(types);
    }

    public boolean canAccommodate(VehicleType vehicleType) {
        return allowedVehicles.contains(vehicleType);
    }
}