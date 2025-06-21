package vehicle;

import java.math.BigDecimal;

public class Car extends Vehicle {
    public Car(String make, String model, int year, String licensePlate, BigDecimal dailyRate) {
        super(make, model, year, licensePlate, VehicleType.SEDAN, dailyRate);
    }

    @Override
    public void displayDetails() {
        System.out.println("Car: " + getMake() + " " + getModel() + " (" + getYear() + "), Rate: " + getDailyRate());
    }
}