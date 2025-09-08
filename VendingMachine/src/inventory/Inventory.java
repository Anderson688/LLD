package inventory;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, Item> itemMap;

    public Inventory() {
        this.itemMap = new HashMap<>();
    }

    public void addItem(Item item) {
        itemMap.put(item.getItemCode(), item);
    }

    public Item getItem(String itemCode) {
        return itemMap.get(itemCode);
    }

    public void updateStock(String itemCode, int quantity) {
        if (itemMap.containsKey(itemCode)) {
            itemMap.get(itemCode).setQuantity(quantity);
        }
    }

    public boolean isItemAvailable(String itemCode) {
        return itemMap.containsKey(itemCode) && itemMap.get(itemCode).getQuantity() > 0;
    }
}