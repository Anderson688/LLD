package stats;

public class BowlingStats {
    private int ballsBowled = 0;
    private int runsConceded = 0;
    private int wicketsTaken = 0;

    public void addWicket() {
        this.wicketsTaken++;
    }

    public void addRunsConceded(int runs) {
        this.runsConceded += runs;
    }

    public void incrementBallsBowled() {
        this.ballsBowled++;
    }

    public double getOversBowled() {
        return (ballsBowled / 6) + (ballsBowled % 6) / 10.0;
    }

    public int getBallsBowled() { return ballsBowled; }
    public int getRunsConceded() { return runsConceded; }
    public int getWicketsTaken() { return wicketsTaken; }
}