//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Pizza pizza = new MargheritaPizza();
        Jalapeno jalapeno = new Jalapeno(pizza);
        System.out.println(jalapeno.cost());
    }
}