package warehouse.inventory;

public interface InventoryObserver {
    void update(String warehouseId, String productId, int newQuantity);
}