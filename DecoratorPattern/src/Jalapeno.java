public class Jalapeno extends Decorator {

    public Jalapeno(Pizza pizza) {
        super(pizza);
    }

    public double cost() {
        return pizza.cost() + 0.5;
    }
}
