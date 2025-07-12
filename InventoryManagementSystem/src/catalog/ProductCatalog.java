package catalog;

import model.Product;

import java.util.Optional;

public class ProductCatalog {
    private static volatile ProductCatalog instance;
    private final ProductDAO productDAO = new ProductDAO();

    private ProductCatalog() {}

    public static ProductCatalog getInstance() {
        if (instance == null) {
            synchronized (ProductCatalog.class) {
                if (instance == null) {
                    instance = new ProductCatalog();
                }
            }
        }
        return instance;
    }

    public void addProduct(Product product) { productDAO.addProduct(product); }
    public Optional<Product> getProduct(String productId) { return productDAO.getProduct(productId); }
}