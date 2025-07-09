package observer;

public interface ScoreUpdater {
    void addObserver(Display display);
    void removeObserver(Display display);
    void notifyObservers();
}