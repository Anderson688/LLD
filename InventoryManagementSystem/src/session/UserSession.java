package session;

import model.User;
import model.Warehouse;
import ordermanagement.Order;
import ordermanagement.OrderProcessor;
import warehouse.manager.WarehouseManager;
import warehouse.strategy.WarehouseSelectionStrategy;

public class UserSession {
    private final User user;
    private final Warehouse warehouse;
    private final OrderProcessor orderProcessor;

    public UserSession(User user, WarehouseSelectionStrategy strategy) {
        this.user = user;
        this.warehouse = WarehouseManager.getInstance().getWarehouseForUser(user, strategy);
        this.orderProcessor = new OrderProcessor();
        System.out.println("UserSession created for " + user.getUserId() +
                ". Assigned to Warehouse: " + warehouse.getWarehouseId());
    }

    public void viewInventory() {
        warehouse.getInventoryService().generateInventoryReport();
    }

    public boolean placeOrder(Order order) {
        return orderProcessor.processOrder(warehouse, order);
    }
}