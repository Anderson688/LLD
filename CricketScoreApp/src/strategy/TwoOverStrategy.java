package strategy;

public class TwoOverStrategy implements MatchTypeStrategy {
    @Override
    public int getNumberOfInnings() {
        return 2;
    }

    @Override
    public int getTotalOvers() {
        return 2;
    }
}