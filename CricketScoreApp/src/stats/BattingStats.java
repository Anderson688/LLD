package stats;

import model.Dismissal;
import model.Player;

public class BattingStats {
    private int runsScored = 0;
    private int ballsFaced = 0;
    private int fours = 0;
    private int sixes = 0;
    private Dismissal dismissal;

    public void addRuns(int runs) {
        if (runs == 4) fours++;
        if (runs == 6) sixes++;
        this.runsScored += runs;
    }

    public void incrementBallsFaced() {
        this.ballsFaced++;
    }

    public int getRunsScored() { return runsScored; }
    public int getBallsFaced() { return ballsFaced; }
    public int getFours() { return fours; }
    public int getSixes() { return sixes; }
    public Dismissal getDismissal() { return dismissal; }
    public void setDismissal(Dismissal dismissal) {
        this.dismissal = dismissal;
    }
}