package strategy;

import model.Player;

public class ExactThrowWinningStrategy implements WinningStrategy {

    @Override
    public boolean hasWon(Player player, int boardSize) {
        return player.getPosition() == boardSize;
    }

    @Override
    public int getNextPosition(Player player, int roll, int boardSize) {
        int nextPosition = player.getPosition() + roll;
        if (nextPosition > boardSize) {
            return player.getPosition(); // No move if overshoot
        }
        return nextPosition;
    }
}