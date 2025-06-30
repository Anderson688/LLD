package inventory;

public class Item {
    private final String itemCode;
    private final String name;
    private final double price;
    private int quantity;

    public Item(String itemCode, String name, double price, int quantity) {
        this.itemCode = itemCode;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getItemCode() { return itemCode; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}