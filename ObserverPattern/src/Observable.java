import java.util.ArrayList;
import java.util.List;

public abstract class Observable {
    private final List<DisplayInterface> observers;

    public Observable() {
        this.observers = new ArrayList<>();
    }

    public void addObserver(DisplayInterface observer) {
        observers.add(observer);
    }

    public void removeObserver(DisplayInterface observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        observers.forEach(DisplayInterface::update);
    }
}