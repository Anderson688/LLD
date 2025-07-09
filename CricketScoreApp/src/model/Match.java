package model;

import stats.MatchStats;
import strategy.MatchTypeStrategy;
import types.MatchState;
import types.TossDecision;

import java.util.*;

public class Match {
    private final String id;
    private final Team teamA;
    private final Team teamB;
    private final List<Innings> innings;
    private final MatchTypeStrategy matchType;
    private MatchState state;
    private final Map<Player, MatchStats> playerMatchStats;
    private final Team tossWinner;
    private final TossDecision tossDecision;
    private Team matchWinner;

    public Match(Team teamA, Team teamB, MatchTypeStrategy matchType, Team tossWinner, TossDecision tossDecision) {
        this.id = UUID.randomUUID().toString();
        this.teamA = teamA;
        this.teamB = teamB;
        this.matchType = matchType;
        this.state = MatchState.SCHEDULED;
        this.innings = new ArrayList<>();
        this.playerMatchStats = new HashMap<>();
        this.tossWinner = tossWinner;
        this.tossDecision = tossDecision;

        // Initialize match stats for all players
        teamA.getPlayers().forEach(p -> playerMatchStats.put(p, new MatchStats()));
        teamB.getPlayers().forEach(p -> playerMatchStats.put(p, new MatchStats()));
    }

    public Innings getCurrentInnings() {
        if (innings.isEmpty()) return null;
        return innings.getLast();
    }

    public void startNewInnings() {
        if (state != MatchState.IN_PROGRESS) {
            throw new IllegalStateException("Match must be in progress to start a new innings.");
        }
        if (innings.size() >= matchType.getNumberOfInnings()) {
            throw new IllegalStateException("Cannot start a new innings. Maximum innings reached for this match type.");
        }
        Team battingTeam = innings.isEmpty() ? (tossDecision == TossDecision.BATTING_FIRST ? tossWinner : (tossWinner == teamA ? teamB : teamA)) : getCurrentInnings().getBowlingTeam();
        Team bowlingTeam = battingTeam == teamA ? teamB : teamA;
        Innings newInnings = new Innings(battingTeam, bowlingTeam);
        this.innings.add(newInnings);
    }

    public String getId() { return id; }
    public Team getTeamA() { return teamA; }
    public Team getTeamB() { return teamB; }
    public List<Innings> getInnings() { return innings; }
    public MatchTypeStrategy getMatchType() { return matchType; }
    public MatchState getState() { return state; }
    public void setState(MatchState state) { this.state = state; }
    public Map<Player, MatchStats> getPlayerMatchStats() { return playerMatchStats; }
    public void setMatchWinner(Team matchWinner) { this.matchWinner = matchWinner; }
    public Team getMatchWinner() { return matchWinner; }
}