package gates;

import core.ParkingLot;
import parkingspots.ParkingSpot;
import tickets.Ticket;
import vehicles.Vehicle;

import java.util.Optional;

public class Entrance {

    private final int id;

    public Entrance(int id) {
        this.id = id;
    }

    public Ticket generateTicket(Vehicle vehicle) {
        ParkingLot parkingLot = ParkingLot.getInstance();
        // Pass 'this' gate to find a spot relative to it.
        Optional<ParkingSpot> spotOptional = parkingLot.findAndAssignSpot(vehicle, this);

        if (spotOptional.isPresent()) {
            ParkingSpot assignedSpot = spotOptional.get();
            Ticket ticket = new Ticket(assignedSpot, vehicle);
            parkingLot.addActiveTicket(ticket);
            return ticket;
        }

        return null;
    }
}