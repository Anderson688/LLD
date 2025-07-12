package warehouse.strategy;

import model.User;
import model.Warehouse;

import java.util.List;

public interface WarehouseSelectionStrategy {
    Warehouse selectWarehouse(User user, List<Warehouse> warehouses);
}