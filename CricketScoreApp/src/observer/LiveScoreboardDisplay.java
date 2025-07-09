package observer;

import model.Dismissal;
import model.Innings;
import model.Match;
import model.Player;
import stats.BattingStats;
import stats.BowlingStats;
import stats.MatchStats;
import types.MatchState;

public class LiveScoreboardDisplay implements Display {
    @Override
    public void display(Match match) {
        System.out.println("\n\n################### 🏏 LIVE SCOREBOARD 🏏 ###################");
        System.out.println("Match ID: " + match.getId() + " | Status: " + match.getState());

        Innings innings = match.getCurrentInnings();
        System.out.println("\n--- Innings " + (match.getInnings().size()) + ": " + innings.getBattingTeam().getName() + " ---");
        System.out.println("Score: " + innings.getScore() + "/" + innings.getWickets());
        System.out.println("Overs: " + String.format("%.1f", innings.getOversPlayed()));

        if (match.getState() == MatchState.IN_PROGRESS) {
            System.out.println("Striker: " + innings.getStriker().getName() + "*");
            System.out.println("Non-Striker: " + innings.getNonStriker().getName());
            System.out.println("Bowler: " + (innings.getCurrentBowler() == null ? "" : innings.getCurrentBowler().getName()));
        }

        System.out.println("\n--- Batting Stats ---");
        System.out.printf("%-20s %-25s %-10s %-10s\n", "Player", "Status", "Runs", "Balls");
        for (Player p : innings.getBattingTeam().getPlayers()) {
            MatchStats stats = match.getPlayerMatchStats().get(p);
            // Display player if they have faced a ball, are currently batting, or are out.
            if (stats.getBattingStats().getBallsFaced() > 0 || p == innings.getStriker() || p == innings.getNonStriker() || stats.getBattingStats().getDismissal() != null) {
                BattingStats battingStats = stats.getBattingStats();
                String status = "Not Out";
                if(battingStats.getDismissal() != null) {
                    Dismissal d = battingStats.getDismissal();
                    status = d.getType().toString().toLowerCase() + " b. " + d.getBowler().getName();
                }
                System.out.printf("%-20s %-25s %-10d %-10d\n", p.getName(), status, battingStats.getRunsScored(), battingStats.getBallsFaced());
            }
        }

        System.out.println("\n--- Bowling Stats ---");
        System.out.printf("%-20s %-25s %-10s %-10s\n", "Player", "Over", "Runs", "Wickets");
        for (Player p : innings.getBowlingTeam().getPlayers()) {
            MatchStats stats = match.getPlayerMatchStats().get(p);
            // Display player if they have faced a ball, are currently batting, or are out.
            if (stats.getBowlingStats().getBallsBowled() > 0 || stats.getBowlingStats().getRunsConceded() > 0) {
                BowlingStats bowlingStats = stats.getBowlingStats();
                System.out.printf("%-20s %-25.1f %-10d %-10d\n", p.getName(), bowlingStats.getOversBowled(), bowlingStats.getRunsConceded(), bowlingStats.getWicketsTaken());
            }
        }
        System.out.println("############################################################\n");
    }
}