package repository;

import model.Match;
import types.MatchState;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryMatchRepository implements MatchRepository {
    private final Map<String, Match> matchStore = new ConcurrentHashMap<>();

    @Override
    public void save(Match match) {
        matchStore.put(match.getId(), match);
    }

    @Override
    public Optional<Match> findById(String matchId) {
        return Optional.ofNullable(matchStore.get(matchId));
    }

    @Override
    public Optional<List<Match>> findAllInProgress() {
        List<Match> inProgressMatches = matchStore.values().stream()
                .filter(match -> match.getState() == MatchState.IN_PROGRESS)
                .toList();
        return inProgressMatches.isEmpty() ? Optional.empty() : Optional.of(inProgressMatches);
    }
}
