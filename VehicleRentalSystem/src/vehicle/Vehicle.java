package vehicle;

import java.math.BigDecimal;
import java.util.UUID;

public abstract class Vehicle {
    protected final String vehicleId;
    protected final String make;
    protected final String model;
    protected final int year;
    protected final String licensePlate;
    protected final VehicleType type;
    protected VehicleStatus status;
    protected String currentLocationId;
    protected BigDecimal dailyRate; // Base daily rate

    public Vehicle(String make, String model, int year, String licensePlate, VehicleType type,
                   BigDecimal dailyRate) {
        this.vehicleId = UUID.randomUUID().toString();
        this.make = make;
        this.model = model;
        this.year = year;
        this.licensePlate = licensePlate;
        this.type = type;
        this.status = VehicleStatus.AVAILABLE; // Default status
        this.dailyRate = dailyRate;
    }

    // Getters
    public String getVehicleId() { return vehicleId; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType() { return type; }
    public VehicleStatus getStatus() { return status; }
    public String getCurrentLocationId() { return currentLocationId; }
    public BigDecimal getDailyRate() { return dailyRate; }

    // Setters (for updatable fields)
    public void setStatus(VehicleStatus status) { this.status = status; }
    public void setCurrentLocationId(String currentLocationId) { this.currentLocationId = currentLocationId; }
    public void setDailyRate(BigDecimal dailyRate) { this.dailyRate = dailyRate; }

    public abstract void displayDetails(); // Abstract method for polymorphism

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + vehicleId + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", type=" + type +
                ", status=" + status +
                '}';
    }
}