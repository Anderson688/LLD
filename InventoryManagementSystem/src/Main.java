import catalog.ProductCatalog;
import model.Product;
import model.User;
import model.Warehouse;
import ordermanagement.Order;
import session.UserSession;
import warehouse.inventory.LowStockNotifier;
import warehouse.manager.WarehouseManager;
import warehouse.strategy.NearestWarehouseStrategy;

import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // --- System Setup ---
        // 1. Setup Product Catalog
        ProductCatalog catalog = ProductCatalog.getInstance();
        catalog.addProduct(new Product.Builder("P001", "Laptop").price(1200).build());
        catalog.addProduct(new Product.Builder("P002", "Mouse").price(25).build());

        // 2. Setup Warehouses
        WarehouseManager warehouseManager = WarehouseManager.getInstance();
        Warehouse wh1 = new Warehouse("WH-NORTH", "Delhi");
        Warehouse wh2 = new Warehouse("WH-SOUTH", "Bangalore");
        warehouseManager.addWarehouse(wh1);
        warehouseManager.addWarehouse(wh2);

        // 3. Setup Observers
        LowStockNotifier notifier = new LowStockNotifier();
        wh1.getInventoryService().addObserver(notifier);
        wh2.getInventoryService().addObserver(notifier);

        // 4. Add stock to warehouses
        wh1.getInventoryService().addStock("P001", 10); // 10 Laptops in Delhi
        wh1.getInventoryService().addStock("P002", 50); // 50 Mice in Delhi
        wh2.getInventoryService().addStock("P001", 8);  // 8 Laptops in Bangalore
        wh2.getInventoryService().addStock("P002", 60); // 60 Mice in Bangalore

        System.out.println("--- Initial System State ---");
        wh1.getInventoryService().generateInventoryReport();
        wh2.getInventoryService().generateInventoryReport();

        System.out.println("\n--- User Interaction Simulation ---");

        // --- User 1 (in Delhi) logs in ---
        User user1 = new User("U101", "Delhi-Zip");
        UserSession session1 = new UserSession(user1, new NearestWarehouseStrategy());

        // User 1 views inventory and places an order
        session1.viewInventory();
        Map<String, Integer> orderItems1 = new HashMap<>();
        orderItems1.put("P001", 7); // Order 7 laptops
        Order order1 = new Order("O1", orderItems1);
        session1.placeOrder(order1); // Should trigger low stock alert for P001 in the User's warehouse

        System.out.println("\n--- State after User 1's Order ---");
        session1.viewInventory();

        // --- User 2 (in Bangalore) logs in ---
        User user2 = new User("U202", "Bangalore-Zip");
        // We can use a different strategy if needed, but we'll reuse the same one.
        UserSession session2 = new UserSession(user2, new NearestWarehouseStrategy());

        // User 2 places an order
        Map<String, Integer> orderItems2 = new HashMap<>();
        orderItems2.put("P001", 2);
        orderItems2.put("P002", 10);
        Order order2 = new Order("O2", orderItems2);
        session2.placeOrder(order2);

        System.out.println("\n--- Final System State ---");
        wh1.getInventoryService().generateInventoryReport();
        wh2.getInventoryService().generateInventoryReport();
    }
}