package ordermanagement;

import java.util.Map;

public class Order {
    private final String orderId;
    private final Map<String, Integer> productQuantities;

    public Order(String orderId, Map<String, Integer> productQuantities) {
        this.orderId = orderId;
        this.productQuantities = productQuantities;
    }

    public String getOrderId() { return orderId; }
    public Map<String, Integer> getProductQuantities() { return productQuantities; }
}