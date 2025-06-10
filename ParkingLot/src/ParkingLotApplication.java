import core.ParkingLot;
import floors.Floor;
import gates.Entrance;
import gates.Exit;
import parkingspots.ParkingSpotFactory;
import parkingspots.ParkingSpotType;
import parkingspots.strategy.FirstAvailableStrategy;
import payment.MinuteFeeStrategy;
import tickets.Ticket;
import vehicles.Vehicle;
import vehicles.VehicleFactory;
import vehicles.VehicleType;

public class ParkingLotApplication {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("--- Setting up Parking Lot ---");
        // Singleton Pattern for ParkingLot
        ParkingLot parkingLot = ParkingLot.getInstance();

        // Adding Floors
        Floor floor1 = new Floor(1);
        parkingLot.addFloor(floor1);

        // Adding Parking Spots with Locations
        floor1.addParkingSpot(ParkingSpotFactory.createParkingSpot(ParkingSpotType.FOUR_WHEELER));
        floor1.addParkingSpot(ParkingSpotFactory.createParkingSpot(ParkingSpotType.FOUR_WHEELER));
        floor1.addParkingSpot(ParkingSpotFactory.createParkingSpot(ParkingSpotType.TWO_WHEELER));
        floor1.addParkingSpot(ParkingSpotFactory.createParkingSpot(ParkingSpotType.TWO_WHEELER));

        // Adding Multiple, Extensible Entrance and Exit Gates with Locations
        Entrance entranceGate1 = new Entrance(1);
        Entrance entranceGate2 = new Entrance(2); // A second entrance!
        Exit exitGate1 = new Exit(1);
        parkingLot.addEntrance(entranceGate1);
        parkingLot.addEntrance(entranceGate2);
        parkingLot.addExit(exitGate1);

        System.out.println("Parking Lot Setup Complete with Multiple Gates.\n");

        // --- Simulation ---
        System.out.println("--- Vehicle Entry Simulation ---");

        // Strategy Pattern for Spot Assignment
        parkingLot.setSpotAssignmentStrategy(new FirstAvailableStrategy());
        System.out.println("Using FirstAvailableStrategy for spot assignment.");

        // Vehicle 1 enters from Gate 1
        System.out.println("\nCar 'V1' entering from Gate 1");
        Vehicle car1 = VehicleFactory.createVehicle("V1", VehicleType.FOUR_WHEELER);
        Ticket ticket1 = entranceGate1.generateTicket(car1);

        if (ticket1 != null) {
            System.out.println("=> Ticket generated for " + car1.getLicensePlate() + " at spot: " + ticket1.getParkingSpot().getId());
        } else {
            System.out.println("Sorry, no parking spot available for " + car1.getLicensePlate());
        }

        // Vehicle 2 enters from Gate 2, should get a different spot
        System.out.println("\nMotorcycle 'V2' entering from Gate 2");
        Vehicle motorcycle1 = VehicleFactory.createVehicle("V2", VehicleType.TWO_WHEELER);
        Ticket ticket2 = entranceGate2.generateTicket(motorcycle1);

        if (ticket2 != null) {
            System.out.println("=> Ticket generated for " + motorcycle1.getLicensePlate() + " at spot: " + ticket2.getParkingSpot().getId());
        } else {
            System.out.println("Sorry, no parking spot available for " + motorcycle1.getLicensePlate());
        }


        // --- Vehicle Exit and Payment Simulation ---
        System.out.println("\n--- Vehicle Exit & Payment Simulation ---");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        parkingLot.setFeeCalculationStrategy(new MinuteFeeStrategy(0.5)); // $0.5 per minute
        System.out.println("Using MinuteFeeStrategy for fee calculation.");

        double fee1 = exitGate1.calculateFee(ticket1);
        System.out.println("Parking fee for " + ticket1.getVehicle().getLicensePlate() + " is: $" + fee1);
        exitGate1.processPayment(ticket1, fee1);
    }
}