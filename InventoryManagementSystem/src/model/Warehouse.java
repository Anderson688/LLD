package model;

import warehouse.inventory.WarehouseInventoryService;

public class Warehouse {
    private final String warehouseId;
    private final String location;
    private final WarehouseInventoryService inventoryService;

    public Warehouse(String warehouseId, String location) {
        this.warehouseId = warehouseId;
        this.location = location;
        this.inventoryService = new WarehouseInventoryService(warehouseId);
    }

    public String getWarehouseId() { return warehouseId; }
    public String getLocation() { return location; }
    public WarehouseInventoryService getInventoryService() { return inventoryService; }

    @Override
    public String toString() {
        return "Warehouse{warehouseId='" + warehouseId + "', location='" + location + "'}";
    }
}