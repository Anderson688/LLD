import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class ParkingLot {
    public static void main(String[] args) throws InterruptedException {
        ParkingSpot ps1 = new ParkingSpot(VehicleType.FOUR_WHEELER);
        ParkingSpot ps2 = new ParkingSpot(VehicleType.FOUR_WHEELER);
        ParkingSpot ps3 = new ParkingSpot(VehicleType.TWO_WHEELER);
        ParkingSpot ps4 = new ParkingSpot(VehicleType.TWO_WHEELER);

        Entrance entrance = new Entrance(List.of(ps1, ps2, ps3, ps4));
        Exit exit = new Exit();

        Vehicle v1 = new Vehicle(VehicleType.FOUR_WHEELER);
        Ticket ticket1 = entrance.assignParkingSpot(v1);
        if (ticket1 == null) System.out.println("Couldn't find any vacant parking spot for vehicle 1..");

        Vehicle v2 = new Vehicle(VehicleType.FOUR_WHEELER);
        Ticket ticket2 = entrance.assignParkingSpot(v2);
        if (ticket2 == null) System.out.println("Couldn't find any vacant parking spot for vehicle 2..");

        Vehicle v3 = new Vehicle(VehicleType.FOUR_WHEELER);
        Ticket ticket3 = entrance.assignParkingSpot(v3);
        if (ticket3 == null) System.out.println("Couldn't find any vacant parking spot for vehicle 3..");

        Vehicle v4 = new Vehicle(VehicleType.TWO_WHEELER);
        Ticket ticket4 = entrance.assignParkingSpot(v4);
        if (ticket4 == null) System.out.println("Couldn't find any vacant parking spot for vehicle 4..");

        else {
            Thread.sleep(5000);
            if (ticket1 != null) exit.releaseVehicle(ticket1);

            Thread.sleep(1000);
            if (ticket2 != null) exit.releaseVehicle(ticket2);

            Thread.sleep(2000);
            if (ticket3 != null) exit.releaseVehicle(ticket3);

            Thread.sleep(5000);
            if (ticket4 != null) exit.releaseVehicle(ticket4);
        }
    }
}