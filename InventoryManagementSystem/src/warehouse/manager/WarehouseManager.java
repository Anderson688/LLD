package warehouse.manager;

import model.User;
import model.Warehouse;
import warehouse.strategy.WarehouseSelectionStrategy;

import java.util.List;

public class WarehouseManager {
    private static volatile WarehouseManager instance;
    private final WarehouseDAO warehouseDAO = new WarehouseDAO();

    private WarehouseManager() {}

    public static WarehouseManager getInstance() {
        if (instance == null) {
            synchronized (WarehouseManager.class) {
                if (instance == null) {
                    instance = new WarehouseManager();
                }
            }
        }
        return instance;
    }

    public void addWarehouse(Warehouse warehouse) { warehouseDAO.addWarehouse(warehouse); }
    public List<Warehouse> listWarehouses() { return warehouseDAO.getAllWarehouses(); }

    public Warehouse getWarehouseForUser(User user, WarehouseSelectionStrategy strategy) {
        return strategy.selectWarehouse(user, warehouseDAO.getAllWarehouses());
    }
}