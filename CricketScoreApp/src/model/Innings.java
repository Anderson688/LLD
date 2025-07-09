package model;

import java.util.ArrayList;
import java.util.List;

public class Innings {
    private final Team battingTeam;
    private final Team bowlingTeam;
    private final List<Over> overs = new ArrayList<>();
    private int score = 0;
    private int wickets = 0;
    private Player striker;
    private Player nonStriker;
    private Player currentBowler;

    public Innings(Team battingTeam, Team bowlingTeam) {
        this.battingTeam = battingTeam;
        this.bowlingTeam = bowlingTeam;
    }

    public Over getCurrentOver() {
        if (overs.isEmpty()) return null;
        return overs.getLast();
    }

    public double getOversPlayed() {
        if (overs.isEmpty()) return 0.0;
        int completedOvers = overs.size() - 1;
        long legalBallsInCurrentOver = getCurrentOver().getLegalBallCount();
        if (legalBallsInCurrentOver == 6) {
            completedOvers++;
            legalBallsInCurrentOver = 0; // Reset for next over
        }
        return completedOvers + (legalBallsInCurrentOver / 10.0);
    }

    public void changeStrike() {
        Player temp = this.striker;
        this.striker = this.nonStriker;
        this.nonStriker = temp;
    }

    public Team getBattingTeam() { return battingTeam; }
    public Team getBowlingTeam() { return bowlingTeam; }
    public List<Over> getOvers() { return overs; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public int getWickets() { return wickets; }
    public void setWickets(int wickets) { this.wickets = wickets; }
    public Player getStriker() { return striker; }
    public void setStriker(Player striker) { this.striker = striker; }
    public Player getNonStriker() { return nonStriker; }
    public void setNonStriker(Player nonStriker) { this.nonStriker = nonStriker; }
    public Player getCurrentBowler() { return currentBowler; }
    public void setCurrentBowler(Player bowler) { this.currentBowler = bowler; }
}