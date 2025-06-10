//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class StrategyPattern {
    public static void main(String[] args) {
        DriveStrategy driveStrategy = new NormalDriveStrategy();
        Vehicle vehicle = new PassengerVehicle(driveStrategy);
        vehicle.drive();

        driveStrategy = new SpecialDriveStrategy();
        vehicle = new PassengerVehicle(driveStrategy);
        vehicle.drive();

        vehicle = new SportyVehicle(driveStrategy);
        vehicle.drive();

        driveStrategy = new NormalDriveStrategy();
        vehicle = new SportyVehicle(driveStrategy);
        vehicle.drive();
    }
}