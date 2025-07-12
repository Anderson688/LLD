package warehouse.manager;

import model.Warehouse;

import java.util.*;

public class WarehouseDAO {
    private final Map<String, Warehouse> warehouseMap = new HashMap<>();

    public void addWarehouse(Warehouse warehouse) { warehouseMap.put(warehouse.getWarehouseId(), warehouse); }
    public Optional<Warehouse> getWarehouse(String warehouseId) { return Optional.ofNullable(warehouseMap.get(warehouseId)); }
    public List<Warehouse> getAllWarehouses() { return new ArrayList<>(warehouseMap.values()); }
}