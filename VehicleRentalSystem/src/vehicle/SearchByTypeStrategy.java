package vehicle;

import java.util.List;
import java.util.stream.Collectors;

public class SearchByTypeStrategy implements IVehicleSearchStrategy {
    @Override
    public List<Vehicle> search(List<Vehicle> allVehicles, String typeName) {
        try {
            VehicleType type = VehicleType.valueOf(typeName.toUpperCase());
            return allVehicles.stream()
                    .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                    .filter(v -> v.getType() == type)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid vehicle type: " + typeName);
            return List.of();
        }
    }
}