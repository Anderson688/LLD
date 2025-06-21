package vehicle;

import core.RentalSystemManager;
import store.RentalLocation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SearchByLocationStrategy implements IVehicleSearchStrategy {
    private final RentalSystemManager manager = RentalSystemManager.getInstance();

    @Override
    public List<Vehicle> search(List<Vehicle> allVehicles, String locationName) {
        Optional<RentalLocation> location = manager.getAllLocations().stream()
                .filter(loc -> loc.getName().equalsIgnoreCase(locationName))
                .findFirst();

        if (location.isPresent()) {
            String locationId = location.get().getLocationId();
            return allVehicles.stream()
                    .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                    .filter(v -> v.getCurrentLocationId().equals(locationId))
                    .collect(Collectors.toList());
        }
        System.out.println("No location found matching: " + locationName);
        return List.of(); // Return empty list if location not found
    }
}