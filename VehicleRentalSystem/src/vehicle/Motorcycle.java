package vehicle;

import java.math.BigDecimal;

public class Motorcycle extends Vehicle {
    public Motorcycle(String make, String model, int year, String licensePlate, BigDecimal dailyRate) {
        super(make, model, year, licensePlate, VehicleType.MOTORCYCLE, dailyRate);
    }

    @Override
    public void displayDetails() {
        System.out.println("Motorcycle: " + getMake() + " " + getModel() + " (" + getYear() + "), Rate: " + getDailyRate());
    }
}