package warehouse.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseInventoryService {
    private final String warehouseId;
    private final Map<String, Integer> productStock = new HashMap<>();
    private final List<InventoryObserver> observers = new ArrayList<>();

    public WarehouseInventoryService(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void addObserver(InventoryObserver observer) { observers.add(observer); }

    private void notifyObservers(String productId, int newQuantity) {
        for (InventoryObserver observer : observers) {
            observer.update(warehouseId, productId, newQuantity);
        }
    }

    public void addStock(String productId, int quantity) {
        int newQuantity = productStock.getOrDefault(productId, 0) + quantity;
        productStock.put(productId, newQuantity);
    }

    public void removeStock(String productId, int quantity) {
        int currentStock = getStock(productId);
        int newQuantity = currentStock - quantity;
        productStock.put(productId, newQuantity);
        notifyObservers(productId, newQuantity);
    }

    public int getStock(String productId) {
        return productStock.getOrDefault(productId, 0);
    }

    public void generateInventoryReport() {
        System.out.println("--- Inventory Report for Warehouse: " + warehouseId + " ---");
        productStock.forEach((productId, quantity) ->
                System.out.println("Product: " + productId + ", Quantity: " + quantity));
        System.out.println("------------------------------------------");
    }
}