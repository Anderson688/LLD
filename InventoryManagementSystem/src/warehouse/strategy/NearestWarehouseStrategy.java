package warehouse.strategy;

import model.User;
import model.Warehouse;

import java.util.List;

// Dummy implementation - in a real scenario, this would use geolocation logic.
public class NearestWarehouseStrategy implements WarehouseSelectionStrategy {
    @Override
    public Warehouse selectWarehouse(User user, List<Warehouse> warehouses) {
        System.out.println("Selecting warehouse for user " + user.getUserId() +
                " at " + user.getLocation() + " based on 'Nearest' strategy.");
        return warehouses.stream().findFirst().orElse(null);
    }
}