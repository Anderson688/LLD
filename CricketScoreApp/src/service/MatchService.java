package service;

import model.*;
import observer.Display;
import observer.HistoricScoreboardDisplay;
import observer.LiveScoreboardDisplay;
import observer.ScoreUpdater;
import repository.MatchRepository;
import stats.MatchStats;
import strategy.MatchTypeStrategy;
import types.BallType;
import types.DismissalType;
import types.MatchState;
import types.TossDecision;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MatchService implements ScoreUpdater {
    private final MatchRepository matchRepository;
    private final List<Display> observers = new ArrayList<>();

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public Match createMatch(Team teamA, Team teamB, MatchTypeStrategy strategy, Team tossWinner, TossDecision tossDecision) {
        Match match = new Match(teamA, teamB, strategy, tossWinner, tossDecision);
        matchRepository.save(match);
        return match;
    }

    public void startMatch(Match match) {
        if (match.getState() == MatchState.SCHEDULED) {
            match.setState(MatchState.IN_PROGRESS);
        } else {
            System.out.println("Match is already in progress or finished.");
            return;
        }
        match.startNewInnings();
        Innings currentInnings = match.getCurrentInnings();

        int nextBatsmanIndex = 0;
        currentInnings.setStriker(currentInnings.getBattingTeam().getPlayers().get(nextBatsmanIndex++));
        currentInnings.setNonStriker(currentInnings.getBattingTeam().getPlayers().get(nextBatsmanIndex));
        System.out.println("\n--- Starting Innings " + match.getInnings().size() + " for " + currentInnings.getBattingTeam().getName() + " ---");
    }

    public void startNewOver(Match match, Player bowler) {
        if (match.getState() != MatchState.IN_PROGRESS) {
            System.out.println("Match is not in progress.");
            return;
        }
        Innings currentInnings = match.getCurrentInnings();
        if (currentInnings == null) {
            System.out.println("No innings to start a new over.");
            return;
        }
        if (currentInnings.getOversPlayed() >= match.getMatchType().getTotalOvers()) {
            System.out.println("Cannot start a new over. Innings has already completed.");
            return;
        }
        if (currentInnings.getCurrentOver() != null && currentInnings.getCurrentOver().getLegalBallCount() < 6) {
            System.out.println("Cannot start a new over. Current over is not complete.");
            return;
        }
        if (currentInnings.getWickets() == 10) {
            System.out.println("Cannot start a new over. All wickets are down.");
            return;
        }
        if (bowler == null || !currentInnings.getBowlingTeam().getPlayers().contains(bowler)) {
            throw new IllegalArgumentException("Invalid bowler specified.");
        }
        if (currentInnings.getCurrentBowler() == bowler) {
            System.out.println("Same bowler cannot bowl consecutive overs.");
            return;
        }

        int nextOverNumber = currentInnings.getOvers().size() + 1;
        currentInnings.getOvers().add(new Over(nextOverNumber));
        currentInnings.changeStrike();
        currentInnings.setCurrentBowler(bowler);
        System.out.println("\n--- Starting Over " + nextOverNumber + " for " + currentInnings.getBattingTeam().getName() + " ---");
        notifyObservers();
    }

    public void processBall(Match match, int runs, BallType ballType, DismissalType dismissalType) {
        if (match.getState() != MatchState.IN_PROGRESS) {
            System.out.println("Match is not in progress.");
            return;
        }
        if (match.getCurrentInnings() == null) {
            System.out.println("No innings in progress.");
            return;
        }
        if (runs < 0 || runs > 6) {
            throw new IllegalArgumentException("Runs must be between 0 and 6.");
        }
        if (ballType == null) {
            throw new IllegalArgumentException("Ball type cannot be null.");
        }
        if (match.getCurrentInnings().getCurrentOver().getLegalBallCount() == 6) {
            System.out.println("Cannot process ball. Current over is complete.");
            return;
        }
        if (match.getCurrentInnings().getWickets() == 10) {
            System.out.println("Cannot process ball. All wickets are down.");
            return;
        }

        Innings currentInnings = match.getCurrentInnings();
        Player striker = currentInnings.getStriker();
        Player bowler = currentInnings.getCurrentBowler();
        Map<Player, MatchStats> playerStats = match.getPlayerMatchStats();

        Dismissal dismissal = (dismissalType != null) ? new Dismissal(striker, bowler, dismissalType) : null;
        Ball ball = new Ball(runs, ballType, dismissal);

        currentInnings.getOvers().getLast().addBall(ball);

        // Update scores
        currentInnings.setScore(currentInnings.getScore() + runs);
        if (ballType == BallType.WIDE || ballType == BallType.NO_BALL) {
            currentInnings.setScore(currentInnings.getScore() + 1); // Extra run
        }

        // Update stats
        playerStats.get(striker).getBattingStats().addRuns(runs);
        playerStats.get(bowler).getBowlingStats().addRunsConceded(runs);

        if (ballType != BallType.WIDE) {
            playerStats.get(striker).getBattingStats().incrementBallsFaced();
        }
        if (ballType == BallType.NORMAL) {
            playerStats.get(bowler).getBowlingStats().incrementBallsBowled();
        } else {
            playerStats.get(bowler).getBowlingStats().addRunsConceded(1);
        }

        if (dismissal != null) {
            currentInnings.setWickets(currentInnings.getWickets() + 1);
            playerStats.get(bowler).getBowlingStats().addWicket();
            playerStats.get(striker).getBattingStats().setDismissal(dismissal);

            if (currentInnings.getWickets() < 10) {
                currentInnings.setStriker(currentInnings.getBattingTeam().getPlayers().get(currentInnings.getWickets()+1));
            }
        }

        if (runs%2 == 1) {
            currentInnings.changeStrike(); // Change strike if runs are odd
        }

        matchRepository.save(match);
        notifyObservers();

        if (currentInnings.getWickets() == 10 || (currentInnings.getCurrentOver().getOverNumber() == match.getMatchType().getTotalOvers() && currentInnings.getCurrentOver().getLegalBallCount() == 6)) {
            handleInningsCompletion(match);
        } else if (match.getInnings().size() == match.getMatchType().getNumberOfInnings()) {
            int battingTeamScore = 0;
            int bowlingTeamScore = 0;
            for (Innings innings : match.getInnings()) {
                if (innings.getBattingTeam().equals(match.getCurrentInnings().getBattingTeam())) {
                    battingTeamScore += innings.getScore();
                } else {
                    bowlingTeamScore += innings.getScore();
                }
            }
            if (battingTeamScore > bowlingTeamScore)
                handleInningsCompletion(match);
        }
    }

    public void handleInningsCompletion(Match match) {
        System.out.println("\n--- End of Innings " + match.getInnings().size() + " ---");
        // If there are more innings to be played as per strategy
        if (match.getInnings().size() < match.getMatchType().getNumberOfInnings()) {
            match.startNewInnings();
            Innings currentInnings = match.getCurrentInnings();
            currentInnings.setStriker(currentInnings.getBattingTeam().getPlayers().get(0));
            currentInnings.setNonStriker(currentInnings.getBattingTeam().getPlayers().get(1));
        } else {
            match.setState(MatchState.FINISHED);
            matchRepository.save(match);
            int teamAScore = 0;
            int teamBScore = 0;
            for (Innings innings : match.getInnings()) {
                if (innings.getBattingTeam().equals(match.getTeamA())) {
                    teamAScore += innings.getScore();
                } else {
                    teamBScore += innings.getScore();
                }
            }
            Team winner = teamAScore > teamBScore ? match.getTeamA() : match.getTeamB();
            System.out.println("\n--- Match Finished ---");
            if (teamAScore == teamBScore) {
                System.out.println("The match is a draw!");
            } else {
                System.out.println(winner.getName() + " wins the match by " + Math.abs(teamAScore - teamBScore) + " runs!");
            }
        }
    }

    public void viewHistoricMatch(String matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(() -> new IllegalArgumentException("Match not found"));
        // Use the same display logic for historic matches
        new HistoricScoreboardDisplay().display(match);
    }

    @Override
    public void addObserver(Display display) { observers.add(display); }
    @Override
    public void removeObserver(Display display) { observers.remove(display); }
    @Override
    public void notifyObservers() {
        // Find any in-progress match to notify observers for
        matchRepository.findAllInProgress().ifPresent(matches -> {
            matches.forEach(match -> observers.forEach(obs -> obs.display(match)));
        });
    }
}