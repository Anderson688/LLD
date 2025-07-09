import model.Match;
import model.Player;
import model.Team;
import observer.LiveScoreboardDisplay;
import repository.InMemoryMatchRepository;
import repository.MatchRepository;
import service.MatchService;
import strategy.TwoOverStrategy;
import types.BallType;
import types.DismissalType;
import types.TossDecision;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1. Setup
        MatchRepository repository = new InMemoryMatchRepository();
        MatchService matchService = new MatchService(repository);

        List<Player> indiaPlayers = IntStream.rangeClosed(1, 11).mapToObj(i -> new Player("IND_Player_" + i)).collect(Collectors.toList());
        Team india = new Team("India", indiaPlayers);

        List<Player> ausPlayers = IntStream.rangeClosed(1, 11).mapToObj(i -> new Player("AUS_Player_" + i)).collect(Collectors.toList());
        Team australia = new Team("Australia", ausPlayers);

        // 2. Create Match
        Match match = matchService.createMatch(india, australia, new TwoOverStrategy(), india, TossDecision.BATTING_FIRST);
        String matchId = match.getId();
        System.out.println("Match created with ID: " + matchId);

        // 3. Register a live scoreboard display (Observer)
        LiveScoreboardDisplay scoreboard = new LiveScoreboardDisplay();
        matchService.addObserver(scoreboard);

        // 4. Start the match
        matchService.startMatch(match);

        // 5. SIMULATE INNINGS 1
        System.out.println("\n--- Simulating Innings 1 ---");
        // Over 1
        matchService.startNewOver(match, ausPlayers.get(10));
        matchService.processBall(match, 1, BallType.NORMAL, null);
        matchService.processBall(match, 4, BallType.NORMAL, null);
        matchService.processBall(match, 0, BallType.NORMAL, null);
        matchService.processBall(match, 6, BallType.NORMAL, null);
        matchService.processBall(match, 1, BallType.NORMAL, null);
        matchService.processBall(match, 0, BallType.NORMAL, DismissalType.BOWLED);
        // Over 2
        matchService.startNewOver(match, ausPlayers.get(9));
        matchService.processBall(match, 4, BallType.NORMAL, null);
        matchService.processBall(match, 2, BallType.NORMAL, null);
        matchService.processBall(match, 1, BallType.NORMAL, null);
        matchService.processBall(match, 0, BallType.WIDE, null); // Extra
        matchService.processBall(match, 6, BallType.NORMAL, null);
        matchService.processBall(match, 1, BallType.NORMAL, null);
        matchService.processBall(match, 4, BallType.NORMAL, null);

        // 6. SIMULATE INNINGS 2
        System.out.println("\n--- Simulating Innings 2 ---");
        // Over 1
        matchService.startNewOver(match, indiaPlayers.get(10));
        matchService.processBall(match, 6, BallType.NORMAL, null);
        matchService.processBall(match, 4, BallType.NORMAL, null);
        matchService.processBall(match, 4, BallType.NORMAL, null);
        matchService.processBall(match, 1, BallType.NORMAL, null);
        matchService.processBall(match, 0, BallType.NORMAL, DismissalType.CAUGHT);
        matchService.processBall(match, 2, BallType.NORMAL, null);
        // Over 2
        matchService.startNewOver(match, indiaPlayers.get(9));
        matchService.processBall(match, 1, BallType.NO_BALL, null);
        matchService.processBall(match, 0, BallType.NORMAL, DismissalType.BOWLED);
        matchService.processBall(match, 0, BallType.NORMAL, null);
        matchService.processBall(match, 2, BallType.WIDE, null); // Extra
        matchService.processBall(match, 0, BallType.NORMAL, DismissalType.BOWLED);
        matchService.processBall(match, 1, BallType.NORMAL, null);
        matchService.processBall(match, 4, BallType.NORMAL, null);
        matchService.processBall(match, 3, BallType.NORMAL, null);

        // 6. View historic match details
        System.out.println("\n--- Viewing Historic Match Details ---");
        matchService.viewHistoricMatch(matchId);
    }
}