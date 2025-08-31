package piece;

import move.BishopMovementStrategy;

public class Bishop extends Piece {
    public Bishop(boolean isWhite) {
        super(isWhite, new BishopMovementStrategy());
    }
}