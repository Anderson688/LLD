package stats;

// Can be implemented, not included in the original code
public class CareerStats {
    private final BattingStats battingStats;
    private final BowlingStats bowlingStats;

    public CareerStats() {
        this.battingStats = new BattingStats();
        this.bowlingStats = new BowlingStats();
    }

    public void updateStats(MatchStats matchStats) {
        this.battingStats.addRuns(matchStats.getBattingStats().getRunsScored());
        this.battingStats.incrementBallsFaced(); // Simplified for example

        this.bowlingStats.addRunsConceded(matchStats.getBowlingStats().getRunsConceded());
        this.bowlingStats.addWicket(); // Simplified for example
    }

    public BattingStats getBattingStats() {
        return battingStats;
    }

    public BowlingStats getBowlingStats() {
        return bowlingStats;
    }
}