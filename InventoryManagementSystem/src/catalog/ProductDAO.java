package catalog;

import model.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ProductDAO {
    private final Map<String, Product> productMap = new HashMap<>();

    public void addProduct(Product product) { productMap.put(product.getProductId(), product); }
    public Optional<Product> getProduct(String productId) { return Optional.ofNullable(productMap.get(productId)); }
    public Map<String, Product> getAllProducts() { return new HashMap<>(productMap); }
}