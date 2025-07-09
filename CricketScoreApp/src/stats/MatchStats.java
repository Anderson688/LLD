package stats;

public class MatchStats {
    private final BattingStats battingStats;
    private final BowlingStats bowlingStats;

    public MatchStats() {
        this.battingStats = new BattingStats();
        this.bowlingStats = new BowlingStats();
    }

    public BattingStats getBattingStats() {
        return battingStats;
    }

    public BowlingStats getBowlingStats() {
        return bowlingStats;
    }
}