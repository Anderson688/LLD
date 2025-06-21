package vehicle;

import java.math.BigDecimal;

public class SUV extends Vehicle {
    public SUV(String make, String model, int year, String licensePlate, BigDecimal dailyRate) {
        super(make, model, year, licensePlate, VehicleType.SUV, dailyRate);
    }

    @Override
    public void displayDetails() {
        System.out.println("SUV: " + getMake() + " " + getModel() + " (" + getYear() + "), Rate: " + getDailyRate());
    }
}