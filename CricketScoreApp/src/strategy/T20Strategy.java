package strategy;

public class T20Strategy implements MatchTypeStrategy {
    @Override
    public int getNumberOfInnings() {
        return 2;
    }

    @Override
    public int getTotalOvers() {
        return 20;
    }
}