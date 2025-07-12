package model;

public class Product {
    private final String productId;
    private final String name;
    private final String description;
    private double price;

    private Product(Builder builder) {
        this.productId = builder.productId;
        this.name = builder.name;
        this.description = builder.description;
        this.price = builder.price;
    }

    public String getProductId() { return productId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{productId='" + productId + "', name='" + name + "', price=" + price + '}';
    }

    public static class Builder {
        private final String productId;
        private final String name;
        private String description;
        private double price;

        public Builder(String productId, String name) {
            this.productId = productId;
            this.name = name;
        }

        public Builder description(String description) { this.description = description; return this; }
        public Builder price(double price) { this.price = price; return this; }
        public Product build() { return new Product(this); }
    }
}