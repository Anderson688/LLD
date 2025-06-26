package strategy;

import model.Player;

public interface WinningStrategy {
    boolean hasWon(Player player, int boardSize);
    int getNextPosition(Player player, int roll, int boardSize);
}