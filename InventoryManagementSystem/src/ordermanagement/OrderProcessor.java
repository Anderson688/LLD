package ordermanagement;

import model.Warehouse;
import warehouse.inventory.WarehouseInventoryService;

public class OrderProcessor {
    public boolean processOrder(Warehouse warehouse, Order order) {
        WarehouseInventoryService inventoryService = warehouse.getInventoryService();
        System.out.println("Processing order " + order.getOrderId() + " for warehouse " + warehouse.getWarehouseId());

        // Step 1: Check stock for all items in the order
        for (var entry : order.getProductQuantities().entrySet()) {
            if (inventoryService.getStock(entry.getKey()) < entry.getValue()) {
                System.out.println("Error: Not enough stock for product " + entry.getKey() +
                        " in warehouse " + warehouse.getWarehouseId());
                return false;
            }
        }

        // Step 2: Fulfill the order by removing stock
        for (var entry : order.getProductQuantities().entrySet()) {
            inventoryService.removeStock(entry.getKey(), entry.getValue());
        }

        System.out.println("Order " + order.getOrderId() + " processed successfully.");
        return true;
    }
}