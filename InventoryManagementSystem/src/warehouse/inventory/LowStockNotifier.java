package warehouse.inventory;

public class LowStockNotifier implements InventoryObserver {
    private static final int LOW_STOCK_THRESHOLD = 5;

    @Override
    public void update(String warehouseId, String productId, int newQuantity) {
        if (newQuantity < LOW_STOCK_THRESHOLD) {
            System.out.println("ALERT: [Warehouse " + warehouseId + "] Low stock for product " +
                    productId + ". Current quantity: " + newQuantity);
        }
    }
}