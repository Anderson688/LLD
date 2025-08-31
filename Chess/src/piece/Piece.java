package piece;

import move.Move;
import move.MovementStrategy;

public abstract class Piece {
    private final boolean isWhite;
    private boolean killed = false;
    private MovementStrategy movementStrategy;

    public Piece(boolean isWhite, MovementStrategy movementStrategy) {
        this.isWhite = isWhite;
        this.movementStrategy = movementStrategy;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public boolean getIsWhite() {
        return isWhite;
    }

    public boolean isValidMove(Move move) {
        return movementStrategy.isValidMove(move);
    }
}