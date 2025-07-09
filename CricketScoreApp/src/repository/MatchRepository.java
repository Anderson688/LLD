package repository;

import model.Match;

import java.util.List;
import java.util.Optional;

public interface MatchRepository {
    void save(Match match);
    Optional<Match> findById(String matchId);
    Optional<List<Match>> findAllInProgress();
}