package parkingspots.strategy;

import floors.Floor;
import parkingspots.ParkingSpot;
import vehicles.Vehicle;

import java.util.List;
import java.util.Optional;

public interface SpotAssignmentStrategy {
    Optional<ParkingSpot> findSpot(List<Floor> parkingSpots, Vehicle vehicle);
}