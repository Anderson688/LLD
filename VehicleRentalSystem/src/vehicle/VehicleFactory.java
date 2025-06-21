package vehicle;

import java.math.BigDecimal;

public class VehicleFactory {
    public static Vehicle createVehicle(VehicleType type, String make, String model, int year, String licensePlate, BigDecimal dailyRate) {
        return switch (type) {
            case SEDAN -> new Car(make, model, year, licensePlate, dailyRate);
            case SUV -> new SUV(make, model, year, licensePlate, dailyRate);
            case MOTORCYCLE -> new Motorcycle(make, model, year, licensePlate, dailyRate);
        };
    }
}