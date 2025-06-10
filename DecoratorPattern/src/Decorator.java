public abstract class Decorator extends Pizza {

    final Pizza pizza;

    private double cost;

    public Decorator(Pizza decorator) {
        this.pizza = decorator;
    }
}