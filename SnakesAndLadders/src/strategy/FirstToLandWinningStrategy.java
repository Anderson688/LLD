package strategy;

import model.Player;

public class FirstToLandWinningStrategy implements WinningStrategy {

    @Override
    public boolean hasWon(Player player, int boardSize) {
        return player.getPosition() >= boardSize;
    }

    @Override
    public int getNextPosition(Player player, int roll, int boardSize) {
        return player.getPosition() + roll;
    }
}