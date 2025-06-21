package vehicle;

import core.RentalSystemManager;

import java.math.BigDecimal;
import java.util.List;

public class VehicleService {
    private final RentalSystemManager manager;

    public VehicleService(RentalSystemManager manager) {
        this.manager = manager;
    }

    public Vehicle addVehicle(VehicleType type, String make, String model, int year, String licensePlate, BigDecimal dailyRate, String locationId) {
        Vehicle vehicle = VehicleFactory.createVehicle(type, make, model, year, licensePlate, dailyRate);
        manager.addVehicle(vehicle, locationId);
        return vehicle;
    }

    public List<Vehicle> searchVehicles(IVehicleSearchStrategy strategy, String criteria) {
        return strategy.search(manager.getAllVehicles(), criteria);
    }
}