package parkingspots.strategy;

import floors.Floor;
import parkingspots.ParkingSpot;
import vehicles.Vehicle;

import java.util.List;
import java.util.Optional;

public class FirstAvailableStrategy implements SpotAssignmentStrategy {

    @Override
    public Optional<ParkingSpot> findSpot(List<Floor> floors, Vehicle vehicle) {
        for (Floor floor : floors) {
            for (ParkingSpot spot : floor.getParkingSpots()) {
                if (spot.canFitVehicle(vehicle)) {
                    return Optional.of(spot);
                }
            }
        }
        return Optional.empty();
    }
}